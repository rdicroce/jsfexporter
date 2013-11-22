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
