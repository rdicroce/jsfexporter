/*
 * #%L
 * Lapis JSF Exporter - RichFaces export sources
 * %%
 * Copyright (C) 2013 - 2015 Lapis Software Associates
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.lapis.jsfexporter.richfaces.datatable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.richfaces.component.UIColumn;
import org.richfaces.component.UIDataTable;

import com.lapis.jsfexporter.api.FacetType;
import com.lapis.jsfexporter.api.IExportCell;
import com.lapis.jsfexporter.api.IExportType;
import com.lapis.jsfexporter.impl.ExportCellImpl;
import com.lapis.jsfexporter.impl.ExportRowImpl;
import com.lapis.jsfexporter.spi.IExportSource;
import com.lapis.jsfexporter.util.ExportUtil;

public class DataTableExportSource implements IExportSource<UIDataTable, DataTableExportOptions> {

	@Override
	public Class<UIDataTable> getSourceType() {
		return UIDataTable.class;
	}

	@Override
	public DataTableExportOptions getDefaultConfigOptions() {
		return new DataTableExportOptions();
	}
	
	@Override
	public int getColumnCount(UIDataTable source, DataTableExportOptions configOptions) {
		int columnCount = 0;
		for (UIComponent kid : source.getChildren()) {
			if (kid instanceof UIColumn && kid.isRendered()) {
				columnCount++;
			}
		}
		return columnCount;
	}

	@Override
	public void exportData(UIDataTable source, DataTableExportOptions configOptions, IExportType<?, ?, ?> exporter, FacesContext context) throws Exception {
		List<UIColumn> columns = new ArrayList<UIColumn>();
		for (UIComponent kid : source.getChildren()) {
			if (kid instanceof UIColumn && kid.isRendered()) {
				columns.add((UIColumn) kid);
			}
		}
		
		List<List<String>> columnNames = exportFacet(FacetType.HEADER, source, columns, exporter, context);
		
		if (configOptions.getRange() == DataTableExportRange.ALL) {
			exportRowCells(source, columns, columnNames, 0, source.getRowCount(), exporter, context);
		} else { // PAGE_ONLY
			exportRowCells(source, columns, columnNames, source.getFirst(), source.getFirst() + source.getRows(), exporter, context);
		}
		
		exportFacet(FacetType.FOOTER, source, columns, exporter, context);
	}
	
	private List<List<String>> exportFacet(FacetType facetType, UIDataTable source, List<UIColumn> columns, IExportType<?, ?, ?> exporter, FacesContext context) {
		List<List<String>> columnNames = new ArrayList<List<String>>();
		List<IExportCell> facetCells = new ArrayList<IExportCell>();
		
		boolean hasFacet = false;
		for (UIColumn column : columns) {
			UIComponent facetComponent = column.getFacet(facetType.getFacetName());
			if (facetComponent != null) {
				hasFacet = true;
				columnNames.add(Arrays.asList(ExportUtil.transformComponentsToString(context, facetComponent)));
			}
		}
		if (hasFacet) {
			for (List<String> columnName : columnNames) {
				facetCells.add(new ExportCellImpl(Arrays.asList(facetType.getFacetName()), columnName.get(0), 1, 1));
			}
			exporter.exportRow(new ExportRowImpl(Arrays.asList(facetType.getFacetName()), null, facetType, facetCells));
		}
		
		for (int i = 0; i < columnNames.size(); i++) {
			columnNames.set(i, new ArrayList<String>(new LinkedHashSet<String>(columnNames.get(i))));
		}
		
		return columnNames;
	}
	
	private void exportRowCells(UIDataTable source, List<UIColumn> columns, List<List<String>> columnNames, int startingRow, int endingRow, IExportType<?, ?, ?> exporter, FacesContext context) {
		List<String> rowName = Arrays.asList(source.getVar());
		List<IExportCell> cells = new ArrayList<IExportCell>();
		int columnCount = columns.size();
		
		for (int i = startingRow; i < endingRow; i++) {
			source.setRowKey(i);
			
			for (int j = 0; j < columnCount; j++) {
				UIColumn column = columns.get(j);
				
				cells.add(new ExportCellImpl(columnNames.get(j), ExportUtil.transformComponentsToString(context, column.getChildren()), 1, 1));
			}
			
			exporter.exportRow(new ExportRowImpl(rowName, null, null, cells));
			cells.clear();
		}
	}
	
}
