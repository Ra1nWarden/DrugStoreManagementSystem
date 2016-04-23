package com.project.dao;

import java.io.FileInputStream;
import java.sql.*;
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

	public List<Provider> getAllProviders(String filter) throws Exception {
		List<Provider> ret = new ArrayList<Provider>();
		Statement statement = null;
		ResultSet result = null;

		try {
			statement = connection.createStatement();
			result = statement.executeQuery("select * from drugstore_info_system.providers where provider_name like '%" + filter + "%'");
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

	public static Provider convertToProvider(ResultSet result) throws Exception {
		Provider provider = new Provider();
		provider.providerAddress = result.getString("provider_address");
		provider.providerId = result.getInt("provider_id");
		provider.providerName = result.getString("provider_name");
		return provider;
	}

	public String getProviderNameForId(int id) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement("select provider_name from drugstore_info_system.providers where provider_id = ?");
			statement.setInt(1, id);
			result = statement.executeQuery();
			result.next();
			return result.getString(1);
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (result != null) {
				result.close();
			}
		}
	}

	public boolean removeProvider(int providerId) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement("delete from drugstore_info_system.providers where provider_id = ?");
			statement.setInt(1, providerId);
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

	public boolean addProvider(Provider provider) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection
					.prepareStatement("insert into providers (provider_name, provider_address) values (?, ?)");
			statement.setString(1, provider.providerName);
			statement.setString(2, provider.providerAddress);
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

	public boolean updateProvider(Provider provider) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(
					"update providers set provider_name = ?, provider_address = ? where provider_id = ?");
			statement.setString(1, provider.providerName);
			statement.setString(2, provider.providerAddress);
			statement.setInt(3, provider.providerId);
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
