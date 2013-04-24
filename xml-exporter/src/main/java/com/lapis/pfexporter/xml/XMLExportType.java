package com.lapis.pfexporter.xml;

import javax.faces.context.ExternalContext;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.lapis.pfexporter.api.AbstractExportType;
import com.lapis.pfexporter.api.IExportCell;
import com.lapis.pfexporter.api.IExportRow;

public class XMLExportType extends AbstractExportType<Document, Void, Element> {

	// !|"|#|\$|%|&|'|\(|\)|\*|\+|,|/|;|<|=|>|\?|@|\[|\\|\]|\^|`|\{|\||\}|~|\s
	private static final String ILLEGAL_CHARACTER_REGEX = "!|\"|#|\\$|%|&|'|\\(|\\)|\\*|\\+|,|/|;|<|=|>|\\?|@|\\[|\\\\|\\]|\\^|`|\\{|\\||\\}|~|\\s";
	// ^(-|\.|\d).*
	private static final String ILLEGAL_START_REGEX = "^(-|\\.|\\d).*";
	
	private Document document;
	private Element rootElement;
	
	public XMLExportType() {
		rootElement = new Element("export");
		document = new Document(rootElement);
	}
	
	@Override
	public Document getContext() {
		return document;
	}

	@Override
	public void writeExport(ExternalContext externalContext) throws Exception {
		externalContext.setResponseCharacterEncoding("UTF-8");
		new XMLOutputter(Format.getPrettyFormat()).output(document, externalContext.getResponseOutputStream());
	}
	
	@Override
	public Element exportRow(IExportRow row) {
		Element currentElement = (Element) row.getParentRowId();
		if (currentElement == null) {
			currentElement = rootElement;
		}
		
		for (String namePart : row.getName()) {
			Element subElement = new Element(cleanElementName(namePart));
			currentElement.addContent(subElement);
			currentElement = subElement;
		}
		
		for (IExportCell cell : row.getCells()) {
			Element cellElement = currentElement;
			for (String namePart : cell.getName()) {
				Element subElement = cellElement.getChild(namePart);
				if (subElement == null) {
					subElement = new Element(cleanElementName(namePart));
					cellElement.addContent(subElement);
				}
				cellElement = subElement;
			}
			
			if (!cellElement.getText().equals("")) {
				String cellName = cellElement.getName();
				cellElement = cellElement.getParentElement();
				Element newCellElement = new Element(cellName);
				cellElement.addContent(newCellElement);
				cellElement = newCellElement;
			}
			cellElement.setText(cell.getValue());
		}
		
		return currentElement;
	}
	
	/**
	 * Cleans an element name so it doesn't contain any illegal characters.
	 * According to the Wikipedia article on XML:
	 * 
	 * Tag names cannot contain any of the characters !"#$%&'()*+,/;<=>?@[\]^`{|}~, nor a space character,
	 * and cannot start with -, ., or a numeric digit.
	 * @param name
	 * @return
	 */
	private static String cleanElementName(String name) {
		name = name.replaceAll(ILLEGAL_CHARACTER_REGEX, "_");
		if (name.matches(ILLEGAL_START_REGEX)) {
			name = "_" + name;
		}
		return name;
	}

}
