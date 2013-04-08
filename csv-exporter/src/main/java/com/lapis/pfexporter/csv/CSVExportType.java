package com.lapis.pfexporter.csv;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;

import au.com.bytecode.opencsv.CSVWriter;

import com.lapis.pfexporter.api.IDataProvider;
import com.lapis.pfexporter.api.IRow;
import com.lapis.pfexporter.spi.IExportType;

public class CSVExportType implements IExportType<CSVContext, CSVExportOptions> {

	private static final String[] EMPTY_STRING_ARRAY = new String[0];
	
	@Override
	public CSVContext createNewContext(CSVExportOptions configOptions) throws Exception {
		StringWriter buffer = new StringWriter();
		return new CSVContext(
				new CSVWriter(
						buffer,
						configOptions.getSeparatorCharacter(),
						configOptions.getQuoteCharacter(),
						configOptions.getEscapeCharacter(),
						configOptions.getLineTerminator()),
				buffer);
	}

	@Override
	public String getContentType() {
		return "text/csv";
	}

	@Override
	public String getFileExtension() {
		return "csv";
	}

	@Override
	public void generateExport(CSVContext context, CSVExportOptions configOptions, IDataProvider data) throws Exception {
		List<String> values = new ArrayList<String>();
		CSVWriter writer = context.getWriter();
		
		String encoding = configOptions.getCharacterEncoding();
		if ("UTF-8-with-bom".equalsIgnoreCase(encoding)) {
			context.getBuffer().write('\ufeff');
		}
		
		while (data.hasNext()) {
			IRow row = data.next();
			while (row.hasNext()) {
				values.add(row.next());
			}
			writer.writeNext(values.toArray(EMPTY_STRING_ARRAY));
			values.clear();
		}
	}

	@Override
	public void writeExport(CSVContext context, CSVExportOptions configOptions, ExternalContext externalContext) throws Exception {
		String encoding = configOptions.getCharacterEncoding();
		encoding = "UTF-8-with-bom".equalsIgnoreCase(encoding) ? "UTF-8" : encoding;
		externalContext.setResponseCharacterEncoding(encoding);
		externalContext.getResponseOutputWriter().write(context.getBuffer().toString());
	}

	@Override
	public CSVExportOptions getDefaultConfigOptions() {
		return new CSVExportOptions();
	}

}
