package com.jxc.internalWindow.cusManage;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jxc.dao.Dao;
import com.jxc.internalWindow.empManage.EmpAddPanel;
import com.jxc.model.CusInfo;
import com.jxc.model.EmpInfo;
import com.jxc.tool.SetUpComponent;

public class CusAddPanel extends JPanel {
	private JTextField cnameField;
	private JTextField lxrField;
	private JComboBox sexBox;
	private JTextField ageField;
	private JTextField telField;
	private JTextField emailField;
	private JTextField khyhField;
	private JTextField yhzhField;
	private JTextField addressField;
	private JButton addButton;
	private JButton resetButton;
	private JPanel panel;

	public CusAddPanel() {
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

		panel = new JPanel();
		addButton = new JButton("添加");
		addButton.addActionListener(new AddActionListener());
		resetButton = new JButton("重置");
		resetButton.addActionListener(new ResetActionListener());
		panel.add(addButton);
		panel.add(resetButton);
		new SetUpComponent(this, panel, 3, 5, 1, 1, true);
	}

	private class AddActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (cnameField.getText().equals("")
					|| lxrField.getText().equals("")
					|| telField.getText().equals("")
					|| ageField.getText().equals("")
					|| khyhField.getText().equals("")
					|| yhzhField.getText().equals("")
					|| addressField.getText().equals("")) {
				JOptionPane.showMessageDialog(CusAddPanel.this, "请填写全部信息！");
				return;
			}
			ResultSet rs = Dao.query("select max(cid) from tb_cusInfo");
			String id = null;
			try {
				if (rs != null && rs.next()) {
					String cid = rs.getString(1);
					if (cid == null) {
						id = "cus1001";
					} else {
						String s = cid.substring(3);
						id = "cus" + (Integer.parseInt(s) + 1);
					}
				}
				CusInfo cusInfo = new CusInfo();
				cusInfo.setCid(id);
				cusInfo.setCname(cnameField.getText().trim());
				cusInfo.setLxr(lxrField.getText().trim());
				cusInfo.setSex((String) sexBox.getSelectedItem());
				cusInfo.setAge(Integer.parseInt(ageField.getText().trim()));
				cusInfo.setTel(telField.getText().trim());
				cusInfo.setEmail(emailField.getText().trim());
				cusInfo.setKhyh(khyhField.getText().trim());
				cusInfo.setYhzh(yhzhField.getText().trim());
				int count = Dao.insertEmpInfo(cusInfo);
				if (count > 0) {
					JOptionPane.showMessageDialog(CusAddPanel.this, "添加记录成功！");
					resetButton.doClick();
				} else {
					JOptionPane.showMessageDialog(CusAddPanel.this, "添加记录失败！");
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
			cnameField.setText("");
			lxrField.setText("");
			ageField.setText("");
			telField.setText("");
			emailField.setText("");
			khyhField.setText("");
			yhzhField.setText("");
			addButton.setText("");
		}
		
	}
}
