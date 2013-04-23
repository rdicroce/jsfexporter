package com.lapis.pfexporter.util;

import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.api.UIColumn;

import com.lapis.pfexporter.ValueFormatterFactory;
import com.lapis.pfexporter.api.FacetType;

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
