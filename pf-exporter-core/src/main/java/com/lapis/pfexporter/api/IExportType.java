package com.lapis.pfexporter.api;

import javax.faces.context.ExternalContext;

public interface IExportType<T, C> {
	
	T getContext();
	void writeExport(ExternalContext externalContext) throws Exception;
	
}
