package com.lapis.pfexporter.api;

public interface ITabularExportType<T, C> extends IExportType<T, C> {

	void exportCell(ITableCell cell);
	void rowComplete();
	
}
