package com.lapis.pfexporter.pdf;

import com.lapis.pfexporter.api.IExportType;
import com.lapis.pfexporter.spi.IExportTypeFactory;
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
