package com.lapis.jsfexporter.excel;

public class ExcelExportOptions {

	private ExcelFileFormat format;
	
	public ExcelExportOptions() {
		this.format = ExcelFileFormat.XLSX;
	}

	public ExcelExportOptions(ExcelFileFormat format) {
		this.format = format;
	}

	public ExcelFileFormat getFormat() {
		return format;
	}

	public void setFormat(ExcelFileFormat format) {
		this.format = format;
	}
	
}
