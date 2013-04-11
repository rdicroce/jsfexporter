package com.lapis.pfexporter.csv;

import com.lapis.pfexporter.api.IExportType;
import com.lapis.pfexporter.spi.IExportTypeFactory;

public class CSVExportTypeFactory implements IExportTypeFactory<CSVContext, CSVExportOptions> {

	@Override
	public String getContentType() {
		return "text/csv";
	}

	@Override
	public String getFileExtension() {
		return "csv";
	}

	@Override
	public CSVExportOptions getDefaultConfigOptions() {
		return new CSVExportOptions();
	}

	@Override
	public IExportType<CSVContext, CSVExportOptions> createNewExporter(CSVExportOptions configOptions) {
		return new CSVExportType(configOptions);
	}
	
}
