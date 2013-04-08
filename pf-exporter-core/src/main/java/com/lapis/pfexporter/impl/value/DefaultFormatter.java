package com.lapis.pfexporter.impl.value;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.lapis.pfexporter.spi.IValueFormatter;

public class DefaultFormatter implements IValueFormatter<UIComponent> {

	@Override
	public Class<UIComponent> getSupportedClass() {
		return UIComponent.class;
	}

	@Override
	public int getPrecedence() {
		return Integer.MIN_VALUE;
	}

	@Override
	public String formatValue(FacesContext context, UIComponent component) {
		return component.toString();
	}

}
