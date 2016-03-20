package com.project.ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		frame.setBounds(100, 100, 314, 180);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JButton prescriptionManagement = new JButton("处方管理");
		panel.add(prescriptionManagement, "6, 4, center, default");

		JButton drugManagement = new JButton("药品管理");
		panel.add(drugManagement, "8, 4, center, default");

		JButton providerManagement = new JButton("供应商管理");
		providerManagement.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ProviderTableWindow providerWindow = new ProviderTableWindow();
				providerWindow.show();
			}

		});
		panel.add(providerManagement, "6, 8, center, default");

		JButton systemManagement = new JButton("系统管理");
		panel.add(systemManagement, "8, 8, center, default");

	}

	public void setVisible(boolean vis) {
		frame.setVisible(vis);
	}

}
