package com.project.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class PrescriptionTableModel extends AbstractTableModel {

	private static final String[] COLUMNS = { "患者姓名", "患者性别", "患者年龄", "医师姓名", "处方时间" };
	private List<Prescription> prescriptions;

	public PrescriptionTableModel(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	@Override
	public int getRowCount() {
		return prescriptions.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMNS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Prescription prescription = prescriptions.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return prescription.patientName;
		case 1:
			return prescription.patientSex;
		case 2:
			return prescription.patientAge;
		case 3:
			return prescription.physicianName;
		case 4:
			return prescription.prescriptionDate;
		}
		return null;
	}
	
	@Override
	public String getColumnName(int pos) {
		return COLUMNS[pos];
	}

	public Prescription getItemAt(int row) {
		return prescriptions.get(row);
	}

}
