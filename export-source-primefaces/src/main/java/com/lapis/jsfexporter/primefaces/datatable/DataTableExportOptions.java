/*
 * #%L
 * Lapis JSF Exporter - PrimeFaces export sources
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
package com.lapis.jsfexporter.primefaces.datatable;

import java.io.Serializable;

public class DataTableExportOptions implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum ExportRange {ALL, PAGE_ONLY}

	private ExportRange range;

	public DataTableExportOptions() {
		this.range = ExportRange.ALL;
	}
	
	public DataTableExportOptions(ExportRange range) {
		this.range = range;
	}

	public ExportRange getRange() {
		return range;
	}

	public void setRange(ExportRange range) {
		this.range = range;
	}

}
