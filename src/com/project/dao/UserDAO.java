package com.project.dao;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.project.model.User;

public class UserDAO {

	private Connection connection;

	public UserDAO() throws Exception {
		Properties prop = new Properties();
		FileInputStream fileStream = new FileInputStream("database.properties");
		prop.load(fileStream);

		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		String dburl = prop.getProperty("dburl");

		connection = DriverManager.getConnection(dburl, username, password);
	}

	public List<User> getAllUsers() throws Exception {
		List<User> ret = new ArrayList<>();
		Statement statement = null;
		ResultSet result = null;

		try {
			statement = connection.createStatement();
			result = statement.executeQuery("select * from drugstore_info_system.users");
			while (result.next()) {
				ret.add(convertToUser(result));
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

	private User convertToUser(ResultSet result) throws Exception {
		User user = new User();
		user.username = result.getString("username");
		user.password = result.getString("password");
		user.admin = result.getBoolean("admin");
		return user;
	}

	public boolean removeUser(String username) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement("delete from drugstore_info_system.users where username = ?");
			statement.setString(1, username);
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

	public boolean addUser(User user) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(
					"insert into drugstore_info_system.users (username, password, admin) values (?, ?, ?)");
			statement.setString(1, user.username);
			statement.setString(2, user.password);
			statement.setBoolean(3, user.admin);
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

	public boolean updateUser(User user, String username) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(
					"update drugstore_info_system.users set username = ?, password = ?, admin = ? where username = ?");
			statement.setString(1, user.username);
			statement.setString(2, user.password);
			statement.setBoolean(3, user.admin);
			statement.setString(4, username);
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

	public User loginUser(String username, String password) throws Exception {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection
					.prepareStatement("select * from  drugstore_info_system.users where username = ? and password = ?");
			statement.setString(1, username);
			statement.setString(2, password);
			result = statement.executeQuery();
			if (result.next()) {
				User user = convertToUser(result);
				return user;
			} else {
				return null;
			}
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
