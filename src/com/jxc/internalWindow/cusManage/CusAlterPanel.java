package com.jxc.internalWindow.cusManage;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jxc.dao.Dao;
import com.jxc.internalWindow.empManage.EmpAlterPanel;
import com.jxc.model.CusInfo;
import com.jxc.model.EmpInfo;
import com.jxc.tool.Item;
import com.jxc.tool.SetUpComponent;

public class CusAlterPanel extends JPanel{
	private JTextField cnameField;
	private JTextField lxrField;
	private JComboBox sexBox;
	private JTextField ageField;
	private JTextField telField;
	private JTextField emailField;
	private JTextField khyhField;
	private JTextField yhzhField;
	private JTextField addressField;
	private JComboBox cusBox;
	private JButton delButton;
	private JButton modifybButton;
	private JPanel panel;
	public CusAlterPanel(){
		setLayout(new GridBagLayout());

		new SetUpComponent(this, new JLabel("客户名称："), 0, 0, 1, 1, false);
		cnameField = new JTextField();
		new SetUpComponent(this, cnameField, 1, 0, 1, 160, true);

		new SetUpComponent(this, new JLabel("联系人："), 2, 0, 1, 1, false);
		lxrField = new JTextField();
		new SetUpComponent(this, lxrField, 3, 0, 1, 160, true);

		new SetUpComponent(this, new JLabel("性别："), 0, 1, 1, 1, false);
		sexBox = new JComboBox();
		sexBox.addItem("男");
		sexBox.addItem("女");
		new SetUpComponent(this, sexBox, 1, 1, 1, 160, true);

		new SetUpComponent(this, new JLabel("年龄："), 2, 1, 1, 1, false);
		ageField = new JTextField();
		// yzbm.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, ageField, 3, 1, 1, 0, true);

		new SetUpComponent(this, new JLabel("电话："), 0, 2, 1, 1, false);
		telField = new JTextField();
		new SetUpComponent(this, telField, 1, 2, 1, 0, true);

		new SetUpComponent(this, new JLabel("邮件："), 2, 2, 1, 1, false);
		emailField = new JTextField();
		// tel.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, emailField, 3, 2, 1, 0, true);

		new SetUpComponent(this, new JLabel("开户银行："), 0, 3, 1, 1, false);
		khyhField = new JTextField();
		// fax.addKeyListener(new InputKeyListener());
		new SetUpComponent(this, khyhField, 1, 3, 1, 0, true);

		new SetUpComponent(this, new JLabel("银行账号："), 2, 3, 1, 1, false);
		yhzhField = new JTextField();
		new SetUpComponent(this, yhzhField, 3, 3, 1, 1, true);
		
		new SetUpComponent(this, new JLabel("住址："), 0, 4, 1,1, false);
		addressField=new JTextField();
		new SetUpComponent(this, addressField, 1, 4, 3, 1, true);

		new SetUpComponent(this, new JLabel("选择客户"), 0, 5, 1, 1, false);
		cusBox=new JComboBox();
		cusBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				doEmpSelectAction();
			}
		});
		new SetUpComponent(this, cusBox, 1, 5, 1, 1, true);
	
		panel = new JPanel();
		delButton = new JButton("删除");
		delButton.addActionListener(new DelCusActionListener());
		
		modifybButton = new JButton("修改");
		modifybButton.addActionListener(new ModifyActionListener());
		panel.add(delButton);
		panel.add(modifybButton);
		new SetUpComponent(this, panel, 3, 5, 1, 1, true);
	}
	
	public void initComboBox() {
		List<List> empInfoList = Dao.getCusInfos();
		List<Item> items = new ArrayList<Item>();
		cusBox.removeAllItems();
		for (Iterator iterator = empInfoList.iterator(); iterator.hasNext();) {
			Item item = new Item();
			List<String> list = (List<String>) iterator.next();
			item.setId(list.get(0).toString().trim());
			item.setName(list.get(1).toString().trim());
			if (items.contains(item))
				continue;
			items.add(item);
			cusBox.addItem(item);
		}
		doEmpSelectAction();
	}

	private void doEmpSelectAction() {
		Item selectedItem;
		if (!(cusBox.getSelectedItem() instanceof Item)) {
			return;
		}
		selectedItem = (Item) cusBox.getSelectedItem();
		CusInfo cusInfo = Dao.getCusInfo(selectedItem);
		cnameField.setText(cusInfo.getCname().trim());
		lxrField.setText(cusInfo.getLxr().trim());
		sexBox.setSelectedItem(cusInfo.getSex());
		ageField.setText(Integer.valueOf(cusInfo.getAge()).toString().trim());
		telField.setText(cusInfo.getTel().trim());
		emailField.setText(cusInfo.getEmail().trim());
		khyhField.setText(cusInfo.getKhyh().trim());
		yhzhField.setText(cusInfo.getYhzh().trim());
		addressField.setText(cusInfo.getAddress().trim());
	}
	private class DelCusActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Item item = (Item) cusBox.getSelectedItem();
			String cid = item.getId();
			int count = Dao.delCusInfo(cid);
			System.out.print(count);
			if (count > 0) {
				JOptionPane.showMessageDialog(CusAlterPanel.this,
						"删除客户" + item.getName() + "成功！");
			} else {
				JOptionPane.showMessageDialog(CusAlterPanel.this,
						"删除客户" + item.getName() + "失败！");
			}
		}
	}
	private class ModifyActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			CusInfo cusInfo = new CusInfo();
			Item item = (Item) cusBox.getSelectedItem();
			cusInfo.setCid(item.getId());
			cusInfo.setCname(cnameField.getText().trim());
			cusInfo.setLxr(lxrField.getText().trim());
			cusInfo.setSex((String)sexBox.getSelectedItem());
			cusInfo.setAge(Integer.parseInt(ageField.getText().trim()));
			cusInfo.setTel(telField.getText().trim());
			cusInfo.setEmail(emailField.getText().trim());
			cusInfo.setYhzh(khyhField.getText().trim());
			cusInfo.setYhzh(yhzhField.getText().trim());
			cusInfo.setAddress(addressField.getText().trim());
			int count = Dao.modifyCusInfo(cusInfo);
			if (count > 0) {
				JOptionPane.showMessageDialog(CusAlterPanel.this, "修改信息成功！");
			} else {
				JOptionPane.showMessageDialog(CusAlterPanel.this, "修改信息失败!");
			}
		}
		
	}
}
