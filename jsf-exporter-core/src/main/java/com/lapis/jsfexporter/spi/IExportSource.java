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
package com.lapis.jsfexporter.spi;

import java.util.ServiceLoader;

import javax.faces.context.FacesContext;

import com.lapis.jsfexporter.api.IExportType;

/**
 * Interface that defines a contract for processing any JSF component and
 * outputting the component's contents in a generic format.
 * <p>
 * Implementations must register themselves with the {@link ServiceLoader} by
 * including a file named <code>com.lapis.jsfexporter.spi.IExportSource</code>
 * in the <code>META-INF/services</code> directory. The file must contain the
 * fully-qualified name of the class implementing this interface.
 * @author Richard
 *
 * @param <T>	The type of the component this export source is capable of processing.
 * @param <C>	The type of the configuration options used by this export source.
 * 				Each export source may define its own configuration options.
 */
public interface IExportSource<T, C> {

	/**
	 * Gets the class type of the component this export source is capable of processing.
	 * @return The class type of the component this export source is capable of processing.
	 */
	Class<T> getSourceType();
	
	/**
	 * Gets the default configuration options for this export source. The options returned
	 * by this method will be used during the export process if the user did not define any.
	 * @return The default configuration options for this export source.
	 */
	C getDefaultConfigOptions();
	
	/**
	 * Gets the number of columns that will be exported from the given source instance using
	 * the given configuration options.
	 * @param source		A component instance of the type supported by this source.
	 * @param configOptions	The configuration options in use for the export.
	 * @return
	 */
	int getColumnCount(T source, C configOptions);
	
	/**
	 * Method that is called by {@link com.lapis.jsfexporter.DataExporter DataExporter} to perform
	 * the export. Implementations should invoke {@link IExportType#exportRow(com.lapis.jsfexporter.api.IExportRow)}
	 * as many times as needed, but must not call any other IExportType methods!
	 * @param source		A component instance of the type supported by this source.
	 * @param configOptions	The configuration options in use for the export.
	 * @param exporter		An instance of the export type in use for the export.
	 * @param context		The FacesContext for the current request.
	 * @throws Exception	Implementations may throw any exception for any reason. Exceptions thrown
	 * 						will propagate out and be processed by the normal JSF exception handling
	 * 						mechanism.
	 */
	void exportData(T source, C configOptions, IExportType<?, ?, ?> exporter, FacesContext context) throws Exception;

}
