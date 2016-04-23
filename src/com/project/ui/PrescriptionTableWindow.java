package com.project.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;

import com.project.dao.PrescriptionDAO;
import com.project.model.Prescription;
import com.project.model.PrescriptionTableModel;

public class PrescriptionTableWindow extends CommonTableWindow implements ComponentListener {

	private PrescriptionTableModel model;
	private PrescriptionDAO dao;

	public PrescriptionTableWindow() {
		super();
		frame.setTitle("处方管理");
		try {
			dao = new PrescriptionDAO();
			loadData();
		} catch (Exception e) {
			alertError("数据库错误", "未知错误，请联系管理员");
		}

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PrescriptionInfoWindow window = new PrescriptionInfoWindow(dao);
				window.addComponentListener(PrescriptionTableWindow.this);
				window.show();
			}

		});

		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int idx = getSelectedOrAlert();
				if (idx > -1) {
					PrescriptionInfoWindow window = new PrescriptionInfoWindow(dao, model.getItemAt(idx));
					window.addComponentListener(PrescriptionTableWindow.this);
					window.show();
				}
			}

		});

		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int idx = getSelectedOrAlert();
				if (idx > -1) {
					Prescription prescription = model.getItemAt(idx);
					try {
						if (dao.removePrescription(prescription.prescriptionId)) {
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

		JButton checkDrugButton = new JButton("查看处方药品");
		buttonPanel.add(checkDrugButton);
		checkDrugButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int idx = getSelectedOrAlert();
				if (idx > -1) {
					Prescription prescription = model.getItemAt(idx);
					PrescriptionDrugTableWindow window = new PrescriptionDrugTableWindow(prescription.prescriptionId);
					window.addComponentListener(PrescriptionTableWindow.this);
					window.show();
				}

			}

		});

		filterText.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PrescriptionTableWindow.this.loadData();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});
	}

	private void loadData() throws Exception {
		model = new PrescriptionTableModel(dao.getAllPrescription(filterText.getText()));
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
