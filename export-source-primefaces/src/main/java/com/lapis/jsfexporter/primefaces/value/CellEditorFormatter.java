package com.lapis.jsfexporter.primefaces.value;

import javax.faces.context.FacesContext;

import org.primefaces.component.celleditor.CellEditor;

import com.lapis.jsfexporter.spi.IValueFormatter;
import com.lapis.jsfexporter.util.ExportUtil;

public class CellEditorFormatter implements IValueFormatter<CellEditor> {

	@Override
	public Class<CellEditor> getSupportedClass() {
		return CellEditor.class;
	}

	@Override
	public int getPrecedence() {
		return 0;
	}

	@Override
	public String formatValue(FacesContext context, CellEditor component) {
		return ExportUtil.transformComponentsToString(context, component.getFacet("output"));
	}

}
