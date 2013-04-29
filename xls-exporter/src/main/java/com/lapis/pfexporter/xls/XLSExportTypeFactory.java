package com.lapis.pfexporter.xls;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.lapis.pfexporter.api.IExportType;
import com.lapis.pfexporter.spi.IExportTypeFactory;

public class XLSExportTypeFactory implements IExportTypeFactory<Workbook, Void, Row> {

	@Override
	public IExportType<Workbook, Void, Row> createNewExporter(Void configOptions) {
		return new XLSExportType();
	}

	@Override
	public Void getDefaultConfigOptions() {
		return null;
	}

	@Override
	public String getContentType() {
		return "application/vnd.ms-excel";
	}

	@Override
	public String getFileExtension() {
		return "xls";
	}

}
