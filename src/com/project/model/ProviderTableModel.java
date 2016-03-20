package com.project.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ProviderTableModel extends AbstractTableModel {

	private static final String[] COLUMNS = { "供应商名称", "供应商地址" };
	private List<Provider> providers;

	public ProviderTableModel(List<Provider> providers) {
		this.providers = providers;
	}

	@Override
	public int getRowCount() {
		return providers.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMNS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Provider provider = providers.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return provider.providerName;
		case 1:
			return provider.providerAddress;
		}
		return null;
	}

	@Override
	public String getColumnName(int pos) {
		return COLUMNS[pos];
	}

	public Provider getItemAt(int row) {
		return providers.get(row);
	}

}
