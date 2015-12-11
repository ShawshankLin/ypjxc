package com.jxc.internalWindow.empManage;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListDataListener;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

import com.jxc.dao.Dao;
import com.jxc.model.EmpInfo;
import com.jxc.model.GysInfo;
import com.jxc.tool.Item;
import com.jxc.tool.SetUpComponent;

public class EmpAlterPanel extends JPanel {
	private JTextField enameField;
	private JTextField ageField;
	private JTextField telField;
	private JTextField xlField;
	private JTextField zhiwuField;
	private JTextField addressField;
	private JComboBox<Item> empBox;
	private JComboBox sexBox;
	private JButton delButton;
	private JButton modifybButton;
	private JPanel panel;

	public EmpAlterPanel() {
		setLayout(new GridBagLayout());

		new SetUpComponent(this, new JLabel("员工姓名："), 0, 0, 1, 1, false);
		enameField = new JTextField();
		enameField.setEditable(false);
		new SetUpComponent(this, enameField, 1, 0, 1, 160, true);

		new SetUpComponent(this, new JLabel("性别"), 2, 0, 1, 1, false);
		sexBox = new JComboBox();
		sexBox.addItem("男");
		sexBox.addItem("女");
		new SetUpComponent(this, sexBox, 3, 0, 1, 160, true);
		
		new SetUpComponent(this, new JLabel("年龄："), 0, 1, 1, 1, false);
		ageField = new JTextField();
		new SetUpComponent(this, ageField, 1, 1, 1, 160, true);

		new SetUpComponent(this, new JLabel("电话："), 2, 1, 1, 1, false);
		telField = new JTextField();
		// yzbm.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, telField, 3, 1, 1, 0, true);

		new SetUpComponent(this, new JLabel("学历："), 0, 2, 1, 1, false);
		xlField = new JTextField();
		new SetUpComponent(this, xlField, 1, 2, 1, 0, true);

		new SetUpComponent(this, new JLabel("职务："), 2, 2, 1, 1, false);
		zhiwuField = new JTextField();
		// tel.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, zhiwuField, 3, 2, 1, 0, true);

		new SetUpComponent(this, new JLabel("住址："), 0, 3, 1, 1, false);
		addressField = new JTextField();
		// fax.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, addressField, 1, 3, 3, 0, true);

		new SetUpComponent(this, new JLabel("选择员工"), 0, 4, 1, 1, false);
		empBox = new JComboBox<Item>();
		empBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				doEmpSelectAction();
			}
		});
		new SetUpComponent(this, empBox, 1, 4, 1, 1, true);

		panel = new JPanel();
		delButton = new JButton("删除");
		delButton.addActionListener(new DelEmpActionListener());
		modifybButton = new JButton("修改");
		modifybButton.addActionListener(new ModifyActionListener());
		panel.add(delButton);
		panel.add(modifybButton);
		new SetUpComponent(this, panel, 3, 4, 1, 1, true);
	}

	public void initComboBox() {
		List<List> empInfoList = Dao.getEmpInfos();
		List<Item> items = new ArrayList<Item>();
		empBox.removeAllItems();
		for (Iterator iterator = empInfoList.iterator(); iterator.hasNext();) {
			Item item = new Item();
			List<String> list = (List<String>) iterator.next();
			item.setId(list.get(0).toString().trim());
			item.setName(list.get(1).toString().trim());
			if (items.contains(item))
				continue;
			items.add(item);
			empBox.addItem(item);
		}
		doEmpSelectAction();
	}

	private void doEmpSelectAction() {
		Item selectedItem;
		if (!(empBox.getSelectedItem() instanceof Item)) {
			return;
		}
		selectedItem = (Item) empBox.getSelectedItem();
		EmpInfo empInfo = Dao.getEmpInfo(selectedItem);
		enameField.setText(empInfo.getEname().trim());
		sexBox.setSelectedItem(empInfo.getSex());
		ageField.setText(Integer.valueOf(empInfo.getAge()).toString().trim());
		telField.setText(empInfo.getTel().trim());
		xlField.setText(empInfo.getXl().trim());
		zhiwuField.setText(empInfo.getZhi().trim());
		addressField.setText(empInfo.getAddress().trim());
	}
	
	private class DelEmpActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Item item = (Item) empBox.getSelectedItem();
			String eid = item.getId();
			int count = Dao.delEmpInfo(eid);
			if (count > 0) {
				JOptionPane.showMessageDialog(EmpAlterPanel.this,
						"删除员工" + item.getName() + "成功！");
			} else {
				JOptionPane.showMessageDialog(EmpAlterPanel.this,
						"删除员工" + item.getName() + "失败！");
			}
		}
	}

	private class ModifyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			EmpInfo empInfo = new EmpInfo();
			Item item = (Item) empBox.getSelectedItem();
			empInfo.setEid(item.getId());
			empInfo.setEname(enameField.getText().trim());
			empInfo.setSex((String)sexBox.getSelectedItem());
			empInfo.setAge(Integer.parseInt(ageField.getText().trim()));
			empInfo.setTel(telField.getText().trim());
			empInfo.setXl(xlField.getText().trim());
			empInfo.setZhi(zhiwuField.getText().trim());
			empInfo.setAddress(addressField.getText().trim());
			int count = Dao.modifyEmpInfo(empInfo);
			if (count > 0) {
				JOptionPane.showMessageDialog(EmpAlterPanel.this, "修改信息成功！");
			} else {
				JOptionPane.showMessageDialog(EmpAlterPanel.this, "修改信息失败!");
			}
		}
	}
}
