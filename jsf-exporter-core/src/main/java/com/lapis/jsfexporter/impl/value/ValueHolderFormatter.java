/*
 * #%L
 * Lapis JSF Exporter Core
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
package com.lapis.jsfexporter.impl.value;

import javax.el.PropertyNotFoundException;
import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.lapis.jsfexporter.spi.IValueFormatter;

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

                try {
                    Class<?> valueType = expr.getType(context.getELContext());
                    if (valueType != null) {
                        converter = context.getApplication().createConverter(valueType);
                    }
                } catch (PropertyNotFoundException e) {
                    //EL being evaluated is not a property, but a method
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
