package com.project.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.project.dao.ProviderDAO;
import com.project.model.Provider;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ProviderInfoWindow {

	private JFrame frame;
	private JTextField nameField;
	private JTextField addressField;
	private final ProviderDAO dao;
	private final boolean edit;
	private int providerId;

	/**
	 * Create the application.
	 */
	public ProviderInfoWindow(ProviderDAO dao) {
		initialize();
		this.dao = dao;
		edit = false;
	}

	public ProviderInfoWindow(ProviderDAO dao, Provider provider) {
		initialize();
		this.dao = dao;
		nameField.setText(provider.providerName);
		addressField.setText(provider.providerAddress);
		providerId = provider.providerId;
		edit = true;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("供应商信息");
		frame.setBounds(100, 100, 389, 281);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel label = new JLabel("供应商名称");
		panel.add(label, "6, 4, right, default");

		nameField = new JTextField();
		panel.add(nameField, "8, 4, fill, default");
		nameField.setColumns(10);

		JLabel label_1 = new JLabel("供应商地址");
		panel.add(label_1, "6, 8, right, default");

		addressField = new JTextField();
		panel.add(addressField, "8, 8, fill, default");
		addressField.setColumns(10);

		JButton doneButton = new JButton("确定");
		doneButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Provider provider = getEditedProvider();
				try {
					if (edit) {
						if (dao.updateProvider(provider)) {
							alert("修改成功");
							frame.setVisible(false);
						} else {
							alertError("修改失败，请联系管理员。", "数据错误");
						}
					} else {
						if (dao.addProvider(provider)) {
							alert("新增供应商成功");
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
		panel.add(doneButton, "6, 14");

		JButton resetButton = new JButton("重置");
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				nameField.setText("");
				addressField.setText("");
			}

		});
		panel.add(resetButton, "8, 14, center, default");
	}

	public void addComponentListener(ComponentListener listener) {
		frame.addComponentListener(listener);
	}

	public void show() {
		frame.setVisible(true);
	}

	private Provider getEditedProvider() {
		Provider provider = new Provider();
		provider.providerAddress = addressField.getText();
		provider.providerName = nameField.getText();
		if (edit) {
			provider.providerId = providerId;
		}
		return provider;
	}

	protected void alertError(String errorMsg, String title) {
		JOptionPane.showMessageDialog(frame, errorMsg, title, JOptionPane.ERROR_MESSAGE);
	}

	protected void alert(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}

}