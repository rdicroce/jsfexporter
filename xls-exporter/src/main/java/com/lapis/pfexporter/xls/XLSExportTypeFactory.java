package com.lapis.pfexporter.xls;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.lapis.pfexporter.api.IExportType;
import com.lapis.pfexporter.spi.IExportTypeFactory;

public class XLSExportTypeFactory implements IExportTypeFactory<Workbook, ExcelExportOptions, Row> {

	@Override
	public IExportType<Workbook, ExcelExportOptions, Row> createNewExporter(ExcelExportOptions configOptions) {
		return new XLSExportType(configOptions);
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
