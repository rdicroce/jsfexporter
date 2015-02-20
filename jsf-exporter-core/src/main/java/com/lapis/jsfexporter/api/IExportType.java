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
package com.lapis.jsfexporter.api;

import javax.faces.context.ExternalContext;

import com.lapis.jsfexporter.spi.IExportSource;

/**
 * Interface that defines a contract for taking in generic data from
 * any source and outputting it in a specific file format.
 * @author Richard
 *
 * @param <T>	The type of the context used by this export type. Generally, this
 * 				should be a high-level representation of the entire file produced
 * 				by this export type.
 * @param <C>	The type of the configuration options used by this export type.
 * 				Each export type may define its own configuration options.
 * @param <R>	The type of the row identifier used by this export type.
 */
public interface IExportType<T, C, R> {
	
	/**
	 * Gets the context being used internally by this exporter. The value returned
	 * by this method will be passed to any user-defined pre/post-processors.
	 * @return This exporter's internal context
	 */
	T getContext();
	
	/**
	 * Event method that is called by {@link com.lapis.jsfexporter.DataExporter DataExporter}
	 * <b>after</b> the pre-processor is invoked (if one is defined) but
	 * <b>before</b> {@link IExportSource#exportData(Object, Object, IExportType, javax.faces.context.FacesContext)}
	 * is invoked.
	 * @param columnCount The number of columns each {@link IExportRow} will have.
	 */
	void beginExport(int columnCount);
	
	/**
	 * Method that is called by {@link IExportSource} for each row the source wants to export.
	 * @param row The row to be exported
	 * @return A unique identifier for the row.
	 */
	R exportRow(IExportRow row);
	
	/**
	 * Event method that is called by {@link com.lapis.jsfexporter.DataExporter DataExporter}
	 * <b>after</b> {@link IExportSource#exportData(Object, Object, IExportType, javax.faces.context.FacesContext)}
	 * is invoked but <b>before</b> the post-processor (if one is defined) is invoked.
	 */
	void endExport();
	
	/**
	 * Returns the MIME type of the file produced by this export type. The returned value will be set
	 * as the Content-Type in the HTTP response.
	 * @return The MIME type of the produced file
	 */
	String getContentType();
	
	/**
	 * Returns the file extension of the file produced by this export type. The returned value will be
	 * appended to the user-defined filename and must not include the leading dot. For example, the CSV
	 * export type returns "csv", not ".csv".
	 * @return
	 */
	String getFileExtension();
	
	/**
	 * Method called by {@link com.lapis.jsfexporter.DataExporter DataExporter} <b>after</b>
	 * {@link #endExport()} to write the export to the client. Implementations may write the export using
	 * {@link ExternalContext#getResponseOutputStream()} or {@link ExternalContext#getResponseOutputWriter()}.
	 * <p>
	 * If necessary, implementations may also call other methods on the ExternalContext,
	 * such as {@link ExternalContext#setResponseCharacterEncoding(String)}, before writing the response.
	 * @param externalContext
	 * @throws Exception
	 */
	void writeExport(ExternalContext externalContext) throws Exception;
	
}
