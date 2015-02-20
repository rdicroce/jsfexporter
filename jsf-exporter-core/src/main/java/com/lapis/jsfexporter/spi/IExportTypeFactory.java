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
package com.lapis.jsfexporter.spi;

import java.util.ServiceLoader;

import com.lapis.jsfexporter.api.IExportType;

/**
 * Interface that defines a contract for creating {@link IExportType} instances.
 * <p>
 * Implementations must register themselves with the {@link ServiceLoader} by
 * including a file named <code>com.lapis.jsfexporter.spi.IExportTypeFactory</code>
 * in the <code>META-INF/services</code> directory. The file must contain the
 * fully-qualified name of the class implementing this interface.
 * @author Richard
 *
 * @param <T>	The type of the context used by the produced export type.
 * @param <C>	The type of the configuration options used by the produced export type.
 * @param <R>	The type of the row identifier used by the produced export type.
 */
public interface IExportTypeFactory<T, C, R> {

	/**
	 * Creates a new export type instance, configured with the given options.
	 * @param configOptions The configuration options in use for the export.
	 * @return A new export type instance, configured appropriately
	 */
	IExportType<T, C, R> createNewExporter(C configOptions);
	
	/**
	 * Gets the default configuration options for the produced export type. The options returned
	 * by this method will be used during the export process if the user did not define any.
	 * @return The default configuration options for this export type.
	 */
	C getDefaultConfigOptions();
	
	/**
	 * Gets the unique identifier for this export type factory. The returned value will be
	 * matched against the fileType parameter on the <code>dataExporter</code> tag to determine
	 * which export type should be used during a given export. The value may be the same as
	 * the file type produced by instances of the export type, but may be something else if
	 * that is convenient or if an export type can produce multiple types of files.
	 * @return A unique identifier for this export type factory.
	 */
	String getExportTypeId();
	
}
