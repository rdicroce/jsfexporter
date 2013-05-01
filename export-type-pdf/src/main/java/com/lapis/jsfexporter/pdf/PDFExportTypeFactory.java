/*
 * #%L
 * Lapis JSF Exporter - PDF export type
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
package com.lapis.jsfexporter.pdf;

import com.lapis.jsfexporter.api.IExportType;
import com.lapis.jsfexporter.spi.IExportTypeFactory;
import com.lowagie.text.Document;

public class PDFExportTypeFactory implements IExportTypeFactory<Document, Void, Integer> {

	@Override
	public IExportType<Document, Void, Integer> createNewExporter(Void configOptions) {
		return new PDFExportType();
	}

	@Override
	public Void getDefaultConfigOptions() {
		return null;
	}

	@Override
	public String getExportTypeId() {
		return "pdf";
	}

}
