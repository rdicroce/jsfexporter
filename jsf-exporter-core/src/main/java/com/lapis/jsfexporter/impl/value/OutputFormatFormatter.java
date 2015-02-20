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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlOutputFormat;
import javax.faces.context.FacesContext;

import com.lapis.jsfexporter.spi.IValueFormatter;

public class OutputFormatFormatter implements IValueFormatter<HtmlOutputFormat> {

	@Override
	public Class<HtmlOutputFormat> getSupportedClass() {
		return HtmlOutputFormat.class;
	}

	@Override
	public int getPrecedence() {
		return 1000;
	}

	@Override
	public String formatValue(FacesContext context, HtmlOutputFormat component) {
		// adapted from org.apache.myfaces.renderkit.html.HtmlFormatRenderer
		List<Object> formatArgs = new ArrayList<Object>();
		for (UIComponent child : component.getChildren()) {
			if (child instanceof UIParameter) {
				UIParameter param = (UIParameter) child;
				if (!param.isDisable() && param.isRendered()) {
					formatArgs.add(param.getValue());
				}
			}
		}
		
		return new MessageFormat(component.getValue().toString(), context.getViewRoot().getLocale()).format(formatArgs.toArray());
	}

}
