/*
 * #%L
 * Lapis JSF Exporter Core
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
package com.lapis.jsfexporter.impl;

import java.util.List;

import com.lapis.jsfexporter.api.FacetType;
import com.lapis.jsfexporter.api.IExportCell;
import com.lapis.jsfexporter.api.IExportRow;

public class ExportRowImpl implements IExportRow {

	private List<String> name;
	private Object parentRowId;
	private FacetType facetType;
	private List<IExportCell> cells;
	
	public ExportRowImpl(List<String> name, Object parentRowId, FacetType facetType, List<IExportCell> cells) {
		this.name = name;
		this.parentRowId = parentRowId;
		this.facetType = facetType;
		this.cells = cells;
	}

	public List<String> getName() {
		return name;
	}

	public Object getParentRowId() {
		return parentRowId;
	}

	public FacetType getFacetType() {
		return facetType;
	}

	public List<IExportCell> getCells() {
		return cells;
	}
	
}
