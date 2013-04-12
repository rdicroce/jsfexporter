package com.lapis.pfexporter.api;

public interface IHierarchicalExportType<T, C> extends IExportType<T, C> {

	void enterChild(String name);
	void exportValue(String name, String value);
	void exitChild();
	
}
