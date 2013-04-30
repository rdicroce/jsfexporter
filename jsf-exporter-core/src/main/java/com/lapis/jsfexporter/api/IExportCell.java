package com.lapis.jsfexporter.api;

import java.util.List;

public interface IExportCell {

	List<String> getName();
	String getValue();
	int getColumnSpanCount();
	int getRowSpanCount();
	
}
