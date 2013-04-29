package com.lapis.pfexporter.xls;

import javax.faces.context.ExternalContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.lapis.pfexporter.api.AbstractExportType;
import com.lapis.pfexporter.api.IExportCell;
import com.lapis.pfexporter.api.IExportRow;

public class XLSExportType extends AbstractExportType<Workbook, Void, Row> {

	private Workbook workbook;
	private Sheet sheet;
	private int rowCount;
	
	public XLSExportType() {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet();
	}
	
	@Override
	public Workbook getContext() {
		return workbook;
	}

	@Override
	public Row exportRow(IExportRow row) {
		Row xlsRow = sheet.createRow(rowCount++);
		int cellIndex = 0;
		for (IExportCell cell : row.getCells()) {
			boolean cellIsUsed;
			do {
				cellIsUsed = false;
				for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
					CellRangeAddress region = sheet.getMergedRegion(i);
					if (region.isInRange(xlsRow.getRowNum(), cellIndex)) {
						cellIsUsed = true;
						cellIndex += region.getLastColumn() - region.getFirstColumn() + 1;
					}
				}
			} while (cellIsUsed);
			
			Cell xlsCell = xlsRow.createCell(cellIndex++);
			xlsCell.setCellValue(cell.getValue());
			
			if (cell.getColumnSpanCount() > 1 || cell.getRowSpanCount() > 1) {
				sheet.addMergedRegion(new CellRangeAddress(
						xlsCell.getRowIndex(),
						xlsCell.getRowIndex() + cell.getRowSpanCount() - 1,
						xlsCell.getColumnIndex(),
						xlsCell.getColumnIndex() + cell.getColumnSpanCount() - 1));
				cellIndex += cell.getColumnSpanCount() - 1;
			}
		}
		return xlsRow;
	}

	@Override
	public void writeExport(ExternalContext externalContext) throws Exception {
		workbook.write(externalContext.getResponseOutputStream());
	}

}
