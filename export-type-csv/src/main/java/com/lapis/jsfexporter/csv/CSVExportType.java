/*
 * #%L
 * Lapis JSF Exporter - CSV export type
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
package com.lapis.jsfexporter.csv;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;

import au.com.bytecode.opencsv.CSVWriter;

import com.lapis.jsfexporter.api.AbstractExportType;
import com.lapis.jsfexporter.api.IExportCell;
import com.lapis.jsfexporter.api.IExportRow;

public class CSVExportType extends AbstractExportType<List<List<String>>, CSVExportOptions, Integer> {

	private static final String[] EMPTY_STRING_ARRAY = new String[0];
	
	private CSVExportOptions configOptions;
	
	private int columnCount;
	private List<List<String>> rows;
	private int rowsIndex;
	
	public CSVExportType(CSVExportOptions configOptions) {
		this.configOptions = configOptions;
		rows = new ArrayList<List<String>>();
	}

	@Override
	public void writeExport(ExternalContext externalContext) throws Exception {
		String encoding = configOptions.getCharacterEncoding();
		boolean writeBOM = false;
		if ("UTF-8-with-bom".equalsIgnoreCase(encoding)) {
			externalContext.setResponseCharacterEncoding("UTF-8");
			writeBOM = true;
		} else {
			externalContext.setResponseCharacterEncoding(encoding);
		}
		
		Writer outputWriter = externalContext.getResponseOutputWriter();
		CSVWriter csvWriter = new CSVWriter(
				outputWriter,
				configOptions.getSeparatorCharacter(),
				configOptions.getQuoteCharacter(),
				configOptions.getEscapeCharacter(),
				configOptions.getLineTerminator());
		
		if (writeBOM) {
			outputWriter.write('\ufeff');
		}
		
		for (List<String> row : rows) {
			csvWriter.writeNext(row.toArray(EMPTY_STRING_ARRAY));
		}
	}
	
	@Override
	public List<List<String>> getContext() {
		return rows;
	}

	@Override
	public void beginExport(int columnCount) {
		this.columnCount = columnCount;
	}

	@Override
	public Integer exportRow(IExportRow row) {
		List<String> outputRow;
		if (rowsIndex < rows.size()) {
			outputRow = rows.get(rowsIndex);
		} else {
			outputRow = addRow();
		}
		
		int columnIndex = 0;
		for (IExportCell cell : row.getCells()) {
			while (outputRow.get(columnIndex) != null) {
				columnIndex++;
			}
			outputRow.set(columnIndex, cell.getValue());
			columnIndex++;
			
			for (int i = 1; i < cell.getColumnSpanCount(); i++) {
				outputRow.set(columnIndex, "");
				columnIndex++;
			}
			
			List<String> spanRow;
			for (int i = 1; i < cell.getRowSpanCount(); i++) {
				if (rowsIndex + i < rows.size()) {
					spanRow = rows.get(rowsIndex + i);
				} else {
					spanRow = addRow();
				}
				
				for (int j = 0; j < cell.getColumnSpanCount(); j++) {
					spanRow.set(columnIndex - j - 1, "");
				}
			}
		}
		
		return rowsIndex++;
	}
	
	private List<String> addRow() {
		List<String> newRow = new ArrayList<String>();
		for (int i = 0; i < columnCount; i++) {
			newRow.add(null);
		}
		rows.add(newRow);
		return newRow;
	}

	@Override
	public String getContentType() {
		return "text/csv";
	}

	@Override
	public String getFileExtension() {
		return "csv";
	}

}
