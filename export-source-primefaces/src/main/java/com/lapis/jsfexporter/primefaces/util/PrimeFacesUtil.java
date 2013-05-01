/*
 * #%L
 * Lapis JSF Exporter - PrimeFaces export sources
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
package com.lapis.jsfexporter.primefaces.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.api.UIColumn;

import com.lapis.jsfexporter.api.FacetType;
import com.lapis.jsfexporter.util.ExportUtil;

public class PrimeFacesUtil {

	public static String getColumnFacetText(UIColumn column, FacetType facetType, FacesContext context) {
		String facetText = facetType == FacetType.HEADER ? column.getHeaderText() : column.getFooterText();
		if (facetText == null) {
			UIComponent facet = column.getFacet(facetType.getFacetName());
			if (facet != null) {
				facetText = ExportUtil.transformComponentsToString(context, facet);
			}
		}
		return facetText;
	}
	
}
