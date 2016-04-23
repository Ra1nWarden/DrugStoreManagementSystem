package com.project.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import com.project.dao.PrescriptionDrugDAO;
import com.project.model.PrescriptionDrug;
import com.project.model.PrescriptionDrugTableModel;

public class PrescriptionDrugTableWindow extends CommonTableWindow implements ComponentListener {

	private PrescriptionDrugTableModel model;
	private PrescriptionDrugDAO dao;
	private final int prescriptionId;

	public PrescriptionDrugTableWindow(int prescriptionId) {
		super();
		this.prescriptionId = prescriptionId;
		frame.setTitle("处方药品管理");
		try {
			dao = new PrescriptionDrugDAO();
			loadData();
		} catch (Exception e) {
			alertError("数据库错误", "未知错误，请联系管理员");
		}

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PrescriptionDrugInfoWindow window = new PrescriptionDrugInfoWindow(dao, prescriptionId);
				window.addComponentListener(PrescriptionDrugTableWindow.this);
				window.show();
			}

		});

		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int idx = getSelectedOrAlert();
				if (idx > -1) {
					PrescriptionDrugInfoWindow window = new PrescriptionDrugInfoWindow(dao, prescriptionId,
							model.getItemAt(idx));
					window.addComponentListener(PrescriptionDrugTableWindow.this);
					window.show();
				}
			}

		});

		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int idx = getSelectedOrAlert();
				if (idx > -1) {
					PrescriptionDrug prescriptionDrug = model.getItemAt(idx);
					try {
						if (dao.removePrescriptionDrug(prescriptionDrug.prescriptionId, prescriptionDrug.drugId)) {
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

		filterText.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PrescriptionDrugTableWindow.this.loadData();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});
	}

	private void loadData() throws Exception {
		model = new PrescriptionDrugTableModel(prescriptionId, filterText.getText());
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

	public void addComponentListener(ComponentListener listener) {
		frame.addComponentListener(listener);
	}

}
