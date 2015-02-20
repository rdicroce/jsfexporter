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

import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;

import com.lapis.jsfexporter.spi.IValueFormatter;
import com.lapis.jsfexporter.util.ExportUtil;

public class PanelGroupFormatter implements IValueFormatter<HtmlPanelGroup> {

	@Override
	public Class<HtmlPanelGroup> getSupportedClass() {
		return HtmlPanelGroup.class;
	}

	@Override
	public int getPrecedence() {
		return 0;
	}

	@Override
	public String formatValue(FacesContext context, HtmlPanelGroup component) {
		return ExportUtil.transformComponentsToString(context, component.getChildren());
	}

}
