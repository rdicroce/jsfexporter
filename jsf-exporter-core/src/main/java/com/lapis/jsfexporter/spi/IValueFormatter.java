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

import com.lapis.jsfexporter.api.IExportCell;
import com.lapis.jsfexporter.api.IExportType;

/**
 * Interface that defines a contract for formatting the value of a JSF component as a String.
 * <p>
 * Implementations must register themselves with the {@link ServiceLoader} by
 * including a file named <code>com.lapis.jsfexporter.spi.IValueFormatter</code>
 * in the <code>META-INF/services</code> directory. The file must contain the
 * fully-qualified name of the class implementing this interface.
 * @author Richard
 *
 * @param <T>	The type of JSF component this formatter is capable of formatting.
 * 				May be any class or interface.
 */
public interface IValueFormatter<T> {

	/**
	 * Gets the type of the component this formatter is capable of processing. The returned
	 * value will be used to find a matching formatter for each component that an
	 * {@link IExportSource} wants to export as a value. A formatter will be considered
	 * a match if the candidate component is of type T or any type that inherits from T.
	 * @return The class type of the component this formatter is capable of processing.
	 */
	Class<T> getSupportedClass();
	
	/**
	 * Gets the precedence of this formatter. If multiple formatters match a candidate
	 * component, the formatter with the highest precedence will be used. If multiple
	 * formatters have the same highest precedence, it is undefined which formatter
	 * will be used.
	 * @return The precedence of this formatter.
	 */
	int getPrecedence();
	
	/**
	 * Formats the value of the given component as a String. An {@link IExportSource} will
	 * pass the returned String to an {@link IExportType} as the value in an {@link IExportCell}.
	 * @param context	The FacesContext for the current request.
	 * @param component	The component to be formatted.
	 * @return The String value of the component
	 */
	String formatValue(FacesContext context, T component);
	
}
