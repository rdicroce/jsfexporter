package com.lapis.pfexporter.xml;

import javax.faces.context.ExternalContext;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.lapis.pfexporter.api.IHierarchicalExportType;

public class XMLExportType implements IHierarchicalExportType<Document, Void> {

	// !|"|#|\$|%|&|'|\(|\)|\*|\+|,|/|;|<|=|>|\?|@|\[|\\|\]|\^|`|\{|\||\}|~|\s
	private static final String ILLEGAL_CHARACTER_REGEX = "!|\"|#|\\$|%|&|'|\\(|\\)|\\*|\\+|,|/|;|<|=|>|\\?|@|\\[|\\\\|\\]|\\^|`|\\{|\\||\\}|~|\\s";
	// ^(-|\.|\d).*
	private static final String ILLEGAL_START_REGEX = "^(-|\\.|\\d).*";
	
	private Document document;
	private Element currentElement;
	
	public XMLExportType() {
		currentElement = new Element("export");
		document = new Document(currentElement);
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
	public void enterChild(String name) {
		Element child = new Element(cleanElementName(name));
		currentElement.addContent(child);
		currentElement = child;
	}

	@Override
	public void exportValue(String name, String value) {
		Element child = new Element(cleanElementName(name));
		child.setText(value);
		currentElement.addContent(child);
	}

	@Override
	public void exitChild() {
		currentElement = currentElement.getParentElement();
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
