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
