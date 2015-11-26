/*
 * #%L
 * Lapis JSF Exporter - Excel export type
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
import org.apache.poi.xwpf.usermodel.*;

public enum WordFileFormat {
	DOC("doc", "application/msword") {
		@Override
		public XWPFDocument createNewWorkbook() {
			XWPFDocument xwpfDocument = new XWPFDocument();
			return xwpfDocument;
		}
	},
	DOCX("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document") {
		@Override
		public XWPFDocument createNewWorkbook() {
			XWPFDocument xwpfDocument = new XWPFDocument();
			return xwpfDocument;
		}
	};
	
	private String fileExtension;
	private String mimeType;
	
	private WordFileFormat(String fileExtension, String mimeType) {
		this.fileExtension = fileExtension;
		this.mimeType = mimeType;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public String getMimeType() {
		return mimeType;
	}
	
	public abstract XWPFDocument createNewWorkbook();
	
}
