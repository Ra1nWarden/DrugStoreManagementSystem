package com.project.ui;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.project.dao.UserDAO;
import com.project.model.Provider;
import com.project.model.User;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

import javax.swing.JButton;

public class UserInfoWindow {

	private JFrame frame;
	private JTextField usernameField;
	private JTextField passwordField;
	private JCheckBox adminCheckbox;
	private boolean edit;
	private final UserDAO dao;
	private String username;

	/**
	 * Create the application.
	 */
	public UserInfoWindow(UserDAO dao) {
		initialize();
		this.dao = dao;
		this.edit = false;
	}

	public UserInfoWindow(UserDAO dao, User user) {
		initialize();
		this.dao = dao;
		this.edit = true;
		this.username = user.username;
		usernameField.setText(user.username);
		passwordField.setText(user.password);
		adminCheckbox.setSelected(user.admin);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("用户信息");
		frame.setBounds(100, 100, 341, 312);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane()
				.setLayout(new FormLayout(
						new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
								FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
								FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
								FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
								FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
								FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
								ColumnSpec.decode("default:grow"), },
						new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel label = new JLabel("用户名");
		frame.getContentPane().add(label, "6, 6, center, default");

		usernameField = new JTextField();
		frame.getContentPane().add(usernameField, "8, 6, center, center");
		usernameField.setColumns(10);

		JLabel label_1 = new JLabel("密码");
		frame.getContentPane().add(label_1, "6, 8, center, default");

		passwordField = new JTextField();
		frame.getContentPane().add(passwordField, "8, 8, center, center");
		passwordField.setColumns(10);

		JLabel label_2 = new JLabel("权限");
		frame.getContentPane().add(label_2, "6, 10, center, default");

		adminCheckbox = new JCheckBox("管理员权限");
		frame.getContentPane().add(adminCheckbox, "8, 10");

		JButton doneButton = new JButton("确定");
		frame.getContentPane().add(doneButton, "6, 16, center, center");
		doneButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				User user = getEditedUser();
				try {
					if (edit) {
						if (dao.updateUser(user, username)) {
							alert("修改成功");
							frame.setVisible(false);
						} else {
							alertError("修改失败，请联系管理员。", "数据错误");
						}
					} else {
						if (dao.addUser(user)) {
							alert("新增用户成功");
							frame.setVisible(false);
						} else {
							alertError("操作失败，请联系管理员。", "数据错误");
						}
					}
				} catch (Exception ex) {
					alertError("操作失败，未知错误。", "数据库错误");
				}
			}

		});

		JButton resetButton = new JButton("重置");
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				usernameField.setText("");
				passwordField.setText("");
			}

		});
		frame.getContentPane().add(resetButton, "8, 16, center, center");
	}

	public void addComponentListener(ComponentListener listener) {
		frame.addComponentListener(listener);
	}

	public void show() {
		frame.setVisible(true);
	}

	private User getEditedUser() {
		User user = new User();
		user.username = usernameField.getText();
		user.password = passwordField.getText();
		user.admin = adminCheckbox.isSelected();
		return user;
	}

	protected void alertError(String errorMsg, String title) {
		JOptionPane.showMessageDialog(frame, errorMsg, title, JOptionPane.ERROR_MESSAGE);
	}

	protected void alert(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}

}
