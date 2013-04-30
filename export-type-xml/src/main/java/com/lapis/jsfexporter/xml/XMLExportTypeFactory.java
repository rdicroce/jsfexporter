package com.lapis.jsfexporter.xml;

import org.jdom2.Document;
import org.jdom2.Element;

import com.lapis.jsfexporter.api.IExportType;
import com.lapis.jsfexporter.spi.IExportTypeFactory;

public class XMLExportTypeFactory implements IExportTypeFactory<Document, Void, Element> {

	@Override
	public IExportType<Document, Void, Element> createNewExporter(Void configOptions) {
		return new XMLExportType();
	}

	@Override
	public Void getDefaultConfigOptions() {
		return null;
	}

	@Override
	public String getExportTypeId() {
		return "xml";
	}

}
