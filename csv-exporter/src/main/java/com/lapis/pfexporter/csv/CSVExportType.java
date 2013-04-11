package com.lapis.pfexporter.csv;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;

import au.com.bytecode.opencsv.CSVWriter;

import com.lapis.pfexporter.api.ITableCell;
import com.lapis.pfexporter.api.ITabularExportType;

public class CSVExportType implements ITabularExportType<CSVContext, CSVExportOptions> {

	private static final String[] EMPTY_STRING_ARRAY = new String[0];
	
	private CSVExportOptions configOptions;
	private CSVContext context;
	
	private List<String> currentRow;
	
	public CSVExportType(CSVExportOptions configOptions) {
		this.configOptions = configOptions;
		
		StringWriter buffer = new StringWriter();
		context = new CSVContext(
				new CSVWriter(
						buffer,
						configOptions.getSeparatorCharacter(),
						configOptions.getQuoteCharacter(),
						configOptions.getEscapeCharacter(),
						configOptions.getLineTerminator()),
				buffer);
		
		currentRow = new ArrayList<String>();
	}

	@Override
	public void writeExport(ExternalContext externalContext) throws Exception {
		Writer outputWriter = externalContext.getResponseOutputWriter();
		String encoding = configOptions.getCharacterEncoding();
		if ("UTF-8-with-bom".equalsIgnoreCase(encoding)) {
			externalContext.setResponseCharacterEncoding("UTF-8");
			outputWriter.write('\ufeff');
		} else {
			externalContext.setResponseCharacterEncoding(encoding);
		}
		outputWriter.write(context.getBuffer().toString());
	}

	@Override
	public void exportCell(ITableCell cell) {
		currentRow.add(cell.getValue());
	}

	@Override
	public void rowComplete() {
		context.getWriter().writeNext(currentRow.toArray(EMPTY_STRING_ARRAY));
		currentRow.clear();
	}
	
	@Override
	public CSVContext getContext() {
		return context;
	}

}
