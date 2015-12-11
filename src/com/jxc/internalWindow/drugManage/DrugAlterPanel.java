package com.jxc.internalWindow.drugManage;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.management.loading.PrivateClassLoader;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jxc.dao.Dao;
import com.jxc.internalWindow.gysManage.GysAlterPanel;
import com.jxc.model.DrugInfo;
import com.jxc.model.GysInfo;
import com.jxc.tool.Item;
import com.jxc.tool.SetUpComponent;

public class DrugAlterPanel extends JPanel {
	private JTextField ypmcField;
	private JTextField xsjField;
	private JTextField jhjField;
	private JTextField slField;
	private JTextField ytField;
	private JTextField scrqField;
	private JTextField bzqField;
	private JComboBox sccjBox;
	private JComboBox drugBox;
	private JButton modifyButton;
	private JButton delButton;
	private JPanel panel;

	public DrugAlterPanel() {
		setLayout(new GridBagLayout());

		new SetUpComponent(this, new JLabel("药品名称："), 0, 0, 1, 1, false);
		ypmcField = new JTextField();
		ypmcField.setEditable(false);
		new SetUpComponent(this, ypmcField, 1, 0, 1, 120, true);

		new SetUpComponent(this, new JLabel("生产厂家："), 2, 0, 1, 1, false);
		sccjBox = new JComboBox();
		sccjBox.setMaximumRowCount(5);
		new SetUpComponent(this, sccjBox, 3, 0, 1, 120, true);

		new SetUpComponent(this, new JLabel("销售价："), 0, 1, 1, 1, false);
		xsjField = new JTextField();
		new SetUpComponent(this, xsjField, 1, 1, 1, 120, true);

		new SetUpComponent(this, new JLabel("进货价："), 2, 1, 1, 1, false);
		jhjField = new JTextField();
		new SetUpComponent(this, jhjField, 3, 1, 1, 120, true);

		new SetUpComponent(this, new JLabel("数量："), 0, 2, 1, 1, false);
		slField = new JTextField();
		new SetUpComponent(this, slField, 1, 2, 1, 120, true);

		new SetUpComponent(this, new JLabel("用途："), 2, 2, 1, 1, false);
		ytField = new JTextField();
		new SetUpComponent(this, ytField, 3, 2, 1, 120, true);

		new SetUpComponent(this, new JLabel("生产日期："), 0, 3, 1, 1, false);
		scrqField = new JTextField();
		new SetUpComponent(this, scrqField, 1, 3, 1, 120, true);

		new SetUpComponent(this, new JLabel("保质期："), 2, 3, 1, 1, false);
		bzqField = new JTextField();
		new SetUpComponent(this, bzqField, 3, 3, 1, 120, true);

		new SetUpComponent(this, new JLabel("选择药品"), 0, 4, 1, 1, false);
		drugBox = new JComboBox();
		drugBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				doDrugSelectAction();
			}
		});
		new SetUpComponent(this, drugBox, 1, 4, 1, 1, true);

		panel = new JPanel();
		delButton = new JButton("删除");
		delButton.addActionListener(new DelActionListener());
		modifyButton = new JButton("修改");
		modifyButton.addActionListener(new ModifyActionListener());
		panel.add(delButton);
		panel.add(modifyButton);
		new SetUpComponent(this, panel, 3, 4, 1, 1, false);

	}

	private class DelActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int count;
			Item item = (Item) drugBox.getSelectedItem();
			String did = item.getId();
			count = Dao.delDrugInfo(did);
			if (count > 0) {
				JOptionPane.showMessageDialog(DrugAlterPanel.this, "删除药品"
						+ item.getName() + "成功！");
				drugBox.removeItem(item);
			} else {
				JOptionPane.showMessageDialog(DrugAlterPanel.this, "删除药品"
						+ item.getName() + "失败！");
			}
		}

	}

	private class ModifyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Item item = (Item) drugBox.getSelectedItem();
			String gid = item.getId();
			DrugInfo drugInfo = new DrugInfo();
			drugInfo.setDid(gid);
			drugInfo.setXsj(Float.parseFloat(xsjField.getText().trim()));
			drugInfo.setJhj(Float.parseFloat(jhjField.getText().trim()));
			drugInfo.setSl(Integer.parseInt(slField.getText().trim()));
			drugInfo.setYt(ytField.getText().trim());
			drugInfo.setScrq(scrqField.getText().trim());
			drugInfo.setBzq(bzqField.getText().trim());
			drugInfo.setSccj(((Item)sccjBox.getSelectedItem()).getId());
			int count = Dao.modifyDrugInfo(drugInfo);
			if (count > 0) {
				JOptionPane.showMessageDialog(DrugAlterPanel.this, "更新信息成功！");
			} else {
				JOptionPane.showMessageDialog(DrugAlterPanel.this, "更新信息失败！");
			}
		}

	}
	public void initSccjBox() {
		List<List> gysInfoList = Dao.getGysInfos();
		List<Item> items = new ArrayList<Item>();
		sccjBox.removeAllItems();
		for (Iterator iterator = gysInfoList.iterator(); iterator.hasNext();) {
			Item item = new Item();
			List<String> list = (List<String>) iterator.next();
			item.setId(list.get(0).toString().trim());
			item.setName(list.get(1).toString().trim());
			if (items.contains(item))
				continue;
			items.add(item);
			sccjBox.addItem(item);
		}

	}

	public void initDrugBox() {
		List<List> drugInfoList = Dao.getDrugInfos();
		List<Item> items = new ArrayList<Item>();
		drugBox.removeAllItems();
		for (Iterator iterator = drugInfoList.iterator(); iterator.hasNext();) {
			Item item = new Item();
			List<String> list = (List<String>) iterator.next();
			item.setId(list.get(0).toString().trim());
			item.setName(list.get(1).toString().trim());
			if (items.contains(item))
				continue;
			items.add(item);
			drugBox.addItem(item);
		}
		doDrugSelectAction();
	}

	private void doDrugSelectAction() {
		Item selectedItem;
		if (!(drugBox.getSelectedItem() instanceof Item)) {
			return;
		}
		selectedItem = (Item) drugBox.getSelectedItem();
		DrugInfo drugInfo = Dao.getDrugInfo(selectedItem);
		ypmcField.setText(drugInfo.getYpmc().trim());
		xsjField.setText(Float.valueOf(drugInfo.getXsj()).toString());
		jhjField.setText(Float.valueOf(drugInfo.getJhj()).toString());
		slField.setText(Integer.valueOf(drugInfo.getSl()).toString());
		ytField.setText(drugInfo.getYt().trim());
		scrqField.setText(drugInfo.getScrq().trim());
		bzqField.setText(drugInfo.getBzq().trim());
	}

}
