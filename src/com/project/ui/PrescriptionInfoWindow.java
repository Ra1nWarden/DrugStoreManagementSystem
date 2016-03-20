package com.project.ui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ComponentListener;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;


public class PrescriptionInfoWindow {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Create the application.
	 */
	public PrescriptionInfoWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("处方信息");
		frame.setBounds(100, 100, 465, 329);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(15dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(61dlu;default):grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel label = new JLabel("患者姓名");
		panel.add(label, "6, 6, center, default");
		
		textField = new JTextField();
		panel.add(textField, "8, 6, center, default");
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("患者年龄");
		panel.add(label_1, "6, 8, center, default");
		
		textField_1 = new JTextField();
		panel.add(textField_1, "8, 8, center, default");
		textField_1.setColumns(10);
		
		JLabel label_2 = new JLabel("患者性别");
		panel.add(label_2, "6, 10, center, default");
		
		JComboBox comboBox = new JComboBox();
		panel.add(comboBox, "8, 10, center, center");
		
		JLabel label_3 = new JLabel("医师姓名");
		panel.add(label_3, "6, 12, center, default");
		
		textField_2 = new JTextField();
		panel.add(textField_2, "8, 12, center, center");
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		panel.add(textField_3, "8, 14, center, center");
		textField_3.setColumns(10);
		
		JLabel label_4 = new JLabel("处方时间");
		panel.add(label_4, "6, 14, center, default");
		
		JButton btnNewButton = new JButton("确定");
		panel.add(btnNewButton, "6, 20");
		
		JButton btnNewButton_1 = new JButton("重置");
		panel.add(btnNewButton_1, "8, 20, center, default");
	}
	
	public void addComponentListener(ComponentListener listener) {
		frame.addComponentListener(listener);
	}
	
	public void show() {
		frame.setVisible(true);
	}

}
