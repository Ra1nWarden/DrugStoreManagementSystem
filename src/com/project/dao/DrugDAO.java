package com.project.dao;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.project.model.Drug;

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
			result = statement.executeQuery("select * from drugs");
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
		drug.drugId = result.getInt("durg_id");
		drug.drugName = result.getString("drug_name");
		drug.price = result.getDouble("price");
		drug.providerId = result.getInt("provider_id");
		return drug;
	}

}
