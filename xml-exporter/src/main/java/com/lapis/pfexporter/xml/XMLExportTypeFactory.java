package com.lapis.pfexporter.xml;

import org.jdom2.Document;

import com.lapis.pfexporter.api.IExportType;
import com.lapis.pfexporter.spi.IExportTypeFactory;

public class XMLExportTypeFactory implements IExportTypeFactory<Document, Void> {

	@Override
	public IExportType<Document, Void> createNewExporter(Void configOptions) {
		return new XMLExportType();
	}

	@Override
	public Void getDefaultConfigOptions() {
		return null;
	}

	@Override
	public String getContentType() {
		return "application/xml";
	}

	@Override
	public String getFileExtension() {
		return "xml";
	}

}
