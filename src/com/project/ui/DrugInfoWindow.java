package com.project.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.util.List;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.project.dao.DrugDAO;
import com.project.dao.ProviderDAO;
import com.project.model.Drug;
import com.project.model.Provider;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class DrugInfoWindow {

	private JFrame frame;
	private JTextField nameField;
	private JTextField priceField;
	private List<Provider> providers;
	private JComboBox<Provider> providerBox;
	private final DrugDAO dao;
	private final boolean edit;
	private int drugId;
	private int stock;

	/**
	 * Create the application.
	 */
	public DrugInfoWindow(DrugDAO dao) {
		initialize();
		this.dao = dao;
		edit = false;
	}

	public DrugInfoWindow(DrugDAO dao, Drug drug) {
		initialize();
		this.dao = dao;
		edit = true;
		drugId = drug.drugId;
		stock = drug.stock;
		nameField.setText(drug.drugName);
		priceField.setText(Double.toString(drug.price));
		for (Provider provider : providers) {
			if (provider.providerId == drug.providerId) {
				providerBox.setSelectedItem(provider);
				break;
			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 489, 310);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setTitle("药品信息");

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel label = new JLabel("药品名称");
		panel.add(label, "10, 8, center, default");

		nameField = new JTextField();
		panel.add(nameField, "12, 8, center, default");
		nameField.setColumns(10);

		JLabel label_1 = new JLabel("供应商");
		panel.add(label_1, "10, 10, center, default");

		providerBox = new JComboBox<Provider>();
		try {
			ProviderDAO providerDAO = new ProviderDAO();
			providers = providerDAO.getAllProviders();
			for (Provider provider : providers) {
				providerBox.addItem(provider);
			}
		} catch (Exception ex) {
			alertError("无法加载供应商信息", "加载失败");
			frame.setVisible(false);
		}
		panel.add(providerBox, "12, 10, center, default");

		JLabel label_2 = new JLabel("药品单价");
		panel.add(label_2, "10, 12, center, default");

		priceField = new JTextField();
		panel.add(priceField, "12, 12, center, default");
		priceField.setColumns(10);

		JButton doneButton = new JButton("确定");
		doneButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Drug drug = null;
				try {
					drug = getEditedDrug();
				} catch (Exception ex) {
					alertError("输入不合法！", "数据错误");
				}
				if (drug == null) {
					return;
				}
				try {
					if (edit) {
						if (dao.updateDrug(drug)) {
							alert("修改成功");
							frame.setVisible(false);
						} else {
							alertError("修改失败，请联系管理员。", "数据错误");
						}
					} else {
						if (dao.addDrug(drug)) {
							alert("新增药品成功");
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
		panel.add(doneButton, "10, 16, center, default");

		JButton resetButton = new JButton("重置");
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				nameField.setText("");
				priceField.setText("");
			}

		});
		panel.add(resetButton, "12, 16, center, default");
	}

	public void show() {
		frame.setVisible(true);
	}

	public void addComponentListener(ComponentListener listener) {
		frame.addComponentListener(listener);
	}

	protected void alertError(String errorMsg, String title) {
		JOptionPane.showMessageDialog(frame, errorMsg, title, JOptionPane.ERROR_MESSAGE);
	}

	protected void alert(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}

	private Drug getEditedDrug() throws Exception {
		Drug drug = new Drug();
		drug.drugName = nameField.getText();
		drug.price = Double.parseDouble(priceField.getText());
		drug.providerId = ((Provider) providerBox.getSelectedItem()).providerId;
		if (edit) {
			drug.drugId = drugId;
			drug.stock = stock;
		} else {
			drug.stock = 0;
		}
		return drug;
	}

}
