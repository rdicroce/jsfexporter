package com.lapis.pfexporter.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;

import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;

import com.lapis.pfexporter.api.FacetType;
import com.lapis.pfexporter.api.IExportType;
import com.lapis.pfexporter.api.IHierarchicalExportType;
import com.lapis.pfexporter.api.ITabularExportType;
import com.lapis.pfexporter.api.datatable.DataTableExportOptions;
import com.lapis.pfexporter.api.datatable.DataTableExportOptions.ExportRange;
import com.lapis.pfexporter.spi.IExportSource;
import com.lapis.pfexporter.util.ExportUtil;

public class DataTableExportSource implements IExportSource<DataTable, DataTableExportOptions> {

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
		List<UIColumn> columns = new ArrayList<UIColumn>(source.getColumns());
		for (Iterator<UIColumn> columnIter = columns.iterator(); columnIter.hasNext();) {
			UIColumn column = columnIter.next();
			if (!column.isRendered() || !column.isExportable()) {
				columnIter.remove();
			}
		}
		
		if (exporter instanceof ITabularExportType) {
			doTabularExport(source, columns, configOptions, (ITabularExportType<?, ?>) exporter, context);
		} else if (exporter instanceof IHierarchicalExportType) {
			doHierarchicalExport(source, columns, configOptions, (IHierarchicalExportType<?, ?>) exporter, context);
		} else {
			throw new IllegalArgumentException(getClass().getSimpleName() + " does not support exporters of type " + exporter.getClass().getName());
		}
	}
	
	private void doTabularExport(DataTable source, List<UIColumn> columns, DataTableExportOptions configOptions, ITabularExportType<?, ?> exporter, FacesContext context) {
		boolean hasHeaders = false;
		List<String> headers = new ArrayList<String>();
		for (UIColumn column : columns) {
			String headerText = ExportUtil.getColumnHeaderText(column, context);
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
			int first = source.getFirst();
			if (source.isLazy()) {
				// TODO implement
			} else {
				exportTabularRow(source, columns, 0, source.getRowCount(), exporter, context);
				source.setFirst(first);
			}
		} else { // PAGE_ONLY
			exportTabularRow(source, columns, source.getFirst(), source.getFirst() + source.getRows(), exporter, context);
		}
	}
	
	private void exportTabularRow(DataTable source, List<UIColumn> columns, int startingRow, int endingRow, ITabularExportType<?, ?> exporter, FacesContext context) {
		for (int i = startingRow; i < endingRow; i++) {
			source.setRowIndex(i);
			
			for (UIColumn column : columns) {
				if (column instanceof DynamicColumn) {
					((DynamicColumn) column).applyModel();
				}
				exporter.exportCell(new TableCellImpl(ExportUtil.transformComponentsToString(context, column.getChildren()), 1, 1, null));
			}
			
			exporter.rowComplete();
		}
	}
	
	private void doHierarchicalExport(DataTable source, List<UIColumn> columns, DataTableExportOptions configOptions, IHierarchicalExportType<?, ?> exporter, FacesContext context) {
		if (configOptions.getRange() == ExportRange.ALL) {
			int first = source.getFirst();
			if (source.isLazy()) {
				// TODO implement
			} else {
				exportHierarchicalRow(source, columns, 0, source.getRowCount(), exporter, context);
				source.setFirst(first);
			}
		} else { // PAGE_ONLY
			exportHierarchicalRow(source, columns, source.getFirst(), source.getFirst() + source.getRows(), exporter, context);
		}
	}
	
	private void exportHierarchicalRow(DataTable source, List<UIColumn> columns, int startingRow, int endingRow, IHierarchicalExportType<?, ?> exporter, FacesContext context) {
		for (int i = startingRow; i < endingRow; i++) {
			source.setRowIndex(i);
			exporter.enterChild(source.getVar());
			
			for (UIColumn column : columns) {
				if (column instanceof DynamicColumn) {
					((DynamicColumn) column).applyModel();
				}
				
				exporter.exportValue(ExportUtil.getColumnHeaderText(column, context), ExportUtil.transformComponentsToString(context, column.getChildren()));
			}
			
			exporter.exitChild();
		}
	}

}
