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
