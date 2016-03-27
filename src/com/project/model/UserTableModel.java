package com.project.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class UserTableModel extends AbstractTableModel {

	private static final String[] COLUMNS = { "用户名", "密码", "管理员权限" };
	private List<User> users;

	public UserTableModel(List<User> users) {
		this.users = users;
	}

	@Override
	public int getRowCount() {
		return users.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMNS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User user = users.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return user.username;
		case 1:
			return user.password;
		case 2:
			return user.admin;
		}
		return null;
	}

	@Override
	public String getColumnName(int pos) {
		return COLUMNS[pos];
	}

	public User getItemAt(int row) {
		return users.get(row);
	}

}
