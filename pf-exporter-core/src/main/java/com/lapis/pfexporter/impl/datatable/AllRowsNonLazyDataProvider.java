package com.lapis.pfexporter.impl.datatable;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.datatable.DataTable;

import com.lapis.pfexporter.api.IDataProvider;
import com.lapis.pfexporter.api.IRow;
import com.lapis.pfexporter.impl.FlatRowImpl;

public class AllRowsNonLazyDataProvider implements IDataProvider {

	private DataTable table;
	private FacesContext context;
	private List<List<UIComponent>> columns;
	private List<DynamicColumn> dynamicColumns;
	private int rowIndex;
	private int rowCount;
	
	public AllRowsNonLazyDataProvider(DataTable table, FacesContext context) {
		this.table = table;
		this.context = context;
		rowCount = table.getRowCount();
		
		columns = new ArrayList<List<UIComponent>>();
		dynamicColumns = new ArrayList<DynamicColumn>();
		for (UIColumn col : table.getColumns()) {
			if (col.isRendered() && col.isExportable()) {
				if (col instanceof DynamicColumn) {
					dynamicColumns.add((DynamicColumn) col);
				}
				
				columns.add(col.getChildren());
			}
		}
	}

	@Override
	public boolean hasNext() {
		return rowIndex < rowCount;
	}

	@Override
	public IRow next() {
		table.setRowIndex(rowIndex++);
		
		for (DynamicColumn col : dynamicColumns) {
			col.applyModel();
		}
		
		return new FlatRowImpl(columns.iterator(), context);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
