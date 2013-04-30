package com.lapis.jsfexporter.api;

import java.util.List;

public interface IExportRow {

	List<String> getName();
	Object getParentRowId();
	FacetType getFacetType();
	List<IExportCell> getCells();
	
}
