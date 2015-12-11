package com.jxc.internalWindow.gysManage;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jxc.dao.Dao;
import com.jxc.internalWindow.GysManage;
import com.jxc.model.*;
import com.jxc.tool.SetUpComponent;

public class GysAddPanel extends JPanel {
	private JTextField gysmcField;
	private JTextField lxrField;// 联系人
	private JTextField telField;
	private JTextField faxField;
	private JTextField emailField;
	private JTextField yzbmFied;
	private JTextField khyhField;
	private JTextField yhzhField;
	private JTextField addressField;
	private JButton addButton;
	private JButton resetButton;

	public GysAddPanel() {
		setLayout(new GridBagLayout());

		new SetUpComponent(this,new JLabel("供应商名称："), 0, 0, 1, 1, false);
		gysmcField = new JTextField();
		new SetUpComponent(this,gysmcField, 1, 0, 1, 160, true);
		
		new SetUpComponent(this,new JLabel("联系人"), 2, 0, 1, 1, false);
		lxrField=new JTextField();
		new SetUpComponent(this,lxrField, 3, 0, 1, 160, true);
		new SetUpComponent(this,new JLabel("联系人电话："), 0, 1, 1, 1, false);
		telField = new JTextField();
		new SetUpComponent(this,telField, 1, 1, 1, 160, true);

		new SetUpComponent(this,new JLabel("传真："), 2, 1, 1, 1, false);
		faxField = new JTextField();
		// yzbm.addKeyListener(new InputKeyListener());
		new SetUpComponent(this,faxField, 3, 1, 1, 0, true);

		new SetUpComponent(this,new JLabel("地址："), 0, 2, 1, 1, false);
		addressField = new JTextField();
		new SetUpComponent(this,addressField, 1, 2, 3, 0, true);

		new SetUpComponent(this,new JLabel("邮件："), 0, 3, 1, 1, false);
		emailField = new JTextField();
		// tel.addKeyListener(new InputKeyListener());
		new SetUpComponent(this,emailField, 1, 3, 1, 0, true);

		new SetUpComponent(this,new JLabel("邮政编码："), 2, 3, 1, 1, false);
		yzbmFied = new JTextField();
		// fax.addKeyListener(new InputKeyListener());
		new SetUpComponent(this,yzbmFied, 3, 3, 1, 0, true);

		new SetUpComponent(this,new JLabel("开户银行："), 0, 4, 1, 1, false);
		khyhField = new JTextField();
		new SetUpComponent(this,khyhField, 1, 4, 1, 0, true);

		new SetUpComponent(this,new JLabel("银行账号："), 2, 4, 1, 1, false);
		yhzhField = new JTextField();
		// yh.addKeyListener(new InputKeyListener());
		new SetUpComponent(this,yhzhField, 3, 4, 1, 0, true);

		addButton = new JButton();
		addButton.addActionListener(new AddActionListener());
		addButton.setText("添加");
		new SetUpComponent(this,addButton, 2, 5, 1, 0, false);

		resetButton = new JButton();
		new SetUpComponent(this,resetButton, 3, 5, 1, 0, false);
		resetButton.addActionListener(new ResetActionListener());
		resetButton.setText("重置");
	}

	private class AddActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (gysmcField.getText().equals("")
					|| lxrField.getText().equals("")
					|| lxrField.getText().equals("")
					|| faxField.getText().equals("")
					|| emailField.getText().equals("")
					|| yzbmFied.getText().equals("")
					|| khyhField.getText().equals("")
					|| yhzhField.getText().equals("")
					|| addressField.getText().equals("")) {
				JOptionPane.showMessageDialog(GysAddPanel.this, "请填写全部信息！");
				return;
			}
			ResultSet rs = Dao.query("select max(gid) from tb_gysInfo");
			String id = null;
			try {
				if (rs != null && rs.next()) {
					String gid = rs.getString(1);
					if (gid == null) {
						id = "gys1001";
					} else {
						String s = gid.substring(3);
						id = "gys" + (Integer.parseInt(s) + 1);
					}
				}
				GysInfo gysInfo = new GysInfo();
				gysInfo.setGid(id);
				gysInfo.setGysmc(gysmcField.getText().trim());
				gysInfo.setLxr(lxrField.getText().trim());
				gysInfo.setTel(telField.getText().trim());
				gysInfo.setFax(faxField.getText().trim());
				gysInfo.setEmail(emailField.getText().trim());
				gysInfo.setYzbm(yzbmFied.getText().trim());
				gysInfo.setKhyh(khyhField.getText().trim());
				gysInfo.setYhzh(yhzhField.getText().trim());
				gysInfo.setAddress(addressField.getText().trim());
				int count= Dao.insertGysInfo(gysInfo);
				if (count>0) {
					JOptionPane.showMessageDialog(GysAddPanel.this, "添加记录成功！");
					resetButton.doClick();
				} else {
					JOptionPane.showMessageDialog(GysAddPanel.this, "添加记录失败！");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	private class ResetActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			gysmcField.setText(null);
			lxrField.setText(null);
			telField.setText(null);
			faxField.setText(null);
			emailField.setText(null);
			yzbmFied.setText(null);
			yhzhField.setText(null);
			khyhField.setText(null);
			addressField.setText(null);
		}
		
	}
}
