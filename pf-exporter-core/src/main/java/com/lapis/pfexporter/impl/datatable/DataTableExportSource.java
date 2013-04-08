package com.lapis.pfexporter.impl.datatable;

import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import com.lapis.pfexporter.api.IDataProvider;
import com.lapis.pfexporter.api.datatable.DataTableExportOptions;
import com.lapis.pfexporter.api.datatable.DataTableExportOptions.ExportRange;
import com.lapis.pfexporter.spi.IExportSource;

public class DataTableExportSource implements IExportSource<DataTable, DataTableExportOptions> {

	@Override
	public IDataProvider exportData(DataTable source, DataTableExportOptions configOptions, FacesContext context) {
		if (configOptions.getRange() == ExportRange.ALL) {
			if (source.isLazy()) {
				// TODO implement
			} else {
				return new AllRowsNonLazyDataProvider(source, context);
			}
		} else { // PAGE_ONLY
			// TODO implement
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<DataTable> getSourceType() {
		return DataTable.class;
	}

	@Override
	public DataTableExportOptions getDefaultConfigOptions() {
		return new DataTableExportOptions();
	}

}
