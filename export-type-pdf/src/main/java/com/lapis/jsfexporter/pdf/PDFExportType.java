/*
 * #%L
 * Lapis JSF Exporter - PDF export type
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
package com.lapis.jsfexporter.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lapis.jsfexporter.api.IExportCell;
import com.lapis.jsfexporter.api.IExportRow;
import com.lapis.jsfexporter.api.IExportType;

import javax.faces.context.ExternalContext;
import java.io.ByteArrayOutputStream;

public class PDFExportType implements IExportType<Document, Void, Integer> {

	private Document document;
	private PdfPTable table;
	private Font font;
	private int rowCount;
	private ByteArrayOutputStream buffer;
	
	public PDFExportType() {
		document = new Document();
		font = FontFactory.getFont("fonts/DroidSansFallbackFull.ttf", BaseFont.IDENTITY_H, true);
		buffer = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, buffer);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Document getContext() {
		return document;
	}
	
	@Override
	public void beginExport(int columnCount) {
		table = new PdfPTable(columnCount);
		if (!document.isOpen()) {
			document.open();
		}
	}

	@Override
	public Integer exportRow(IExportRow row) {
		for (IExportCell cell : row.getCells()) {
			PdfPCell pdfCell = new PdfPCell();
			pdfCell.setColspan(cell.getColumnSpanCount());
			pdfCell.setRowspan(cell.getRowSpanCount());
			pdfCell.setPhrase(new Phrase(cell.getValue(), font));
			table.addCell(pdfCell);
		}
		
		return rowCount++;
	}

	@Override
	public void endExport() {
		try {
			document.add(table);
		} catch (DocumentException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void writeExport(ExternalContext externalContext) throws Exception {
		document.close();
		buffer.writeTo(externalContext.getResponseOutputStream());
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
