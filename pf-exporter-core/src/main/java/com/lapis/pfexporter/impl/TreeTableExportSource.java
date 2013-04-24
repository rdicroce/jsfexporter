package com.lapis.pfexporter.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.api.UIColumn;
import org.primefaces.component.api.UITree;
import org.primefaces.component.treetable.TreeTable;
import org.primefaces.model.TreeNode;

import com.lapis.pfexporter.api.FacetType;
import com.lapis.pfexporter.api.IExportCell;
import com.lapis.pfexporter.api.IExportType;
import com.lapis.pfexporter.spi.IExportSource;
import com.lapis.pfexporter.util.ExportUtil;

public class TreeTableExportSource implements IExportSource<TreeTable, Void> {

	private static final Map<FacetType, List<String>> FACET_NAMES;
	
	static {
		Map<FacetType, List<String>> facetNamesTemp = new HashMap<FacetType, List<String>>();
		facetNamesTemp.put(FacetType.HEADER, Collections.unmodifiableList(Arrays.asList("header")));
		facetNamesTemp.put(FacetType.FOOTER, Collections.unmodifiableList(Arrays.asList("footer")));
		FACET_NAMES = facetNamesTemp;
	}
	
	@Override
	public Class<TreeTable> getSourceType() {
		return TreeTable.class;
	}

	@Override
	public Void getDefaultConfigOptions() {
		return null;
	}
	
	@Override
	public int getColumnCount(TreeTable source, Void configOptions) {
		int columnCount = 0;
		for (UIComponent kid : source.getChildren()) {
			if (kid instanceof UIColumn && kid.isRendered() && ((UIColumn) kid).isExportable()) {
				columnCount++;
			}
		}
		return columnCount;
	}

	@Override
	public void exportData(TreeTable source, Void configOptions, IExportType<?, ?, ?> exporter, FacesContext context) throws Exception {
		List<UIColumn> columns = new ArrayList<UIColumn>();
		for (UIComponent kid : source.getChildren()) {
			if (kid instanceof UIColumn && kid.isRendered() && ((UIColumn) kid).isExportable()) {
				columns.add((UIColumn) kid);
			}
		}
		
		List<String> rowName = Arrays.asList(source.getVar());
		List<List<String>> columnNames = exportFacet(FacetType.HEADER, columns, exporter, context);
		exportNode(source.getValue(), null, null, source, rowName, columns, columnNames, exporter, context);
		exportFacet(FacetType.FOOTER, columns, exporter, context);
	}
	
	private List<List<String>> exportFacet(FacetType facetType, List<UIColumn> columns, IExportType<?, ?, ?> exporter, FacesContext context) {
		List<List<String>> columnNames = new ArrayList<List<String>>();
		List<IExportCell> facetCells = new ArrayList<IExportCell>();
		boolean hasFacet = false;
		for (UIColumn column : columns) {
			String facetText = ExportUtil.getColumnFacetText(column, facetType, context);
			if (facetText != null) {
				hasFacet = true;
			}
			columnNames.add(Arrays.asList(facetText));
		}
		if (hasFacet) {
			for (List<String> columnName : columnNames) {
				facetCells.add(new ExportCellImpl(FACET_NAMES.get(facetType), columnName.get(0), 1, 1));
			}
			exporter.exportRow(new ExportRowImpl(FACET_NAMES.get(facetType), null, facetType, facetCells));
		}
		
		return columnNames;
	}
	
	private void exportNode(TreeNode node, String rowKey, Object parentRowId, TreeTable source, List<String> rowName, List<UIColumn> columns,
			List<List<String>> columnNames, IExportType<?, ?, ?> exporter, FacesContext context) {
		if (rowKey != null) {
			source.setRowKey(rowKey);
			
			List<IExportCell> cells = new ArrayList<IExportCell>();
			int columnCount = columns.size();
			for (int i = 0; i < columnCount; i++) {
				cells.add(new ExportCellImpl(columnNames.get(i), ExportUtil.transformComponentsToString(context, columns.get(i).getChildren()), 1, 1));
			}
			
			parentRowId = exporter.exportRow(new ExportRowImpl(rowName, parentRowId, null, cells));
		}
		
		for (int i = 0; i < node.getChildCount(); i++) {
			String childRowKey = rowKey == null ? String.valueOf(i) : rowKey + UITree.SEPARATOR + i;
			exportNode(node.getChildren().get(i), childRowKey, parentRowId, source, rowName, columns, columnNames, exporter, context);
		}
	}

}
