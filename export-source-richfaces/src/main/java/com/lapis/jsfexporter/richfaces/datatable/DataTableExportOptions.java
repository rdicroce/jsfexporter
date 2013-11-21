/*
 * #%L
 * Lapis JSF Exporter - RichFaces export sources
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
package com.lapis.jsfexporter.richfaces.datatable;

import java.io.Serializable;

/**
 * Configuration options for rich:dataTable export source.
 * @author Richard
 *
 */
public class DataTableExportOptions implements Serializable {
	private static final long serialVersionUID = 1L;

	private DataTableExportRange range;

	/**
	 * Constructor for default options:
	 * <ul>
	 * <li>Range: ALL</li>
	 * </ul>
	 */
	public DataTableExportOptions() {
		this.range = DataTableExportRange.ALL;
	}
	
	public DataTableExportOptions(DataTableExportRange range) {
		this.range = range;
	}

	public DataTableExportRange getRange() {
		return range;
	}

	public void setRange(DataTableExportRange range) {
		this.range = range;
	}

}
