/*
 * #%L
 * Lapis JSF Exporter - Excel export type
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
package com.lapis.jsfexporter.excel;

/**
 * Configuration options for Excel export type.
 * @author Richard
 *
 */
public class ExcelExportOptions {

	private ExcelFileFormat format;
	
	/**
	 * Constructor for default options:
	 * <ul>
	 * <li>Format: XLSX</li>
	 * </ul>
	 */
	public ExcelExportOptions() {
		this.format = ExcelFileFormat.XLSX;
	}

	public ExcelExportOptions(ExcelFileFormat format) {
		this.format = format;
	}

	public ExcelFileFormat getFormat() {
		return format;
	}

	public void setFormat(ExcelFileFormat format) {
		this.format = format;
	}
	
}
