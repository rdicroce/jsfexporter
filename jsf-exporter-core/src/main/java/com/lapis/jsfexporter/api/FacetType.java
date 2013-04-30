package com.lapis.jsfexporter.api;

public enum FacetType {
	HEADER, FOOTER;
	
	public String getFacetName() {
		return name().toLowerCase();
	}
}
