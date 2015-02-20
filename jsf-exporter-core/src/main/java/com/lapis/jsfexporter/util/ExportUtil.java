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
package com.lapis.jsfexporter.util;

import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.lapis.jsfexporter.ValueFormatterFactory;

public class ExportUtil {

	public static String transformComponentsToString(FacesContext context, UIComponent... components) {
		return transformComponentsToString(context, Arrays.asList(components));
	}
	
	public static String transformComponentsToString(FacesContext context, List<UIComponent> components) {
		StringBuilder builder = new StringBuilder();
		
		for (UIComponent component : components) {
			if (component.isRendered()) {
				builder.append(ValueFormatterFactory.getValueFormatter(context, component).formatValue(context, component));
			}
		}
		
		return builder.toString();
	}
	
}
