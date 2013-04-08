package com.lapis.pfexporter.impl.value;

import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.lapis.pfexporter.spi.IValueFormatter;

public class ValueHolderFormatter implements IValueFormatter<ValueHolder> {

	@Override
	public Class<ValueHolder> getSupportedClass() {
		return ValueHolder.class;
	}

	@Override
	public String formatValue(FacesContext context, ValueHolder valueHolder) {
		// code adapted from PrimeFaces source
		UIComponent component = (UIComponent) valueHolder;
		
		if (component instanceof EditableValueHolder) {
			Object submittedValue = ((EditableValueHolder) component).getSubmittedValue();
			if (submittedValue != null) {
				return submittedValue.toString();
			}
		}

		Object value = valueHolder.getValue();
		if (value == null) {
			return "";
		}
		
		Converter converter = valueHolder.getConverter(); // try getting the converter from the component
		if (converter == null) { // no converter defined, try to find an appropriate one
			ValueExpression expr = component.getValueExpression("value");
			if (expr != null) {
				Class<?> valueType = expr.getType(context.getELContext());
				if (valueType != null) {
					converter = context.getApplication().createConverter(valueType);
				}
			}
		}
		
		if (converter == null) { // no converter defined and couldn't find one
			return value.toString();
		} else {
			return converter.getAsString(context, component, value);
		}
	}

	@Override
	public int getPrecedence() {
		return 0;
	}

}
