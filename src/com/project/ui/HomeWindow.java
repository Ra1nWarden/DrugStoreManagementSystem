package com.project.ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormSpecs;

public class HomeWindow {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public HomeWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("医院药房管理系统");
		frame.setBounds(100, 100, 314, 226);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
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
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JButton basicInformationButton = new JButton("基础信息管理");
		panel.add(basicInformationButton, "6, 4");
		
		JButton importButton = new JButton("进货管理");
		panel.add(importButton, "8, 4");
		
		JButton exportButton = new JButton("药品发放管理");
		panel.add(exportButton, "6, 8");
		
		JButton stockButton = new JButton("库存管理");
		panel.add(stockButton, "8, 8");
		
		JButton statsButton = new JButton("查询统计");
		panel.add(statsButton, "6, 12");
		
		JButton systemButton = new JButton("系统管理");
		panel.add(systemButton, "8, 12");
		
	}

	public void setVisible(boolean vis) {
		frame.setVisible(vis);
	}

}
