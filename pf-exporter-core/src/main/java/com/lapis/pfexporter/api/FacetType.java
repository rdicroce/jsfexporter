package com.lapis.pfexporter.api;

public enum FacetType {
	HEADER, FOOTER;
	
	public String getFacetName() {
		return name().toLowerCase();
	}
}
