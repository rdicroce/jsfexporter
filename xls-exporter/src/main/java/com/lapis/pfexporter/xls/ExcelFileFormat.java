package com.lapis.pfexporter.xls;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public enum ExcelFileFormat {
	XLS("xls", "application/vnd.ms-excel") {
		@Override
		public Workbook createNewWorkbook() {
			return new HSSFWorkbook();
		}
	},
	XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
		@Override
		public Workbook createNewWorkbook() {
			return new XSSFWorkbook();
		}
	};
	
	private String fileExtension;
	private String mimeType;
	
	private ExcelFileFormat(String fileExtension, String mimeType) {
		this.fileExtension = fileExtension;
		this.mimeType = mimeType;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public String getMimeType() {
		return mimeType;
	}
	
	public abstract Workbook createNewWorkbook();
	
}
