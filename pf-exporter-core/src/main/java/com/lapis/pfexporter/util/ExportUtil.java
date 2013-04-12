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
	
	public static String getColumnHeaderText(UIColumn column, FacesContext context) {
		String headerText = column.getHeaderText();
		if (headerText == null) {
			UIComponent header = column.getFacet(FacetType.HEADER.getFacetName());
			if (header != null) {
				headerText = ExportUtil.transformComponentsToString(context, header);
			}
		}
		return headerText;
	}
	
}
