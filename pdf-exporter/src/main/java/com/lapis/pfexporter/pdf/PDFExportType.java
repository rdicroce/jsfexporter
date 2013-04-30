package com.lapis.pfexporter.pdf;

import java.io.ByteArrayOutputStream;

import javax.faces.context.ExternalContext;

import com.lapis.pfexporter.api.IExportCell;
import com.lapis.pfexporter.api.IExportRow;
import com.lapis.pfexporter.api.IExportType;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

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
