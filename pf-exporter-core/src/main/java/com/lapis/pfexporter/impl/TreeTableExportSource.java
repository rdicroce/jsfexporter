package com.lapis.pfexporter.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.api.UIColumn;
import org.primefaces.component.api.UITree;
import org.primefaces.component.treetable.TreeTable;
import org.primefaces.model.TreeNode;

import com.lapis.pfexporter.api.FacetType;
import com.lapis.pfexporter.api.IExportType;
import com.lapis.pfexporter.api.IHierarchicalExportType;
import com.lapis.pfexporter.api.ITabularExportType;
import com.lapis.pfexporter.spi.IExportSource;
import com.lapis.pfexporter.util.ExportUtil;

public class TreeTableExportSource implements IExportSource<TreeTable, Void> {

	@Override
	public Class<TreeTable> getSourceType() {
		return TreeTable.class;
	}

	@Override
	public Void getDefaultConfigOptions() {
		return null;
	}

	@Override
	public void exportData(TreeTable source, Void configOptions, IExportType<?, ?> exporter, FacesContext context) throws Exception {
		List<UIColumn> columns = new ArrayList<UIColumn>();
		for (UIComponent kid : source.getChildren()) {
			if (kid instanceof UIColumn && kid.isRendered() && ((UIColumn) kid).isExportable()) {
				columns.add((UIColumn) kid);
			}
		}
		
		if (exporter instanceof ITabularExportType) {
			doTabularExport(source, columns, configOptions, (ITabularExportType<?, ?>) exporter, context);
		} else if (exporter instanceof IHierarchicalExportType) {
			processNodeForHierarchicalExport(source.getValue(), null, source, columns, (IHierarchicalExportType<?, ?>) exporter, context);
		} else {
			throw new IllegalArgumentException(getClass().getSimpleName() + " does not support exporters of type " + exporter.getClass().getName());
		}
	}
	
	private void doTabularExport(TreeTable source, List<UIColumn> columns, Void configOptions, ITabularExportType<?, ?> exporter, FacesContext context) {
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
		
		processNodeForTabularExport(source.getValue(), null, source, columns, exporter, context);
	}
	
	private void processNodeForTabularExport(TreeNode node, String rowKey, TreeTable source, List<UIColumn> columns, ITabularExportType<?, ?> exporter, FacesContext context) {
		if (rowKey != null) {
			source.setRowKey(rowKey);
			for (UIColumn column : columns) {
				exporter.exportCell(new TableCellImpl(ExportUtil.transformComponentsToString(context, column.getChildren()), 1, 1, null));
			}
			exporter.rowComplete();
		}
		
		for (int i = 0; i < node.getChildCount(); i++) {
			String childRowKey = rowKey == null ? String.valueOf(i) : rowKey + UITree.SEPARATOR + i;
			processNodeForTabularExport(node.getChildren().get(i), childRowKey, source, columns, exporter, context);
		}
	}
	
	private void processNodeForHierarchicalExport(TreeNode node, String rowKey, TreeTable source, List<UIColumn> columns, IHierarchicalExportType<?, ?> exporter, FacesContext context) {
		if (rowKey != null) {
			source.setRowKey(rowKey);
			exporter.enterChild(source.getVar());
			for (UIColumn column : columns) {
				exporter.exportValue(ExportUtil.getColumnHeaderText(column, context), ExportUtil.transformComponentsToString(context, column.getChildren()));
			}
		}
		
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				String childRowKey = rowKey == null ? String.valueOf(i) : rowKey + UITree.SEPARATOR + i;
				processNodeForHierarchicalExport(node.getChildren().get(i), childRowKey, source, columns, exporter, context);
			}
		}
		
		if (rowKey != null) {
			exporter.exitChild();
		}
	}

}
