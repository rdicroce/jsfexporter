package com.lapis.pfexporter.impl;

import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.lapis.pfexporter.ValueFormatterFactory;
import com.lapis.pfexporter.api.IRow;

public class FlatRowImpl implements IRow {

	private Iterator<List<UIComponent>> columns;
	private FacesContext context;
	
	public FlatRowImpl(Iterator<List<UIComponent>> columns, FacesContext context) {
		this.columns = columns;
		this.context = context;
	}

	@Override
	public boolean hasNext() {
		return columns.hasNext();
	}

	@Override
	public String next() {
		StringBuilder builder = new StringBuilder();
		
		List<UIComponent> components = columns.next();
		for (UIComponent component : components) {
			if (component.isRendered()) {
				builder.append(ValueFormatterFactory.getValueFormatter(context, component).formatValue(context, component));
			}
		}
		
		return builder.toString();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
