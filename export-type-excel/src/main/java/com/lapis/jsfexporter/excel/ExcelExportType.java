/*
 * #%L
 * Lapis JSF Exporter - Excel export type
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
package com.lapis.jsfexporter.excel;

import javax.faces.context.ExternalContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.lapis.jsfexporter.api.AbstractExportType;
import com.lapis.jsfexporter.api.IExportCell;
import com.lapis.jsfexporter.api.IExportRow;

public class ExcelExportType extends AbstractExportType<Workbook, ExcelExportOptions, Row> {

	private ExcelExportOptions configOptions;
	private Workbook workbook;
	private Sheet sheet;
	private int rowCount;
	
	public ExcelExportType(ExcelExportOptions configOptions) {
		this.configOptions = configOptions;
		workbook = configOptions.getFormat().createNewWorkbook();
		sheet = workbook.createSheet();
	}
	
	@Override
	public Workbook getContext() {
		return workbook;
	}

	@Override
	public Row exportRow(IExportRow row) {
		Row xlsRow = sheet.createRow(rowCount++);
		int cellIndex = 0;
		for (IExportCell cell : row.getCells()) {
			boolean cellIsUsed;
			do {
				cellIsUsed = false;
				for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
					CellRangeAddress region = sheet.getMergedRegion(i);
					if (region.isInRange(xlsRow.getRowNum(), cellIndex)) {
						cellIsUsed = true;
						cellIndex += region.getLastColumn() - region.getFirstColumn() + 1;
					}
				}
			} while (cellIsUsed);
			
			Cell xlsCell = xlsRow.createCell(cellIndex++);
			xlsCell.setCellValue(cell.getValue());
			
			if (cell.getColumnSpanCount() > 1 || cell.getRowSpanCount() > 1) {
				sheet.addMergedRegion(new CellRangeAddress(
						xlsCell.getRowIndex(),
						xlsCell.getRowIndex() + cell.getRowSpanCount() - 1,
						xlsCell.getColumnIndex(),
						xlsCell.getColumnIndex() + cell.getColumnSpanCount() - 1));
				cellIndex += cell.getColumnSpanCount() - 1;
			}
		}
		return xlsRow;
	}

	@Override
	public void writeExport(ExternalContext externalContext) throws Exception {
		workbook.write(externalContext.getResponseOutputStream());
	}

	@Override
	public String getContentType() {
		return configOptions.getFormat().getMimeType();
	}

	@Override
	public String getFileExtension() {
		return configOptions.getFormat().getFileExtension();
	}

}
