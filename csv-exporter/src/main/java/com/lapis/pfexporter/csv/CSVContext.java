package com.lapis.pfexporter.csv;

import java.io.StringWriter;
import au.com.bytecode.opencsv.CSVWriter;

public class CSVContext {

	private CSVWriter writer;
	private StringWriter buffer;
	
	public CSVContext(CSVWriter writer, StringWriter buffer) {
		this.writer = writer;
		this.buffer = buffer;
	}

	public CSVWriter getWriter() {
		return writer;
	}

	public StringWriter getBuffer() {
		return buffer;
	}
	
}
