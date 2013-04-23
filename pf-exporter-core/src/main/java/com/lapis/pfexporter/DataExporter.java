package com.lapis.pfexporter;

import java.net.URLEncoder;
import java.util.HashMap;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.primefaces.util.Constants;

import com.lapis.pfexporter.api.IExportType;
import com.lapis.pfexporter.spi.IExportSource;
import com.lapis.pfexporter.spi.IExportTypeFactory;

public class DataExporter implements ActionListener, StateHolder {

	private ValueExpression source;
	private ValueExpression sourceOptions;
	private ValueExpression fileType;
	private ValueExpression fileName;
	private ValueExpression fileOptions;
	private MethodExpression preProcessor;
	private MethodExpression postProcessor;
	
	public DataExporter() {}
	
	public DataExporter(ValueExpression source, ValueExpression sourceOptions,
			ValueExpression fileType, ValueExpression fileName,
			ValueExpression fileOptions, MethodExpression preProcessor,
			MethodExpression postProcessor) {
		this.source = source;
		this.sourceOptions = sourceOptions;
		this.fileType = fileType;
		this.fileName = fileName;
		this.fileOptions = fileOptions;
		this.preProcessor = preProcessor;
		this.postProcessor = postProcessor;
	}

	@Override
	public Object saveState(FacesContext context) {
		Object[] values = new Object[7];
		values[0] = source;
		values[1] = sourceOptions;
		values[2] = fileType;
		values[3] = fileName;
		values[4] = fileOptions;
		values[5] = preProcessor;
		values[6] = postProcessor;
		return values;
	}

	@Override
	public void restoreState(FacesContext context, Object state) {
		Object[] values = (Object[]) state;
		source = (ValueExpression) values[0];
		sourceOptions = (ValueExpression) values[1];
		fileType = (ValueExpression) values[2];
		fileName = (ValueExpression) values[3];
		fileOptions = (ValueExpression) values[4];
		preProcessor = (MethodExpression) values[5];
		postProcessor = (MethodExpression) values[6];
	}

	@Override
	public boolean isTransient() {
		return false;
	}

	@Override
	public void setTransient(boolean newTransientValue) {}

	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException {
		try {
			internalProcessAction(event);
		} catch (Exception e) {
			throw new FacesException(e);
		}
	}
	
	private <TS extends UIComponent, CS, TT, CT> void internalProcessAction(ActionEvent event) throws Exception {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		ELContext elContext = facesContext.getELContext();
		
		// get the export source and retrieve source options
		String componentId = (String) source.getValue(elContext);
		TS sourceComponent = (TS) event.getComponent().findComponent(componentId);
		if (sourceComponent == null) {
			throw new FacesException("Could not find component \"" + componentId + "\" in view");
		}
		IExportSource<TS, CS> exportSource = (IExportSource<TS, CS>) ExportSourceFactory.getExportSource(facesContext, sourceComponent);
		CS sourceOptionsValue;
		if (sourceOptions == null) { // source options are not mandatory; get the defaults if not set
			sourceOptionsValue = exportSource.getDefaultConfigOptions();
		} else {
			sourceOptionsValue = (CS) sourceOptions.getValue(elContext);
		}
		
		// get the export type factory and retrieve file options
		String fileTypeValue = (String) fileType.getValue(elContext);
		IExportTypeFactory<TT, CT, ?> exportTypeFactory = (IExportTypeFactory<TT, CT, ?>) ExportTypeFactoryFactory.getExportType(facesContext, fileTypeValue);
		CT fileOptionsValue;
		if (fileOptions == null) { // file options are not mandatory; get the defaults if not set
			fileOptionsValue = exportTypeFactory.getDefaultConfigOptions();
		} else {
			fileOptionsValue = (CT) fileOptions.getValue(elContext);
		}
		
		// create a new exporter
		IExportType<TT, CT, ?> exportType = exportTypeFactory.createNewExporter(fileOptionsValue);
		TT exportContext = exportType.getContext();
		
		// invoke the pre-processor if there is one
		if (preProcessor != null) {
			preProcessor.invoke(elContext, new Object[]{exportContext});
		}
		
		// generate the export
		exportSource.exportData(sourceComponent, sourceOptionsValue, exportType, facesContext);
		
		// invoke the post-processor if there is one
		if (postProcessor != null) {
			postProcessor.invoke(elContext, new Object[]{exportContext});
		}
		
		// configure response meta-data
		externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", new HashMap<String, Object>());
		externalContext.setResponseContentType(exportTypeFactory.getContentType());
		externalContext.setResponseHeader("Expires", "0");
		externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		externalContext.setResponseHeader("Pragma", "public");
		/*
		 * filename* is the proper way to send non-ASCII filenames, but not all browsers support it (e.g. IE 8).
		 * So we also supply a normal filename encoded the same way, since that works on most browsers (but not Firefox).
		 * See RFC 6266 and http://greenbytes.de/tech/tc2231/
		 */
		String encodedFileName = URLEncoder.encode(fileName.getValue(elContext) + "." + fileTypeValue, "UTF-8");
		externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"; filename*=UTF8''" + encodedFileName);
		
		// write the response and signal JSF that we're done
		exportType.writeExport(externalContext);
		facesContext.responseComplete();
	}

}
