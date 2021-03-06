package com.project.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import com.project.dao.ProviderDAO;
import com.project.model.Provider;
import com.project.model.ProviderTableModel;

public class ProviderTableWindow extends CommonTableWindow implements ComponentListener {

	private ProviderTableModel model;
	private ProviderDAO dao;

	public ProviderTableWindow() {
		super();
		frame.setTitle("供应商管理");
		try {
			dao = new ProviderDAO();
			loadData();
		} catch (Exception e) {
			alertError("数据库错误XX", "未知错误，请联系管理员");
			e.printStackTrace();
		}

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ProviderInfoWindow window = new ProviderInfoWindow(dao);
				window.addComponentListener(ProviderTableWindow.this);
				window.show();
			}

		});

		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int idx = getSelectedOrAlert();
				if (idx > -1) {
					ProviderInfoWindow window = new ProviderInfoWindow(dao, model.getItemAt(idx));
					window.addComponentListener(ProviderTableWindow.this);
					window.show();
				}
			}

		});

		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int idx = getSelectedOrAlert();
				if (idx > -1) {
					Provider provider = model.getItemAt(idx);
					try {
						if (dao.removeProvider(provider.providerId)) {
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
					ProviderTableWindow.this.loadData();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		});
	}

	private void loadData() throws Exception {
		model = new ProviderTableModel(dao.getAllProviders(filterText.getText()));
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
