/*
 * #%L
 * Lapis JSF Exporter Core
 * %%
 * Copyright (C) 2013 Lapis Software Associates
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
package com.lapis.jsfexporter.impl.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;

import com.lapis.jsfexporter.api.FacetType;
import com.lapis.jsfexporter.api.IExportCell;
import com.lapis.jsfexporter.api.IExportType;
import com.lapis.jsfexporter.impl.ExportCellImpl;
import com.lapis.jsfexporter.impl.ExportRowImpl;
import com.lapis.jsfexporter.spi.IExportSource;
import com.lapis.jsfexporter.util.ExportUtil;

public class DataTableExportSource implements IExportSource<HtmlDataTable, Void> {

	@Override
	public Class<HtmlDataTable> getSourceType() {
		return HtmlDataTable.class;
	}

	@Override
	public Void getDefaultConfigOptions() {
		return null;
	}

	@Override
	public int getColumnCount(HtmlDataTable source, Void configOptions) {
		int columnCount = 0;
		for (UIComponent child : source.getChildren()) {
			if (child instanceof UIColumn && child.isRendered()) {
				columnCount++;
			}
		}
		return columnCount;
	}

	@Override
	public void exportData(HtmlDataTable source, Void configOptions, IExportType<?, ?, ?> exporter, FacesContext context) throws Exception {
		List<UIColumn> columns = new ArrayList<UIColumn>();
		for (UIComponent child : source.getChildren()) {
			if (child instanceof UIColumn && child.isRendered()) {
				columns.add((UIColumn) child);
			}
		}
		
		List<List<String>> columnNames = exportFacet(FacetType.HEADER, source, columns, exporter, context);
		
		List<String> rowName = Arrays.asList(source.getVar());
		List<IExportCell> cells = new ArrayList<IExportCell>();
		for (int i = 0; i < source.getRowCount(); i++) {
			source.setRowIndex(i);
			
			for (int j = 0; j < columns.size(); j++) {
				UIColumn column = columns.get(j);
				
				cells.add(new ExportCellImpl(columnNames.get(j), ExportUtil.transformComponentsToString(context, column.getChildren()), 1, 1));
			}
			
			exporter.exportRow(new ExportRowImpl(rowName, null, null, cells));
			cells.clear();
		}
		
		exportFacet(FacetType.FOOTER, source, columns, exporter, context);
	}
	
	private List<List<String>> exportFacet(FacetType facetType, HtmlDataTable source, List<UIColumn> columns, IExportType<?, ?, ?> exporter, FacesContext context) {
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

}
