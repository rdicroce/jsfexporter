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
package com.lapis.jsfexporter.impl.value;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;

import com.lapis.jsfexporter.spi.IValueFormatter;
import com.lapis.jsfexporter.util.ExportUtil;

public class CommandLinkFormatter implements IValueFormatter<HtmlCommandLink> {

	@Override
	public Class<HtmlCommandLink> getSupportedClass() {
		return HtmlCommandLink.class;
	}

	@Override
	public int getPrecedence() {
		return 0;
	}

	@Override
	public String formatValue(FacesContext context, HtmlCommandLink component) {
		// adapted from PrimeFaces exporter
		Object value = component.getValue();
		if (value == null) {
			for (UIComponent child : component.getChildren()) {
				if (child instanceof ValueHolder) {
					return ExportUtil.transformComponentsToString(context, child);
				}
			}
			
			return "";
		} else {
			return value.toString();
		}
	}

}
