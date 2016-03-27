package com.project.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import com.project.dao.UserDAO;
import com.project.model.User;
import com.project.model.UserTableModel;

public class UserTableWindow extends CommonTableWindow implements ComponentListener {

	private UserTableModel model;
	private UserDAO dao;

	public UserTableWindow() {
		super();
		frame.setTitle("系统用户管理");
		try {
			dao = new UserDAO();
			loadData();
		} catch (Exception e) {
			alertError("数据库错误", "未知错误，请联系管理员");
		}

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserInfoWindow window = new UserInfoWindow(dao);
				window.addComponentListener(UserTableWindow.this);
				window.show();
			}

		});

		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int idx = getSelectedOrAlert();
				if (idx > -1) {
					UserInfoWindow window = new UserInfoWindow(dao, model.getItemAt(idx));
					window.addComponentListener(UserTableWindow.this);
					window.show();
				}
			}

		});

		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int idx = getSelectedOrAlert();
				if (idx > -1) {
					User user = model.getItemAt(idx);
					try {
						if (dao.removeUser(user.username)) {
							alert("删除成功！");
							loadData();
						} else {
							alertError("删除失败", "未知错误！请联系管理员。");
						}
					} catch (Exception ex) {
						alertError("数据库错误", "请联系管理员。");
					}
				}
			}

		});
	}

	private void loadData() throws Exception {
		model = new UserTableModel(dao.getAllUsers());
		contentTable.setModel(model);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		try {
			loadData();
		} catch (Exception ex) {
			alertError("刷新列表失败！", "错误");
		}

	}

}
