package com.project.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.project.model.Provider;

public class ProviderDAO {

	private Connection connection;

	public ProviderDAO() throws Exception {
		Properties prop = new Properties();
		FileInputStream fileStream = new FileInputStream("database.properties");
		prop.load(fileStream);

		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		String dburl = prop.getProperty("dburl");

		connection = DriverManager.getConnection(dburl, username, password);
	}

	public List<Provider> getAllProviders() throws Exception {
		List<Provider> ret = new ArrayList<Provider>();
		Statement statement = null;
		ResultSet result = null;

		try {
			statement = connection.createStatement();
			result = statement.executeQuery("select * from providers");
			while (result.next()) {
				ret.add(convertToProvider(result));
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

	private Provider convertToProvider(ResultSet result) throws Exception {
		Provider provider = new Provider();
		provider.providerAddress = result.getString("provider_address");
		provider.providerId = result.getInt("provider_id");
		provider.providerName = result.getString("provider_name");
		return provider;
	}

}
