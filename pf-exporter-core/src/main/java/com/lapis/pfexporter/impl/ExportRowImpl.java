package com.lapis.pfexporter.impl;

import java.util.List;

import com.lapis.pfexporter.api.FacetType;
import com.lapis.pfexporter.api.IExportCell;
import com.lapis.pfexporter.api.IExportRow;

public class ExportRowImpl implements IExportRow {

	private List<String> name;
	private Object parentRowId;
	private FacetType facetType;
	private List<IExportCell> cells;
	
	public ExportRowImpl(List<String> name, Object parentRowId, FacetType facetType, List<IExportCell> cells) {
		this.name = name;
		this.parentRowId = parentRowId;
		this.facetType = facetType;
		this.cells = cells;
	}

	public List<String> getName() {
		return name;
	}

	public Object getParentRowId() {
		return parentRowId;
	}

	public FacetType getFacetType() {
		return facetType;
	}

	public List<IExportCell> getCells() {
		return cells;
	}
	
}
