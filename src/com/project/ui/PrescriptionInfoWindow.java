package com.project.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.project.dao.PrescriptionDAO;
import com.project.model.Prescription;
import com.project.model.PrescriptionTableModel;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class PrescriptionInfoWindow {

	private JFrame frame;
	private JTextField patientNameField;
	private JTextField patientAgeField;
	private JTextField physicianNameField;
	private JTextField prescriptionDateField;
	private JComboBox<String> patientSexBox;
	private PrescriptionDAO dao;
	private PrescriptionTableModel model;
	private final boolean edit;
	private int prescriptionId;
	private SimpleDateFormat formatter;

	public PrescriptionInfoWindow(PrescriptionDAO dao) {
		initialize();
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		this.dao = dao;
		edit = false;
	}

	public PrescriptionInfoWindow(PrescriptionDAO dao, Prescription prescription) {
		initialize();
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		this.dao = dao;
		edit = true;
		prescriptionId = prescription.prescriptionId;
		patientNameField.setText(prescription.patientName);
		patientAgeField.setText(Integer.toString(prescription.patientAge));
		physicianNameField.setText(prescription.physicianName);
		prescriptionDateField.setText(formatter.format(prescription.prescriptionDate));
		patientSexBox.setSelectedItem(prescription.patientSex);
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
		panel.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(15dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("max(61dlu;default):grow"), },
				new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel patientNameLabel = new JLabel("患者姓名");
		panel.add(patientNameLabel, "6, 6, center, default");

		patientNameField = new JTextField();
		panel.add(patientNameField, "8, 6, center, default");
		patientNameField.setColumns(10);

		JLabel patientAgeLabel = new JLabel("患者年龄");
		panel.add(patientAgeLabel, "6, 8, center, default");

		patientAgeField = new JTextField();
		panel.add(patientAgeField, "8, 8, center, default");
		patientAgeField.setColumns(10);

		JLabel patientSexLabel = new JLabel("患者性别");
		panel.add(patientSexLabel, "6, 10, center, default");

		patientSexBox = new JComboBox<String>();
		patientSexBox.addItem("男");
		patientSexBox.addItem("女");
		panel.add(patientSexBox, "8, 10, center, center");

		JLabel physicianNameLabel = new JLabel("医师姓名");
		panel.add(physicianNameLabel, "6, 12, center, default");

		physicianNameField = new JTextField();
		panel.add(physicianNameField, "8, 12, center, center");
		physicianNameField.setColumns(10);

		JLabel prescriptionDateLabel = new JLabel("处方时间");
		panel.add(prescriptionDateLabel, "6, 14, center, default");

		prescriptionDateField = new JTextField();
		panel.add(prescriptionDateField, "8, 14, center, center");
		prescriptionDateField.setColumns(10);

		JButton doneButton = new JButton("确定");
		doneButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Prescription prescription = null;
				try {
					prescription = getEditedPrescription();
				} catch (Exception ex) {
					alertError("输入不合法！", "数据错误");
				}
				if (prescription == null) {
					return;
				}
				try {
					if (edit) {
						if (dao.updatePrescription(prescription)) {
							alert("修改成功");
							frame.setVisible(false);
						} else {
							alertError("修改失败，请联系管理员。", "数据错误");
						}
					} else {
						if (dao.addPrescription(prescription)) {
							alert("新增处方成功");
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
		panel.add(doneButton, "6, 20");

		JButton resetButton = new JButton("重置");
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				patientAgeField.setText("");
				patientNameField.setText("");
				physicianNameField.setText("");
				prescriptionDateField.setText("");
			}

		});
		panel.add(resetButton, "8, 20, center, default");
	}

	public void addComponentListener(ComponentListener listener) {
		frame.addComponentListener(listener);
	}

	public void show() {
		frame.setVisible(true);
	}

	public Prescription getEditedPrescription() throws Exception {
		Prescription prescription = new Prescription();
		if (edit) {
			prescription.prescriptionId = prescriptionId;
		}
		prescription.patientAge = Integer.parseInt(patientAgeField.getText());
		prescription.patientName = patientNameField.getText();
		prescription.patientSex = (String) patientSexBox.getSelectedItem();
		prescription.physicianName = physicianNameField.getText();
		prescription.prescriptionDate = new Date(formatter.parse(prescriptionDateField.getText()).getTime());
		return prescription;
	}

	protected void alertError(String errorMsg, String title) {
		JOptionPane.showMessageDialog(frame, errorMsg, title, JOptionPane.ERROR_MESSAGE);
	}

	protected void alert(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}

}
