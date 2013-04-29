package com.lapis.pfexporter.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.lapis.pfexporter.api.IExportType;
import com.lapis.pfexporter.spi.IExportTypeFactory;

public class ExcelExportTypeFactory implements IExportTypeFactory<Workbook, ExcelExportOptions, Row> {

	@Override
	public IExportType<Workbook, ExcelExportOptions, Row> createNewExporter(ExcelExportOptions configOptions) {
		return new ExcelExportType(configOptions);
	}

	@Override
	public ExcelExportOptions getDefaultConfigOptions() {
		return new ExcelExportOptions();
	}

	@Override
	public String getExportTypeId() {
		return "excel";
	}

}
