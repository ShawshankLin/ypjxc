package com.jxc.login;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.jxc.dao.PropertiesUtils;
import com.jxc.tool.SetUpComponent;
public class ConnDialog extends JDialog {
	private JPanel panel;
	private JTextField fwqnameField, sjknameField, sjkyhField;
	private JPasswordField sjkmmField;
	private JButton okButton;

	public ConnDialog() {
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		int width = this.getToolkit().getDefaultToolkit().getScreenSize().width;
		int height = this.getToolkit().getDefaultToolkit().getScreenSize().height;
		setBounds(width / 2 - 100, height / 2 - 100, 340, 220);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		panel = new JPanel(new GridBagLayout());

		new SetUpComponent(panel, new JLabel("服务器名："), 0, 0, 1, 1, false);
		fwqnameField = new JTextField();
		fwqnameField.setText(PropertiesUtils.getPropertyValue("HOST"));
		new SetUpComponent(panel, fwqnameField, 1, 0, 3, 1, true);

		
		new SetUpComponent(panel, new JLabel("数据库名："), 0, 1, 1, 1, false);
		sjknameField = new JTextField();
		sjknameField.setText(PropertiesUtils.getPropertyValue("URL"));
		new SetUpComponent(panel, sjknameField, 1, 1, 3, 1, true);

		new SetUpComponent(panel, new JLabel("数据库用户："), 0, 2, 1, 1, false);
		sjkyhField = new JTextField();
		sjkyhField.setText(PropertiesUtils.getPropertyValue("USER"));
		new SetUpComponent(panel, sjkyhField, 1, 2, 3, 1, true);

		new SetUpComponent(panel, new JLabel("数据库密码："), 0, 3, 1, 1, false);
		sjkmmField = new JPasswordField();
		sjkmmField.setText(PropertiesUtils.getPropertyValue("PWD"));
		new SetUpComponent(panel, sjkmmField, 1, 3, 3, 1, true);

		okButton = new JButton("确认");
		new SetUpComponent(panel, okButton, 3, 4, 1, 1, false);
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PropertiesUtils.updateProperties("HOST", fwqnameField.getText().trim());
				PropertiesUtils.updateProperties("URL", sjknameField.getText().trim());
				PropertiesUtils.updateProperties("USER", sjkyhField.getText().trim());
				PropertiesUtils.updateProperties("PWD",String.valueOf(sjkmmField.getPassword()));
				new Login();
				ConnDialog.this.dispose();
			}
		});

		this.add(panel);
	}
}
