package com.project.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.project.model.Drug;
import com.project.model.Prescription;

public class PrescriptionDAO {

	private Connection connection;

	public PrescriptionDAO() throws Exception {
		Properties prop = new Properties();
		FileInputStream fileStream = new FileInputStream("database.properties");
		prop.load(fileStream);

		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		String dburl = prop.getProperty("dburl");

		connection = DriverManager.getConnection(dburl, username, password);
	}

	public List<Prescription> getAllPrescription() throws Exception {
		List<Prescription> ret = new ArrayList<Prescription>();
		Statement statement = null;
		ResultSet result = null;

		try {
			statement = connection.createStatement();
			result = statement.executeQuery("select * from drugstore_info_system.prescriptions");
			while (result.next()) {
				ret.add(convertToPrescription(result));
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

	private Prescription convertToPrescription(ResultSet result) throws Exception {
		Prescription prescription = new Prescription();
		prescription.patientAge = result.getInt("patient_age");
		prescription.patientName = result.getString("patient_name");
		prescription.patientSex = result.getString("patient_sex");
		prescription.physicianName = result.getString("physician_name");
		prescription.prescriptionDate = result.getDate("prescription_date");
		prescription.prescriptionId = result.getInt("prescription_id");
		return prescription;
	}

	public boolean removePrescription(int prescriptionId) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection
					.prepareStatement("delete from drugstore_info_system.prescriptions where prescription_id = ?");
			statement.setInt(1, prescriptionId);
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

	public boolean addPrescription(Prescription prescription) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(
					"insert into drugstore_info_system.prescriptions (patient_name, patient_sex, patient_age, physician_name, prescription_date) values (?, ?, ?, ?, ?)");
			statement.setString(1, prescription.patientName);
			statement.setString(2, prescription.patientSex);
			statement.setInt(3, prescription.patientAge);
			statement.setString(4, prescription.physicianName);
			statement.setDate(5, prescription.prescriptionDate);
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

	public boolean updatePrescription(Prescription prescription) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(
					"update drugstore_info_system.prescriptions set patient_name = ?, patient_sex = ?, patient_age = ?, physician_name = ?, prescription_date = ? where prescription_id = ?");
			statement.setString(1, prescription.patientName);
			statement.setString(2, prescription.patientSex);
			statement.setInt(3, prescription.patientAge);
			statement.setString(4, prescription.physicianName);
			statement.setDate(5, prescription.prescriptionDate);
			statement.setInt(6, prescription.prescriptionId);
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

}
