/*
 * #%L
 * Lapis JSF Exporter Core
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
package com.lapis.jsfexporter.impl.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;

import com.lapis.jsfexporter.api.IExportCell;
import com.lapis.jsfexporter.api.IExportType;
import com.lapis.jsfexporter.impl.ExportCellImpl;
import com.lapis.jsfexporter.impl.ExportRowImpl;
import com.lapis.jsfexporter.spi.IExportSource;
import com.lapis.jsfexporter.util.ExportUtil;

public class PanelGridExportSource implements IExportSource<HtmlPanelGrid, Void> {

	@Override
	public Class<HtmlPanelGrid> getSourceType() {
		return HtmlPanelGrid.class;
	}

	@Override
	public Void getDefaultConfigOptions() {
		return null;
	}

	@Override
	public int getColumnCount(HtmlPanelGrid source, Void configOptions) {
		return source.getColumns();
	}

	@Override
	public void exportData(HtmlPanelGrid source, Void configOptions, IExportType<?, ?, ?> exporter, FacesContext context) throws Exception {
		int columnCount = source.getColumns();
		int currentColumn = 1;
		List<IExportCell> cells = new ArrayList<IExportCell>();
		for (UIComponent kid : source.getChildren()) {
			cells.add(new ExportCellImpl(Arrays.asList("column" + currentColumn), ExportUtil.transformComponentsToString(context, kid), 1, 1));
			if (currentColumn == columnCount) {
				exporter.exportRow(new ExportRowImpl(Arrays.asList("row"), null, null, cells));
				currentColumn = 1;
				cells.clear();
			} else {
				currentColumn++;
			}
		}
		if (currentColumn != 1) { // partial row
			for (; currentColumn <= columnCount; currentColumn++) {
				cells.add(new ExportCellImpl(Arrays.asList("column" + currentColumn), "", 1, 1));
			}
			exporter.exportRow(new ExportRowImpl(Arrays.asList("row"), null, null, cells));
		}
	}

}
