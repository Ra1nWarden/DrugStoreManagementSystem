package com.project.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.project.dao.PrescriptionDrugDAO;

public class PrescriptionDrugTableModel extends AbstractTableModel {

	private static final String[] COLUMNS = { "药品名称", "供应商", "单价", "数量", "状态" };

	private List<PrescriptionDrug> drugs;

	public PrescriptionDrugTableModel(int prescriptionId) throws Exception {
		PrescriptionDrugDAO dao = new PrescriptionDrugDAO();
		drugs = dao.getAllDrugsForPrescription(prescriptionId);
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
		PrescriptionDrug drug = getItemAt(rowIndex);
		switch (columnIndex) {
		case 0:
			return drug.drug.drugName;
		case 1:
			return drug.provider.providerName;
		case 2:
			return drug.drug.price;
		case 3:
			return drug.amount;
		case 4:
			return drug.status;
		}
		return null;
	}

	@Override
	public String getColumnName(int pos) {
		return COLUMNS[pos];
	}

	public PrescriptionDrug getItemAt(int index) {
		return drugs.get(index);
	}

}
