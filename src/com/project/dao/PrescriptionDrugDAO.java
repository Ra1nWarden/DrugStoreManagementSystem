package com.project.dao;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.project.model.PrescriptionDrug;

public class PrescriptionDrugDAO {

	private Connection connection;

	public PrescriptionDrugDAO() throws Exception {
		Properties prop = new Properties();
		FileInputStream fileStream = new FileInputStream("database.properties");
		prop.load(fileStream);

		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		String dburl = prop.getProperty("dburl");

		connection = DriverManager.getConnection(dburl, username, password);
	}

	public List<PrescriptionDrug> getAllDrugsForPrescription(int id) throws Exception {
		List<PrescriptionDrug> ret = new ArrayList<PrescriptionDrug>();
		PreparedStatement statement = null;
		ResultSet result = null;

		try {
			statement = connection.prepareStatement(
					"select * from drugstore_info_system.prescriptions join drugstore_info_system.drugs join drugstore_info_system.providers join drugstore_info_system.prescription_drugs on prescription_drugs.prescription_id = prescriptions.prescription_id and prescription_drugs.drug_id = drugs.drug_id and drugs.provider_id = providers.provider_id where prescriptions.prescription_id = ?");
			statement.setInt(1, id);
			result = statement.executeQuery();
			while (result.next()) {
				ret.add(convertToPrescriptionDrug(result));
			}
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (result != null) {
				result.close();
			}
		}
		return ret;
	}

	public boolean removePrescriptionDrug(int prescriptionId, int drugId) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(
					"delete from drugstore_info_system.prescription_drugs where prescription_id = ? and drug_id = ?");
			statement.setInt(1, prescriptionId);
			statement.setInt(2, drugId);
			return statement.executeUpdate() > 0;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (result != null) {
				result.close();
			}
		}
	}

	public boolean addPrescriptionDrug(PrescriptionDrug prescriptionDrug) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(
					"insert into drugstore_info_system.prescription_drugs (prescription_id, drug_id, amount, status) values (?, ?, ?, ?)");
			statement.setInt(1, prescriptionDrug.prescriptionId);
			statement.setInt(2, prescriptionDrug.drugId);
			statement.setInt(3, prescriptionDrug.amount);
			statement.setString(4, prescriptionDrug.status);
			return statement.executeUpdate() > 0;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (result != null) {
				result.close();
			}
		}
	}

	public boolean updatePrescriptionDrug(PrescriptionDrug prescriptionDrug, String oldStatus) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			if (!oldStatus.equals(prescriptionDrug.status)) {
				if (prescriptionDrug.status.equals("已发货")) {
					statement = connection
							.prepareStatement("select * from drugstore_info_system.drugs where drug_id = ?");
					statement.setInt(1, prescriptionDrug.drugId);
					result = statement.executeQuery();
					if (result.next()) {
						int orig = result.getInt("stock");
						if (orig >= prescriptionDrug.amount) {
							statement = connection.prepareStatement(
									"update drugstore_info_system.drugs set stock = stock - ? where drug_id = ?");
							statement.setInt(1, prescriptionDrug.amount);
							statement.setInt(2, prescriptionDrug.drugId);
							statement.executeUpdate();
						} else {
							return false;
						}
					} else {
						throw new IllegalArgumentException();
					}
				} else if (prescriptionDrug.status.equals("已退货") && oldStatus.equals("已发货")) {
					statement = connection.prepareStatement(
							"update drugstore_info_system.drugs set stock = stock + ? where drug_id = ?");
					statement.setInt(1, prescriptionDrug.amount);
					statement.setInt(2, prescriptionDrug.drugId);
					statement.executeUpdate();
				}
			}
			statement = connection.prepareStatement(
					"update drugstore_info_system.prescription_drugs set prescription_id = ?, drug_id = ?, amount = ?, status = ? where prescription_id = ? and drug_id = ?");
			statement.setInt(1, prescriptionDrug.prescriptionId);
			statement.setInt(2, prescriptionDrug.drugId);
			statement.setInt(3, prescriptionDrug.amount);
			statement.setString(4, prescriptionDrug.status);
			statement.setInt(5, prescriptionDrug.prescriptionId);
			statement.setInt(6, prescriptionDrug.drugId);
			return statement.executeUpdate() > 0;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (result != null) {
				result.close();
			}
		}
	}

	public PrescriptionDrug convertToPrescriptionDrug(ResultSet result) throws Exception {
		PrescriptionDrug drug = new PrescriptionDrug();
		drug.prescriptionId = result.getInt("prescription_id");
		drug.drugId = result.getInt("drug_id");
		drug.amount = result.getInt("amount");
		drug.status = result.getString("status");
		drug.drug = DrugDAO.convertToDrug(result);
		drug.provider = ProviderDAO.convertToProvider(result);
		return drug;
	}

}
