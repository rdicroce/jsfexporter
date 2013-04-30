package com.lapis.jsfexporter.api;

public abstract class AbstractExportType<T, C, R> implements IExportType<T, C, R> {

	@Override
	public void beginExport(int columnCount) {} // default implementation, do nothing

	@Override
	public void endExport() {} // default implementation, do nothing

}
