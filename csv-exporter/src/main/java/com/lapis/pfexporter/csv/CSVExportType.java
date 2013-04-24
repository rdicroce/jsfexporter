package com.lapis.pfexporter.csv;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;

import au.com.bytecode.opencsv.CSVWriter;

import com.lapis.pfexporter.api.AbstractExportType;
import com.lapis.pfexporter.api.IExportCell;
import com.lapis.pfexporter.api.IExportRow;

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
		Writer outputWriter = externalContext.getResponseOutputWriter();
		CSVWriter csvWriter = new CSVWriter(
				outputWriter,
				configOptions.getSeparatorCharacter(),
				configOptions.getQuoteCharacter(),
				configOptions.getEscapeCharacter(),
				configOptions.getLineTerminator());
		
		String encoding = configOptions.getCharacterEncoding();
		if ("UTF-8-with-bom".equalsIgnoreCase(encoding)) {
			externalContext.setResponseCharacterEncoding("UTF-8");
			outputWriter.write('\ufeff');
		} else {
			externalContext.setResponseCharacterEncoding(encoding);
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

}
