package com.lapis.jsfexporter;

import java.io.IOException;

import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletException;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

public class DataExporterTagHandler extends TagHandler {
	private final TagAttribute source;
	private final TagAttribute sourceOptions;
	private final TagAttribute fileType;
	private final TagAttribute fileName;
	private final TagAttribute fileOptions;
	private final TagAttribute preProcessor;
	private final TagAttribute postProcessor;

	public DataExporterTagHandler(TagConfig tagConfig) {
		super(tagConfig);
		this.source = getRequiredAttribute("source");
		this.sourceOptions = getAttribute("sourceOptions");
		this.fileType = getRequiredAttribute("fileType");
		this.fileName = getRequiredAttribute("fileName");
		this.fileOptions = getAttribute("fileOptions");
		this.preProcessor = getAttribute("preProcessor");
		this.postProcessor = getAttribute("postProcessor");
	}

	public void apply(FaceletContext faceletContext, UIComponent parent) throws IOException, FacesException, FaceletException, ELException {
		if (ComponentHandler.isNew(parent)) {
			ValueExpression sourceVE = source.getValueExpression(faceletContext, Object.class);
			ValueExpression sourceOptionsVE = null;
			ValueExpression fileTypeVE = fileType.getValueExpression(faceletContext, Object.class);
			ValueExpression fileNameVE = fileName.getValueExpression(faceletContext, Object.class);
			ValueExpression fileOptionsVE = null;
			MethodExpression preProcessorME = null;
			MethodExpression postProcessorME = null;
			
			if(sourceOptions != null) {
				sourceOptionsVE = sourceOptions.getValueExpression(faceletContext, Object.class);
			}
			if(fileOptions != null) {
				fileOptionsVE = fileOptions.getValueExpression(faceletContext, Object.class);
			}
			if(preProcessor != null) {
				preProcessorME = preProcessor.getMethodExpression(faceletContext, null, new Class[]{Object.class});
			}
			if(postProcessor != null) {
				postProcessorME = postProcessor.getMethodExpression(faceletContext, null, new Class[]{Object.class});
			}
			
			((ActionSource) parent).addActionListener(new DataExporter(sourceVE, sourceOptionsVE, fileTypeVE, fileNameVE, fileOptionsVE, preProcessorME, postProcessorME));
		}
	}
}
