package com.lapis.pfexporter.impl;

import com.lapis.pfexporter.api.FacetType;
import com.lapis.pfexporter.api.ITableCell;

public class TableCellImpl implements ITableCell {

	private String value;
	private int columnSpanCount;
	private int rowSpanCount;
	private FacetType facetType;
	
	public TableCellImpl(String value, int columnSpanCount, int rowSpanCount, FacetType facetType) {
		this.value = value;
		this.columnSpanCount = columnSpanCount;
		this.rowSpanCount = rowSpanCount;
		this.facetType = facetType;
	}

	public String getValue() {
		return value;
	}

	public int getColumnSpanCount() {
		return columnSpanCount;
	}

	public int getRowSpanCount() {
		return rowSpanCount;
	}

	public FacetType getFacetType() {
		return facetType;
	}
	
}
