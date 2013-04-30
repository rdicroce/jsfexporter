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
