/*
 * #%L
 * Lapis JSF Exporter - PrimeFaces export sources
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
package com.lapis.jsfexporter.primefaces.datalist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.FacesContext;

import org.primefaces.component.datalist.DataList;

import com.lapis.jsfexporter.api.IExportCell;
import com.lapis.jsfexporter.api.IExportType;
import com.lapis.jsfexporter.impl.ExportCellImpl;
import com.lapis.jsfexporter.impl.ExportRowImpl;
import com.lapis.jsfexporter.spi.IExportSource;
import com.lapis.jsfexporter.util.ExportUtil;

public class DataListExportSource implements IExportSource<DataList, DataListExportOptions> {

	private static final List<String> CONTENT_NAME = Arrays.asList("content");
	private static final List<String> DESCRIPTION_NAME = Arrays.asList("description");
	
	@Override
	public Class<DataList> getSourceType() {
		return DataList.class;
	}

	@Override
	public DataListExportOptions getDefaultConfigOptions() {
		return new DataListExportOptions();
	}

	@Override
	public int getColumnCount(DataList source, DataListExportOptions configOptions) {
		return source.isDefinition() ? 2 : 1;
	}

	@Override
	public void exportData(DataList source, DataListExportOptions configOptions, IExportType<?, ?, ?> exporter,
			FacesContext context) throws Exception {
		if (configOptions.getRange() == DataListExportRange.ALL) {
			if (source.isLazy()) {
				int first = source.getFirst();
				int rowCount = source.getRowCount();
				int rowsPerPage = source.getRows();
				
				int passes = rowCount / rowsPerPage; // integer division rounds towards zero
				int finalPass = rowCount % rowsPerPage;
				for (int i = 0; i < passes; i++) {
					source.setFirst(i * rowsPerPage);
					source.loadLazyData();
					exportRow(source, i * rowsPerPage, (i + 1) * rowsPerPage, exporter, context);
				}
				if (finalPass > 0) {
					source.setFirst(passes * rowsPerPage);
					source.loadLazyData();
					exportRow(source, passes * rowsPerPage, (passes * rowsPerPage) + finalPass, exporter, context);
				}
				
				source.setFirst(first);
				source.loadLazyData();
			} else {
				exportRow(source, 0, source.getRowCount(), exporter, context);
			}
		} else { // PAGE_ONLY
			exportRow(source, source.getFirst(), source.getFirst() + source.getRows(), exporter, context);
		}
	}
	
	private void exportRow(DataList source, int startingRow, int endingRow, IExportType<?, ?, ?> exporter,
			FacesContext context) {
		List<String> rowName = Arrays.asList(source.getVar());
		List<IExportCell> cells = new ArrayList<IExportCell>();
		
		for (int i = startingRow; i < endingRow; i++) {
			source.setRowIndex(i);
			
			cells.add(new ExportCellImpl(CONTENT_NAME, ExportUtil.transformComponentsToString(context, source.getChildren()), 1, 1));
			if (source.isDefinition()) {
				cells.add(new ExportCellImpl(DESCRIPTION_NAME, ExportUtil.transformComponentsToString(context, source.getFacet("description")), 1, 1));
			}
			
			exporter.exportRow(new ExportRowImpl(rowName, null, null, cells));
			cells.clear();
		}
	}

}
