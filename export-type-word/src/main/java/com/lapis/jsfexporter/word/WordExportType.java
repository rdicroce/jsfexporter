/*
 * #%L
 * Lapis JSF Exporter - Word export type
 * %%
 * Copyright (C) 2013 - 2015 Lapis Software Associates
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.lapis.jsfexporter.word;


import com.lapis.jsfexporter.api.IExportRow;
import com.lapis.jsfexporter.api.IExportType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import javax.faces.context.ExternalContext;

public class WordExportType implements IExportType<XWPFDocument, WordExportOptions, Integer> {

	private final WordExportOptions configOptions;
	private XWPFDocument document;
	private XWPFTable table;
	private int rowCount;

	public WordExportType(WordExportOptions configOptions) {
		this.configOptions = configOptions;
		document = configOptions.getFormat().createNewWorkbook();
		table = document.createTable();
	}

	@Override
	public XWPFDocument getContext() {
		return document;
	}

	@Override
	public void beginExport(int columnCount) {
		table = document.createTable(1,columnCount);
	}

	@Override
	public Integer exportRow(IExportRow row) {
		XWPFTableRow tableRow = table.createRow();
		for(int i  = 0; i< row.getCells().size(); i++){
			XWPFTableCell tableCell = tableRow.getCell(i);
			tableCell.setText(row.getCells().get(i).getValue());
		}

		return rowCount++;
//		returntableRow
	}

//	private static void rowSpan(XWPFTable table, int col, int fromRow, int toRow) {
//		for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
//			XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
//			if ( rowIndex == fromRow ) {
//				// The first merged cell is set with RESTART merge value
//				cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
//			} else {
//				// Cells which join (merge) the first one, are set with CONTINUE
//				cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
//			}
//		}
//	}
//	private void colSpan(XWPFTable table, int row, int fromCol, int toCol) {
//		XWPFTableCell cell = table.getRow(row).getCell(fromCol);
//		cell.getCTTc().getTcPr().addNewGridSpan();
//		cell.getCTTc().getTcPr().getGridSpan().setVal(BigInteger.valueOf((long) toCol));
//	}

	@Override
	public void endExport() {
//		try {
//			document.add(table);
//		} catch (DocumentException e) {
//			throw new RuntimeException(e);
//		}
	}

	@Override
	public void writeExport(ExternalContext externalContext) throws Exception {
		document.write(externalContext.getResponseOutputStream());
	}

	@Override
	public String getContentType() {
		return "application/pdf";
	}

	@Override
	public String getFileExtension() {
		return "pdf";
	}

}
