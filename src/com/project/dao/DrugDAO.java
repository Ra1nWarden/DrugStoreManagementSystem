package com.project.dao;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.project.model.Drug;
import com.project.model.Provider;

public class DrugDAO {

	private Connection connection;

	public DrugDAO() throws Exception {
		Properties prop = new Properties();
		FileInputStream fileStream = new FileInputStream("database.properties");
		prop.load(fileStream);

		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		String dburl = prop.getProperty("dburl");

		connection = DriverManager.getConnection(dburl, username, password);
	}

	public List<Drug> getAllDrugs() throws Exception {
		List<Drug> ret = new ArrayList<Drug>();
		Statement statement = null;
		ResultSet result = null;

		try {
			statement = connection.createStatement();
			result = statement.executeQuery("select * from drugstore_info_system.drugs");
			while (result.next()) {
				ret.add(convertToDrug(result));
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

	private Drug convertToDrug(ResultSet result) throws Exception {
		Drug drug = new Drug();
		drug.drugId = result.getInt("drug_id");
		drug.drugName = result.getString("drug_name");
		drug.price = result.getDouble("price");
		drug.providerId = result.getInt("provider_id");
		drug.stock = result.getInt("stock");
		return drug;
	}

	public boolean removeDrug(int drugId) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement("delete from drugstore_info_system.drugs where drug_id = ?");
			statement.setInt(1, drugId);
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

	public boolean addDrug(Drug drug) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection
					.prepareStatement("insert into drugstore_info_system.drugs (drug_name, provider_id, price) values (?, ?, ?)");
			statement.setString(1, drug.drugName);
			statement.setInt(2, drug.providerId);
			statement.setDouble(3, drug.price);
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

	public boolean updateDrug(Drug drug) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(
					"update drugstore_info_system.drugs set drug_name = ?, provider_id = ?, price = ?, stock = ? where drug_id = ?");
			statement.setString(1, drug.drugName);
			statement.setInt(2, drug.providerId);
			statement.setDouble(3, drug.price);
			statement.setInt(4, drug.stock);
			statement.setInt(5, drug.drugId);
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
