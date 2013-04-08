package com.lapis.pfexporter.csv;

import java.io.Serializable;

public class CSVExportOptions implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private char separatorCharacter;
	private char quoteCharacter;
	private char escapeCharacter;
	private String lineTerminator;
	private String characterEncoding;
	
	public CSVExportOptions() {
		this.separatorCharacter = ',';
		this.quoteCharacter = '"';
		this.escapeCharacter = '"';
		this.lineTerminator = "\n";
		this.characterEncoding = "UTF-8";
	}
	
	public CSVExportOptions(char separatorCharacter, char quoteCharacter,
			char escapeCharacter, String lineTerminator,
			String characterEncoding) {
		this.separatorCharacter = separatorCharacter;
		this.quoteCharacter = quoteCharacter;
		this.escapeCharacter = escapeCharacter;
		this.lineTerminator = lineTerminator;
		this.characterEncoding = characterEncoding;
	}

	public char getSeparatorCharacter() {
		return separatorCharacter;
	}

	public void setSeparatorCharacter(char separatorCharacter) {
		this.separatorCharacter = separatorCharacter;
	}

	public char getQuoteCharacter() {
		return quoteCharacter;
	}

	public void setQuoteCharacter(char quoteCharacter) {
		this.quoteCharacter = quoteCharacter;
	}

	public char getEscapeCharacter() {
		return escapeCharacter;
	}

	public void setEscapeCharacter(char escapeCharacter) {
		this.escapeCharacter = escapeCharacter;
	}

	public String getLineTerminator() {
		return lineTerminator;
	}

	public void setLineTerminator(String lineTerminator) {
		this.lineTerminator = lineTerminator;
	}

	public String getCharacterEncoding() {
		return characterEncoding;
	}

	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}
	
}
