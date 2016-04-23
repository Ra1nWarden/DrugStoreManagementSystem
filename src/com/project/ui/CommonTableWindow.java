package com.project.ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;

public class CommonTableWindow {

	protected JFrame frame;
	protected JTable contentTable;
	protected JPanel buttonPanel;
	protected JButton addButton;
	protected JButton editButton;
	protected JButton deleteButton;
	protected JTextField filterText;

	/**
	 * Create the application.
	 */
	public CommonTableWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 580, 446);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		JScrollPane scrollPane = new JScrollPane();

		buttonPanel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
						.addComponent(buttonPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(33, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);

		contentTable = new JTable();
		contentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(contentTable);
		
		filterText = new JTextField();
		buttonPanel.add(filterText);
		filterText.setColumns(10);

		addButton = new JButton("添加");
		buttonPanel.add(addButton);

		editButton = new JButton("修改");
		buttonPanel.add(editButton);

		deleteButton = new JButton("删除");
		buttonPanel.add(deleteButton);
		frame.getContentPane().setLayout(groupLayout);
	}

	protected void alertError(String errorMsg, String title) {
		JOptionPane.showMessageDialog(frame, errorMsg, title, JOptionPane.ERROR_MESSAGE);
	}
	
	protected void alert(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}
	
	protected void show() {
		frame.setVisible(true);
	}
	
	protected int getSelectedOrAlert() {
		int idx = contentTable.getSelectedRow();
		if(idx == -1) {
			alertError("请选中要操作的对象", "错误");
		}
		return idx;
	}
	
}
