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

/**
 * Interface that defines a single cell in an {@link IExportRow}.
 * <p>
 * All methods must return valid values, although each individual export type
 * may not use all of them.
 * @author Richard
 *
 */
public interface IExportCell {

	/**
	 * Gets the "name" of this cell. This is used by some export types that produce
	 * output that is meant to be readable by a computer.
	 * <p>
	 * For example, the XML export type will use this as the name of the tag. If
	 * the list contains more than one String (like [foo, bar]), then the tags
	 * will be nested and the output will be <code>&lt;foo&gt;&lt;bar&gt;value&lt;/foo&gt;&lt;/bar&gt;</code>
	 * @return The name of this cell
	 */
	List<String> getName();
	
	/**
	 * Gets the value of this cell, which should be determined using an appropriate
	 * {@link com.lapis.jsfexporter.spi.IValueFormatter IValueFormatter}.
	 * {@link com.lapis.jsfexporter.util.ExportUtil ExportUtil} provides convenience methods for
	 * transforming JSF components to Strings using IValueFormatters.
	 * @return The value of this cell
	 */
	String getValue();
	
	/**
	 * Gets the number of columns this cell spans. If this cell does not span multiple columns,
	 * the value 1 should be returned.
	 * This is used by some export types that produce output that is meant to be human-readable.
	 * <p>
	 * For example, the Excel export type will merge columns across this number of cells.
	 * @return The number of columns this cell spans
	 */
	int getColumnSpanCount();
	
	/**
	 * Gets the number of rows this cell spans. If this cell does not span multiple rows, the value 1 should be returned.
	 * This is used by some export types that produce output that is meant to be human-readable.
	 * <p>
	 * For example, the Excel export type will merge rows across this number of cells.
	 * @return The number of rows this cell spans
	 */
	int getRowSpanCount();
	
}
