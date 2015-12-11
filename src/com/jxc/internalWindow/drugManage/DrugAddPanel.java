package com.jxc.internalWindow.drugManage;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jxc.dao.Dao;
import com.jxc.internalWindow.gysManage.GysAddPanel;
import com.jxc.model.DrugInfo;
import com.jxc.model.GysInfo;
import com.jxc.tool.Item;
import com.jxc.tool.SetUpComponent;

public class DrugAddPanel extends JPanel {
	private JTextField ypmcField;
	private JTextField xsjField;
	private JTextField jhjField;
	private JTextField slField;
	private JTextField ytField;
	private JTextField scrqField;
	private JTextField bzqField;
	private JComboBox sccjBox;
	private JPanel panel;
	private JButton addButton;
	private JButton resetButton;

	public DrugAddPanel() {
		setLayout(new GridBagLayout());

		new SetUpComponent(this, new JLabel("药品名称："), 0, 0, 1, 1, false);
		ypmcField = new JTextField();
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

		
		panel=new JPanel();
		addButton = new JButton("添加");
		addButton.addActionListener(new AddActionListener());
		resetButton = new JButton("重置");
		resetButton.addActionListener(new ResetActionListener());
		panel.add(addButton);
		panel.add(resetButton);
		new SetUpComponent(this, panel, 3, 4, 1, 1, false);

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

	private class AddActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (ypmcField.getText().equals("") || xsjField.getText().equals("")
					|| jhjField.getText().equals("")
					|| slField.getText().equals("")
					|| ytField.getText().equals("")
					|| scrqField.getText().equals("")
					|| bzqField.getText().equals("")) {
				JOptionPane.showMessageDialog(DrugAddPanel.this, "请填写全部信息！");
				return;
			}
			ResultSet rs = Dao.query("select max(did) from tb_drugInfo");
			String id = null;
			try {
				if (rs != null && rs.next()) {
					String gid = rs.getString(1);
					if (gid == null) {
						id = "drug1001";
					} else {
						String s = gid.substring(4);
						id = "drug" + (Integer.parseInt(s) + 1);
					}
				}
				DrugInfo drugInfo = new DrugInfo();
				drugInfo.setDid(id);
				Item item = (Item) sccjBox.getSelectedItem();
				drugInfo.setSccj(item.getId());
				drugInfo.setYpmc(ypmcField.getText().trim());
				drugInfo.setXsj(Float.parseFloat(xsjField.getText().trim()));
				drugInfo.setJhj(Float.parseFloat(jhjField.getText().trim()));
				drugInfo.setSl(Integer.parseInt(slField.getText().trim()));
				drugInfo.setYt(ytField.getText().trim());
				drugInfo.setScrq(scrqField.getText().trim());
				drugInfo.setBzq(bzqField.getText().trim());
				int count = Dao.insertDrugInfo(drugInfo);
				if (count > 0) {
					JOptionPane.showMessageDialog(DrugAddPanel.this, "添加记录成功！");
					resetButton.doClick();
				} else {
					JOptionPane.showMessageDialog(DrugAddPanel.this, "添加记录失败！");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private class ResetActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ypmcField.setText(null);
			xsjField.setText(null);
			jhjField.setText(null);
			slField.setText(null);
			ytField.setText(null);
			scrqField.setText(null);
			bzqField.setText(null);
		}

	}

}
