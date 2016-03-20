package com.project.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.project.dao.DrugDAO;
import com.project.dao.ProviderDAO;

public class DrugTableModel extends AbstractTableModel {

	private static final String[] COLUMNS = { "药品名称", "供应商", "单价" };

	private List<Drug> drugs;
	private ProviderDAO providerDAO;

	public DrugTableModel() throws Exception {
		DrugDAO drugDAO = new DrugDAO();
		drugs = drugDAO.getAllDrugs();
	}

	public DrugTableModel(List<Drug> drugs) throws Exception {
		this.drugs = drugs;
		providerDAO = new ProviderDAO();
	}

	@Override
	public int getRowCount() {
		return drugs.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMNS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Drug item = drugs.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return item.drugName;
		case 1:
			try {
				return providerDAO.getFieldForId("provider_name", item.drugId);
			} catch (Exception e) {
				return "未知供应商";
			}
		case 2:
			return item.price;
		}
		return null;
	}
	
	@Override
	public String getColumnName(int pos) {
		return COLUMNS[pos];
	}
	
	public Drug getItemAt(int row) {
		return drugs.get(row);
	}

}
