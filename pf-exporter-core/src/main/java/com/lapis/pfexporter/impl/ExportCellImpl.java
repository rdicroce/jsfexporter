package com.lapis.pfexporter.impl;

import java.util.List;

import com.lapis.pfexporter.api.IExportCell;

public class ExportCellImpl implements IExportCell {

	private List<String> name;
	private String value;
	private int columnSpanCount;
	private int rowSpanCount;
	
	public ExportCellImpl(List<String> name, String value, int columnSpanCount, int rowSpanCount) {
		this.name = name;
		this.value = value;
		this.columnSpanCount = columnSpanCount;
		this.rowSpanCount = rowSpanCount;
	}

	public List<String> getName() {
		return name;
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
	
}
