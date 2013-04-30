package com.lapis.jsfexporter.pdf;

import com.lapis.jsfexporter.api.IExportType;
import com.lapis.jsfexporter.spi.IExportTypeFactory;
import com.lowagie.text.Document;

public class PDFExportTypeFactory implements IExportTypeFactory<Document, Void, Integer> {

	@Override
	public IExportType<Document, Void, Integer> createNewExporter(Void configOptions) {
		return new PDFExportType();
	}

	@Override
	public Void getDefaultConfigOptions() {
		return null;
	}

	@Override
	public String getExportTypeId() {
		return "pdf";
	}

}
