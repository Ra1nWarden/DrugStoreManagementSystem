package com.project.ui;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.project.dao.DrugDAO;
import com.project.dao.ProviderDAO;
import com.project.model.Drug;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

import javax.swing.JButton;

public class ImportExportWindow {

	private JFrame frame;
	private JTextField nameField;
	private JTextField providerField;
	private JTextField deltaField;
	private DrugDAO dao;
	private final Drug drug;

	/**
	 * Create the application.
	 */
	public ImportExportWindow(DrugDAO dao, Drug drug) throws Exception {
		initialize();
		this.dao = dao;
		this.drug = drug;
		nameField.setText(drug.drugName);
		ProviderDAO providerDAO = new ProviderDAO();
		providerField.setText(providerDAO.getProviderNameForId(drug.providerId));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("进/退货");
		frame.setBounds(100, 100, 362, 300);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane()
				.setLayout(new FormLayout(new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
						new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
								FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
								FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel label = new JLabel("药品名");
		frame.getContentPane().add(label, "6, 6, right, default");

		nameField = new JTextField();
		nameField.setEditable(false);
		frame.getContentPane().add(nameField, "8, 6, center, center");
		nameField.setColumns(10);

		JLabel label_1 = new JLabel("供应商");
		frame.getContentPane().add(label_1, "6, 8, right, default");

		providerField = new JTextField();
		providerField.setEditable(false);
		frame.getContentPane().add(providerField, "8, 8, center, default");
		providerField.setColumns(10);

		JLabel label_2 = new JLabel("操作");
		frame.getContentPane().add(label_2, "6, 10, right, default");

		JComboBox<CharSequence> comboBox = new JComboBox<CharSequence>();
		comboBox.addItem("进货");
		comboBox.addItem("退货");
		frame.getContentPane().add(comboBox, "8, 10, center, default");

		JLabel label_3 = new JLabel("数量");
		frame.getContentPane().add(label_3, "6, 12, right, default");

		deltaField = new JTextField();
		frame.getContentPane().add(deltaField, "8, 12, center, default");
		deltaField.setColumns(10);

		JButton doneButton = new JButton("确定");
		frame.getContentPane().add(doneButton, "8, 16, center, default");
		doneButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int delta = Integer.parseInt(deltaField.getText());
					if (comboBox.getSelectedIndex() == 0) {
						drug.stock += delta;
						if (dao.updateDrug(drug)) {
							alert("修改成功");
							frame.setVisible(false);
						} else {
							alertError("修改失败，请联系管理员。", "数据错误");
						}
					} else {
						if (delta > drug.stock) {
							alertError("输入不合法！没有足够的库存！", "输入错误");
						}
						drug.stock -= delta;
						if (dao.updateDrug(drug)) {
							alert("修改成功");
							frame.setVisible(false);
						} else {
							alertError("修改失败，请联系管理员。", "数据错误");
						}
					}
				} catch (Exception ex) {
					alertError("输入不合法！", "输入错误");
				}
			}

		});
	}

	protected void alertError(String errorMsg, String title) {
		JOptionPane.showMessageDialog(frame, errorMsg, title, JOptionPane.ERROR_MESSAGE);
	}

	protected void alert(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}

	public void addComponentListener(ComponentListener listener) {
		frame.addComponentListener(listener);
	}

	public void show() {
		frame.setVisible(true);
	}

}
