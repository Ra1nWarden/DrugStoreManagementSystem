package com.project.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;

import com.project.dao.DrugDAO;
import com.project.model.Drug;
import com.project.model.DrugTableModel;

public class DrugTableWindow extends CommonTableWindow implements ComponentListener {

	private DrugTableModel model;
	private DrugDAO dao;
	private JButton portButton;

	public DrugTableWindow() {
		super();
		frame.setTitle("药品管理");
		portButton = new JButton("进/退货");
		buttonPanel.add(portButton);

		try {
			dao = new DrugDAO();
			loadData();
		} catch (Exception e) {
			alertError("未知错误，请联系管理员", "数据库错误");
		}

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DrugInfoWindow window = new DrugInfoWindow(dao);
				window.addComponentListener(DrugTableWindow.this);
				window.show();
			}

		});

		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int idx = getSelectedOrAlert();
				if (idx > -1) {
					DrugInfoWindow window = new DrugInfoWindow(dao, model.getItemAt(idx));
					window.addComponentListener(DrugTableWindow.this);
					window.show();
				}
			}

		});

		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int idx = getSelectedOrAlert();
				if (idx > -1) {
					Drug drug = model.getItemAt(idx);
					try {
						if (dao.removeDrug(drug.drugId)) {
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

		portButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int idx = getSelectedOrAlert();
				if (idx > -1) {
					try {
						ImportExportWindow window = new ImportExportWindow(dao, model.getItemAt(idx));
						window.addComponentListener(DrugTableWindow.this);
						window.show();
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
					DrugTableWindow.this.loadData();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});
	}

	private void loadData() throws Exception {
		model = new DrugTableModel(dao.getAllDrugs(filterText.getText()));
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
