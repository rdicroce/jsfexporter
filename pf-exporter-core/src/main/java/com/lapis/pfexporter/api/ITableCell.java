package com.lapis.pfexporter.api;

public interface ITableCell {

	String getValue();
	int getColumnSpanCount();
	int getRowSpanCount();
	FacetType getFacetType();
	
}
