package com.jxc.internalWindow.userManage;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jxc.dao.Dao;
import com.jxc.model.UserInfo;
import com.jxc.tool.SetUpComponent;


public class UserAlterDialog extends JDialog {
	private JPanel panel, southPanel;
	private JTextField uidField,usernameField,passwordField;
	private JButton okButton, noButton;
	private JComboBox roleBox;
	private UserInfo userInfo;
	public UserAlterDialog(UserInfo userInfo) {
		this.userInfo=userInfo;
		setUpComponent();
		int width = this.getParent().getToolkit().getDefaultToolkit()
				.getScreenSize().width;
		int height = this.getParent().getToolkit().getDefaultToolkit()
				.getScreenSize().height;
		this.setBounds(width / 2 - 200, height / 2 - 100, 560, 200);
		this.setTitle("修改用户信息");
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);

		this.setLayout(new BorderLayout());
		this.add(panel);
	}
	public void setUpComponent() {
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		new SetUpComponent(panel, new JLabel("用户ID："), 0, 0, 1, 1, false);
		uidField = new JTextField();
		uidField.setEditable(false);
		uidField.setText(userInfo.getUid());
		new SetUpComponent(panel, uidField, 1, 0, 1, 160, true);
		
		new SetUpComponent(panel, new JLabel("用户名："), 2, 0, 1, 1, false);
		usernameField = new JTextField();
		usernameField.setText(userInfo.getUsername());
		new SetUpComponent(panel, usernameField, 3, 0, 1, 160, true);

		new SetUpComponent(panel, new JLabel("密码："), 0, 1, 1, 1, false);
		passwordField = new JTextField();
		passwordField.setText(userInfo.getPassword());
		new SetUpComponent(panel, passwordField, 1, 1, 1, 160, true);
		
		okButton = new JButton("确定");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UserInfo user = new UserInfo();
				user.setUid(uidField.getText().trim());
				user.setUsername(usernameField.getText().trim());
				user.setPassword(passwordField.getText().trim());
				int count = Dao.modifyUserInfo(user);
				if (count > 0) {
					JOptionPane.showMessageDialog(UserAlterDialog.this,
							"更新信息成功！");
					UserAlterDialog.this.dispose();
				} else {
					JOptionPane.showMessageDialog(UserAlterDialog.this,
							"更新信息失败！");
				}
			}
		});
		noButton = new JButton("取消");
		noButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UserAlterDialog.this.dispose();
			}
		});
		southPanel = new JPanel();
		southPanel.add(okButton);
		southPanel.add(noButton);
		new SetUpComponent(panel, southPanel, 3, 2, 1, 1, true);
		this.setLayout(new BorderLayout());
		this.add(panel);

	}

}
