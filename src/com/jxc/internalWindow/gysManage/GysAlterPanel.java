package com.jxc.internalWindow.gysManage;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import com.jxc.dao.Dao;
import com.jxc.model.GysInfo;
import com.jxc.tool.Item;
import com.jxc.tool.SetUpComponent;

public class GysAlterPanel extends JPanel {
	private JTextField gysmcField;
	private JTextField lxrField;// 联系人
	private JTextField telField;
	private JTextField faxField;
	private JTextField emailField;
	private JTextField yzbmFied;
	private JTextField khyhField;
	private JTextField yhzhField;
	private JTextField addressField;
	private JComboBox gys;
	private JButton modifyButton;
	private JButton delButton;

	public GysAlterPanel() {
		setLayout(new GridBagLayout());

		new SetUpComponent(this, new JLabel("供应商名称："), 0, 0, 1, 1, false);
		gysmcField = new JTextField();
		new SetUpComponent(this, gysmcField, 1, 0, 1, 160, true);

		new SetUpComponent(this, new JLabel("联系人"), 2, 0, 1, 1, false);
		lxrField = new JTextField();
		new SetUpComponent(this, lxrField, 3, 0, 1, 160, true);
		new SetUpComponent(this, new JLabel("联系人电话："), 0, 1, 1, 1, false);
		telField = new JTextField();
		new SetUpComponent(this, telField, 1, 1, 1, 160, true);

		new SetUpComponent(this, new JLabel("传真："), 2, 1, 1, 1, false);
		faxField = new JTextField();
		// yzbm.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, faxField, 3, 1, 1, 0, true);

		new SetUpComponent(this, new JLabel("地址："), 0, 2, 1, 1, false);
		addressField = new JTextField();
		new SetUpComponent(this, addressField, 1, 2, 3, 0, true);

		new SetUpComponent(this, new JLabel("邮件："), 0, 3, 1, 1, false);
		emailField = new JTextField();
		// tel.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, emailField, 1, 3, 1, 0, true);

		new SetUpComponent(this, new JLabel("邮政编码："), 2, 3, 1, 1, false);
		yzbmFied = new JTextField();
		// fax.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, yzbmFied, 3, 3, 1, 0, true);

		new SetUpComponent(this, new JLabel("开户银行："), 0, 4, 1, 1, false);
		khyhField = new JTextField();
		new SetUpComponent(this, khyhField, 1, 4, 1, 0, true);

		new SetUpComponent(this, new JLabel("银行账号："), 2, 4, 1, 1, false);
		yhzhField = new JTextField();
		// yh.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, yhzhField, 3, 4, 1, 0, true);

		new SetUpComponent(this, new JLabel("选择供应商"), 0, 7, 1, 0, false);
		gys = new JComboBox();
		gys.setPreferredSize(new Dimension(230, 21));
		initComboBox();// 初始化下拉选择框
		// 处理供应商信息的下拉选择框的选择事件
		gys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doGysSelectAction();
			}
		});
		// 定位供应商信息的下拉选择框
		new SetUpComponent(this, gys, 1, 7, 2, 0, true);

		modifyButton = new JButton("修改");
		delButton = new JButton("删除");
		JPanel panel = new JPanel();
		panel.add(modifyButton);
		panel.add(delButton);
		// 定位按钮
		new SetUpComponent(this, panel, 3, 7, 1, 0, false);
		// 处理删除按钮的单击事件
		delButton.addActionListener(new DelActionListener());
		// 处理修改按钮的单击事件
		modifyButton.addActionListener(new ModifyActionListener());
	}

	public void initComboBox() {
		List<List> gysInfoList = Dao.getGysInfos();
		List<Item> items = new ArrayList<Item>();
		gys.removeAllItems();
		for (Iterator iterator = gysInfoList.iterator(); iterator.hasNext();) {
			Item item = new Item();
			List<String> list = (List<String>) iterator.next();
			item.setId(list.get(0).toString().trim());
			item.setName(list.get(1).toString().trim());
			if (items.contains(item))
				continue;
			items.add(item);
			gys.addItem(item);
		}
		doGysSelectAction();
	}

	private void doGysSelectAction() {
		Item selectedItem;
		if (!(gys.getSelectedItem() instanceof Item)) {
			return;
		}
		selectedItem = (Item) gys.getSelectedItem();
		GysInfo gysInfo = Dao.getGysInfo(selectedItem);
		gysmcField.setText(gysInfo.getGysmc().trim());
		lxrField.setText(gysInfo.getLxr().trim());
		telField.setText(gysInfo.getTel().trim());
		faxField.setText(gysInfo.getFax().trim());
		emailField.setText(gysInfo.getEmail().trim());
		yzbmFied.setText(gysInfo.getYhzh().trim());
		khyhField.setText(gysInfo.getYhzh().trim());
		yhzhField.setText(gysInfo.getYhzh().trim());
		addressField.setText(gysInfo.getAddress().trim());
	}

	private class DelActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int count;
			Item item = (Item) gys.getSelectedItem();
			String gid = item.getId();
			count = Dao.delGysInfo(gid);
			if (count > 0) {
				JOptionPane.showMessageDialog(GysAlterPanel.this, "删除供应商"
						+ item.getName() + "成功！");
				gys.removeItem(item);
			} else {
				JOptionPane.showMessageDialog(GysAlterPanel.this, "删除供应商"
						+ item.getName() + "失败！");
			}
		}

	}

	private class ModifyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Item item = (Item) gys.getSelectedItem();
			String gid = item.getId();
			GysInfo gysInfo = new GysInfo();
			gysInfo.setGid(gid);
			gysInfo.setGysmc(gysmcField.getText().trim());
			gysInfo.setLxr(lxrField.getText().trim());
			gysInfo.setTel(telField.getText().trim());
			gysInfo.setFax(faxField.getText().trim());
			gysInfo.setEmail(emailField.getText().trim());
			gysInfo.setYzbm(yzbmFied.getText().trim());
			gysInfo.setKhyh(khyhField.getText().trim());
			gysInfo.setAddress(addressField.getText().trim());
			int count = Dao.modifyGysInfo(gysInfo);
			if (count > 0) {
				JOptionPane.showMessageDialog(GysAlterPanel.this, "更新信息成功！");
			} else {
				JOptionPane.showMessageDialog(GysAlterPanel.this, "更新信息失败！");
			}
		}

	}
}
