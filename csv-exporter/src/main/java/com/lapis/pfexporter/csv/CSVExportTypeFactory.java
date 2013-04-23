package com.lapis.pfexporter.csv;

import java.util.List;

import com.lapis.pfexporter.api.IExportType;
import com.lapis.pfexporter.spi.IExportTypeFactory;

public class CSVExportTypeFactory implements IExportTypeFactory<List<List<String>>, CSVExportOptions, Integer> {

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
	public IExportType<List<List<String>>, CSVExportOptions, Integer> createNewExporter(CSVExportOptions configOptions) {
		return new CSVExportType(configOptions);
	}
	
}
