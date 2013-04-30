package com.lapis.jsfexporter.primefaces.datatable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.columngroup.ColumnGroup;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.row.Row;

import com.lapis.jsfexporter.api.FacetType;
import com.lapis.jsfexporter.api.IExportCell;
import com.lapis.jsfexporter.api.IExportType;
import com.lapis.jsfexporter.impl.ExportCellImpl;
import com.lapis.jsfexporter.impl.ExportRowImpl;
import com.lapis.jsfexporter.primefaces.datatable.DataTableExportOptions.ExportRange;
import com.lapis.jsfexporter.primefaces.util.PrimeFacesUtil;
import com.lapis.jsfexporter.spi.IExportSource;
import com.lapis.jsfexporter.util.ExportUtil;

public class DataTableExportSource implements IExportSource<DataTable, DataTableExportOptions> {

	private static final Map<FacetType, List<String>> FACET_NAMES;
	
	static {
		Map<FacetType, List<String>> facetNamesTemp = new HashMap<FacetType, List<String>>();
		facetNamesTemp.put(FacetType.HEADER, Collections.unmodifiableList(Arrays.asList("header")));
		facetNamesTemp.put(FacetType.FOOTER, Collections.unmodifiableList(Arrays.asList("footer")));
		FACET_NAMES = facetNamesTemp;
	}
	
	@Override
	public Class<DataTable> getSourceType() {
		return DataTable.class;
	}

	@Override
	public DataTableExportOptions getDefaultConfigOptions() {
		return new DataTableExportOptions();
	}
	
	@Override
	public int getColumnCount(DataTable source, DataTableExportOptions configOptions) {
		int columnCount = 0;
		for (UIComponent kid : source.getChildren()) {
			if (kid instanceof UIColumn && kid.isRendered() && ((UIColumn) kid).isExportable()) {
				columnCount++;
			}
		}
		return columnCount;
	}

	@Override
	public void exportData(DataTable source, DataTableExportOptions configOptions, IExportType<?, ?, ?> exporter, FacesContext context) throws Exception {
		List<UIColumn> columns = new ArrayList<UIColumn>();
		for (UIComponent kid : source.getChildren()) {
			if (kid instanceof UIColumn && kid.isRendered() && ((UIColumn) kid).isExportable()) {
				columns.add((UIColumn) kid);
			}
		}
		
		List<List<String>> columnNames = exportFacet(FacetType.HEADER, source, columns, exporter, context);
		
		if (configOptions.getRange() == ExportRange.ALL) {
			if (source.isLazy()) {
				int first = source.getFirst();
				int rowCount = source.getRowCount();
				int rowsPerPage = source.getRows();
				
				int passes = rowCount / rowsPerPage; // integer division rounds towards zero
				int finalPass = rowCount % rowsPerPage;
				for (int i = 0; i < passes; i++) {
					source.setFirst(i * rowsPerPage);
					source.loadLazyData();
					exportRowCells(source, columns, columnNames, i * rowsPerPage, (i + 1) * rowsPerPage, exporter, context);
				}
				if (finalPass > 0) {
					exportRowCells(source, columns, columnNames, passes * rowsPerPage, (passes * rowsPerPage) + finalPass, exporter, context);
				}
				
				source.setFirst(first);
				source.loadLazyData();
			} else {
				exportRowCells(source, columns, columnNames, 0, source.getRowCount(), exporter, context);
			}
		} else { // PAGE_ONLY
			exportRowCells(source, columns, columnNames, source.getFirst(), source.getFirst() + source.getRows(), exporter, context);
		}
		
		exportFacet(FacetType.FOOTER, source, columns, exporter, context);
	}
	
	private List<List<String>> exportFacet(FacetType facetType, DataTable source, List<UIColumn> columns, IExportType<?, ?, ?> exporter, FacesContext context) {
		List<List<String>> columnNames = new ArrayList<List<String>>();
		List<IExportCell> facetCells = new ArrayList<IExportCell>();
		ColumnGroup columnGroup = source.getColumnGroup(facetType.getFacetName());
		
		if (columnGroup == null) {
			boolean hasFacet = false;
			for (UIColumn column : columns) {
				String facetText = PrimeFacesUtil.getColumnFacetText(column, facetType, context);
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
		} else if (columnGroup.getChildCount() > 0) {
			int rowCount = columnGroup.getChildCount();
			int colCount = 0;
			for (int i = 0; i < columnGroup.getChildren().get(0).getChildCount(); i++) {
				colCount += ((UIColumn) columnGroup.getChildren().get(0).getChildren().get(i)).getColspan();
			}
			
			for (int i = 0; i < colCount; i++) {
				List<String> columnName = new ArrayList<String>();
				for (int j = 0; j < rowCount; j++) {
					columnName.add(null);
				}
				columnNames.add(columnName);
			}
			
			for (int rowIndex = 0; rowIndex < columnGroup.getChildCount(); rowIndex++) {
				Row row = (Row) columnGroup.getChildren().get(rowIndex);
				int columnIndex = 0;
				
				for (UIComponent rowChild : row.getChildren()) {
					UIColumn rowColumn = (UIColumn) rowChild;
					String facetText = PrimeFacesUtil.getColumnFacetText(rowColumn, facetType, context);
					
					while (columnNames.get(columnIndex).get(rowIndex) != null) {
						columnIndex++;
					}
					
					for (int i = 0; i < rowColumn.getColspan(); i++) {
						for (int j = 0; j < rowColumn.getRowspan(); j++) {
							columnNames.get(columnIndex).set(rowIndex + j, facetText);
						}
						columnIndex++;
					}
					
					facetCells.add(new ExportCellImpl(FACET_NAMES.get(facetType), facetText, rowColumn.getColspan(), rowColumn.getRowspan()));
				}
				
				exporter.exportRow(new ExportRowImpl(FACET_NAMES.get(facetType), null, facetType, facetCells));
				facetCells.clear();
			}
		}
		
		for (int i = 0; i < columnNames.size(); i++) {
			columnNames.set(i, new ArrayList<String>(new LinkedHashSet<String>(columnNames.get(i))));
		}
		
		return columnNames;
	}
	
	private void exportRowCells(DataTable source, List<UIColumn> columns, List<List<String>> columnNames, int startingRow, int endingRow, IExportType<?, ?, ?> exporter, FacesContext context) {
		List<String> rowName = Arrays.asList(source.getVar());
		List<IExportCell> cells = new ArrayList<IExportCell>();
		int columnCount = columns.size();
		
		for (int i = startingRow; i < endingRow; i++) {
			source.setRowIndex(i);
			
			for (int j = 0; j < columnCount; j++) {
				UIColumn column = columns.get(j);
				if (column instanceof DynamicColumn) {
					((DynamicColumn) column).applyModel();
				}
				
				cells.add(new ExportCellImpl(columnNames.get(j), ExportUtil.transformComponentsToString(context, column.getChildren()), 1, 1));
			}
			
			exporter.exportRow(new ExportRowImpl(rowName, null, null, cells));
			cells.clear();
		}
	}

}
