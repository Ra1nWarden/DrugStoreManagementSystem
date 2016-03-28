package com.project.app;

import com.project.dao.UserDAO;
import com.project.model.User;

public class UserManager {

	private UserDAO userDAO;
	private User loggedInUser;
	private static UserManager manager;

	private UserManager() {
		try {
			userDAO = new UserDAO();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}

	}

	public static UserManager getInstance() {
		if (manager == null) {
			manager = new UserManager();
		}
		return manager;
	}

	public boolean logIn(String username, String password) {
		try {
			User user = userDAO.loginUser(username, password);
			if (user == null) {
				return false;
			} else {
				loggedInUser = user;
				return true;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}

}
