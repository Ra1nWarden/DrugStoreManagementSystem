package com.project.ui;

import javax.swing.JFrame;

import com.project.dao.DrugDAO;
import com.project.dao.PrescriptionDrugDAO;
import com.project.dao.ProviderDAO;
import com.project.model.Drug;
import com.project.model.PrescriptionDrug;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;

public class PrescriptionDrugInfoWindow {

	private JFrame frame;
	private PrescriptionDrugDAO dao;
	private ProviderDAO providerDAO;
	private PrescriptionDrug prescriptionDrug;
	private final boolean edit;
	private JTextField providerField;
	private JTextField amountField;
	private JComboBox<Drug> nameSelection;
	private JComboBox<CharSequence> statusSelection;
	private List<Drug> allDrugs;
	private final int prescriptionId;

	/**
	 * Create the application.
	 */
	public PrescriptionDrugInfoWindow(PrescriptionDrugDAO dao, int prescriptionId) {
		initialize();
		this.edit = false;
		this.dao = dao;
		this.prescriptionId = prescriptionId;
		statusSelection.addItem("未发货");
		statusSelection.setEnabled(false);
	}

	public PrescriptionDrugInfoWindow(PrescriptionDrugDAO dao, int prescriptionId, PrescriptionDrug prescriptionDrug) {
		initialize();
		this.edit = true;
		providerField.setEditable(false);
		amountField.setEditable(false);
		nameSelection.setEnabled(false);
		try {
			providerField.setText(prescriptionDrug.provider.providerName);
			amountField.setText(Integer.toString(prescriptionDrug.amount));
			for (Drug drug : allDrugs) {
				if (drug.drugName.equals(prescriptionDrug.drug.drugName)) {
					nameSelection.setSelectedItem(drug);
					break;
				}
			}
		} catch (Exception ex) {
			alertError("加载数据失败", "数据库错误");
		}
		this.dao = dao;
		this.prescriptionId = prescriptionId;
		this.prescriptionDrug = prescriptionDrug;
		statusSelection.addItem("已发货");
		statusSelection.addItem("已退货");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("处方药品信息");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel label = new JLabel("药品名");
		frame.getContentPane().add(label, "6, 6, right, default");

		nameSelection = new JComboBox<Drug>();
		try {
			providerDAO = new ProviderDAO();
			DrugDAO drugDAO = new DrugDAO();
			allDrugs = drugDAO.getAllDrugs("");
			for (Drug eachDrug : allDrugs) {
				nameSelection.addItem(eachDrug);
			}
		} catch (Exception ex) {
			alertError("无法加载药品！", "数据库错误");
			frame.setVisible(false);
		}
		frame.getContentPane().add(nameSelection, "8, 6, center, default");

		JLabel label_1 = new JLabel("供应商");
		frame.getContentPane().add(label_1, "6, 8, right, default");

		providerField = new JTextField();
		providerField.setEditable(false);
		frame.getContentPane().add(providerField, "8, 8, center, center");
		providerField.setColumns(10);

		nameSelection.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				try {
					Drug selectedDrug = (Drug) e.getItem();
					providerField.setText(providerDAO.getProviderNameForId(selectedDrug.providerId));
				} catch (Exception ex) {
					alertError("无法加载供应商！", "数据库错误");
					frame.setVisible(false);
				}
			}

		});

		JLabel label_2 = new JLabel("数量");
		frame.getContentPane().add(label_2, "6, 10, right, default");

		amountField = new JTextField();
		frame.getContentPane().add(amountField, "8, 10, center, default");
		amountField.setColumns(10);

		JLabel label_3 = new JLabel("状态");
		frame.getContentPane().add(label_3, "6, 12, right, default");

		statusSelection = new JComboBox<CharSequence>();
		frame.getContentPane().add(statusSelection, "8, 12, center, default");

		JButton doneButton = new JButton("确定");
		frame.getContentPane().add(doneButton, "8, 18, center, default");
		doneButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PrescriptionDrug prescription = null;
				try {
					prescription = getEditedPrescriptionDrug();
				} catch (Exception ex) {
					alertError("输入不合法！", "数据错误");
				}
				if (prescription == null) {
					return;
				}
				try {
					if (edit) {
						if (dao.updatePrescriptionDrug(prescription, prescriptionDrug.status)) {
							alert("修改成功");
							frame.setVisible(false);
						} else {
							alertError("修改失败：没有足够的库存。", "数据错误");
						}
					} else {
						if (dao.addPrescriptionDrug(prescription)) {
							alert("新增处方药品成功");
							frame.setVisible(false);
						} else {
							alertError("操作失败，请联系管理员。", "数据错误");
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					alertError("操作失败，未知错误。", "数据库错误");
				}
			}

		});
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

	private PrescriptionDrug getEditedPrescriptionDrug() throws Exception {
		PrescriptionDrug drug = new PrescriptionDrug();
		drug.prescriptionId = prescriptionId;
		drug.drugId = ((Drug) nameSelection.getSelectedItem()).drugId;
		drug.amount = Integer.parseInt(amountField.getText());
		drug.status = (String) statusSelection.getSelectedItem();
		return drug;
	}

}
