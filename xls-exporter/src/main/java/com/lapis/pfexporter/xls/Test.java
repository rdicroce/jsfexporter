package com.lapis.pfexporter.xls;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Workbook wb = new HSSFWorkbook();
		Sheet s = wb.createSheet();
		
		Row r0 = s.createRow(0);
		Cell r0c0 = r0.createCell(0);
		//System.out.println(r0c0);
		s.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
		//Row r1 = s.createRow(1);
		//Cell r1c0 = r1.getCell(0);
		//System.out.println(r1c0);
		System.out.println(s.getMergedRegion(0).isInRange(1, 0));
	}

}
