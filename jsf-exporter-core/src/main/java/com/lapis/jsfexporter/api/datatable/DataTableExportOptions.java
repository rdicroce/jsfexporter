package com.lapis.jsfexporter.api.datatable;

import java.io.Serializable;

public class DataTableExportOptions implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum ExportRange {ALL, PAGE_ONLY}

	private ExportRange range;

	public DataTableExportOptions() {
		this.range = ExportRange.ALL;
	}
	
	public DataTableExportOptions(ExportRange range) {
		this.range = range;
	}

	public ExportRange getRange() {
		return range;
	}

	public void setRange(ExportRange range) {
		this.range = range;
	}

}
