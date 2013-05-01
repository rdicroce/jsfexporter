/*
 * #%L
 * Lapis JSF Exporter - CSV export type
 * %%
 * Copyright (C) 2013 Lapis Software Associates
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
package com.lapis.jsfexporter.csv;

import java.io.Serializable;

/**
 * Configuration options for CSV export type. Most options are self-explanatory.
 * <p>
 * The one exception is character encoding. This may be the name of any character
 * encoding supported by the JVM, or "UTF-8-with-bom", which is equivalent to
 * the UTF-8 encoding except it prepends the UTF-8 byte-order mark to the CSV file.
 * This is useful if you want a CSV with non-ASCII characters to open properly in Excel.
 * @author Richard
 *
 */
public class CSVExportOptions implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private char separatorCharacter;
	private char quoteCharacter;
	private char escapeCharacter;
	private String lineTerminator;
	private String characterEncoding;
	
	/**
	 * Constructor for default options:
	 * <ul>
	 * <li>Separator: ,</li>
	 * <li>Quote: "</li>
	 * <li>Escape: "</li>
	 * <li>Line terminator: \n</li>
	 * <li>Character encoding: UTF-8</li>
	 * </ul>
	 */
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
