/*
 * #%L
 * Lapis JSF Exporter Core
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
package com.lapis.jsfexporter.api;

import java.util.List;

import com.lapis.jsfexporter.spi.IExportSource;

/**
 * Interface that defines a single row to be passed to {@link IExportType#exportRow(IExportRow)}.
 * <p>
 * All methods must return valid values, although each individual export type
 * may not use all of them.
 * @author Richard
 *
 */
public interface IExportRow {

	/**
	 * Gets the "name" of this row. This is used by some export types that produce
	 * output that is meant to be readable by a computer.
	 * <p>
	 * For example, the XML export type will use this as the name of the tag. If
	 * the list contains more than one String (like [foo, bar]), then the tags
	 * will be nested and the output will be <code>&lt;foo&gt;&lt;bar&gt;...cells...&lt;/foo&gt;&lt;/bar&gt;</code>
	 * @return The name of this row
	 */
	List<String> getName();
	
	/**
	 * Gets the identifier of the row that is the parent of this row, as returned by
	 * {@link IExportType#exportRow(IExportRow)}. If this row has no parent, null should
	 * be returned.
	 * @return The identifier of the parent row
	 */
	Object getParentRowId();
	
	/**
	 * Gets the facet type of this row. If this row is not part of a facet, null should
	 * be returned.
	 * @return This row's facet type
	 */
	FacetType getFacetType();
	
	/**
	 * Gets the list of cells that are in this row. The list must contain exactly the same
	 * number of cells as returned by {@link IExportSource#getColumnCount(Object, Object)}.
	 * The list must not contain any null elements.
	 * @return The list of cells in this row
	 */
	List<IExportCell> getCells();
	
}
