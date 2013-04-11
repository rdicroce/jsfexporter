package com.lapis.pfexporter.impl.datatable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;

import com.lapis.pfexporter.api.FacetType;
import com.lapis.pfexporter.api.IExportType;
import com.lapis.pfexporter.api.ITabularExportType;
import com.lapis.pfexporter.api.datatable.DataTableExportOptions;
import com.lapis.pfexporter.api.datatable.DataTableExportOptions.ExportRange;
import com.lapis.pfexporter.impl.TableCellImpl;
import com.lapis.pfexporter.impl.ValueFormatterUtil;
import com.lapis.pfexporter.spi.IExportSource;

public class DataTableExportSource implements IExportSource<DataTable, DataTableExportOptions> {

	/*@Override
	public IDataProvider exportData(DataTable source, DataTableExportOptions configOptions, FacesContext context) {
		if (configOptions.getRange() == ExportRange.ALL) {
			if (source.isLazy()) {
				// TODO implement
			} else {
				return new AllRowsNonLazyDataProvider(source, context);
			}
		} else { // PAGE_ONLY
			// TODO implement
		}
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public Class<DataTable> getSourceType() {
		return DataTable.class;
	}

	@Override
	public DataTableExportOptions getDefaultConfigOptions() {
		return new DataTableExportOptions();
	}

	@Override
	public void exportData(DataTable source, DataTableExportOptions configOptions, IExportType<?, ?> exporter, FacesContext context) throws Exception {
		if (exporter instanceof ITabularExportType) {
			doTabularExport(source, configOptions, (ITabularExportType<?, ?>) exporter, context);
		} else {
			throw new IllegalArgumentException(getClass().getSimpleName() + " does not support exporters of type " + exporter.getClass().getName());
		}
	}
	
	private void doTabularExport(DataTable source, DataTableExportOptions configOptions, ITabularExportType<?, ?> exporter, FacesContext context) {
		List<UIColumn> columns = new ArrayList<UIColumn>(source.getColumns());
		for (Iterator<UIColumn> columnIter = columns.iterator(); columnIter.hasNext();) {
			UIColumn column = columnIter.next();
			if (!column.isRendered() || !column.isExportable()) {
				columnIter.remove();
			}
		}
		
		boolean hasHeaders = false;
		List<String> headers = new ArrayList<String>();
		for (UIColumn column : columns) {
			if (column instanceof DynamicColumn) {
				((DynamicColumn) column).applyModel();
			}
			
			String headerText = column.getHeaderText();
			if (headerText == null) {
				UIComponent header = column.getFacet(FacetType.HEADER.getFacetName());
				if (header != null) {
					headerText = ValueFormatterUtil.transformComponentsToString(context, header);
				}
			}
			if (headerText != null) {
				hasHeaders = true;
			}
			headers.add(headerText);
		}
		if (hasHeaders) {
			for (String headerText : headers) {
				exporter.exportCell(new TableCellImpl(headerText, 1, 1, FacetType.HEADER));
			}
			exporter.rowComplete();
		}
		
		if (configOptions.getRange() == ExportRange.ALL) {
			if (source.isLazy()) {
				// TODO implement
			} else {
				for (int i = 0; i < source.getRowCount(); i++) {
					source.setRowIndex(i);
					
					for (UIColumn column : columns) {
						if (column instanceof DynamicColumn) {
							((DynamicColumn) column).applyModel();
						}
						exporter.exportCell(new TableCellImpl(ValueFormatterUtil.transformComponentsToString(context, column.getChildren()), 1, 1, null));
					}
					
					exporter.rowComplete();
				}
			}
		} else { // PAGE_ONLY
			// TODO implement
		}
	}

}
