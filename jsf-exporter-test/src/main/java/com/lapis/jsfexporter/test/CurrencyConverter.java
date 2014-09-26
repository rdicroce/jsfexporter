package com.lapis.jsfexporter.test;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("com.lapis.jsfexporter.CurrencyConverter")
public class CurrencyConverter implements Converter{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null){
            return "€" + value.toString();
        }
        return null;
    }
}
