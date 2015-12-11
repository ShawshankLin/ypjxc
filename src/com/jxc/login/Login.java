package com.jxc.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.jxc.mainWindow.*;
import com.jxc.dao.Dao;

public class Login extends JFrame {
	private JPanel loginPanel;
	private JLabel userLabel;
	private JLabel passLabel;
	private JTextField userName;
	private JPasswordField password;
	private JButton reset, login, set;

	public static void main(String[] args) {
		// JFrame.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Login();
			}
		});
	}

	public Login() {
		init();
		setUpUIComponent();
		setUpComponentListen();

	}

	private void init() {
		setTitle("欢迎登录药品进销存管理系统");
		setVisible(true);
		int width = this.getToolkit().getDefaultToolkit().getScreenSize().width;
		int height = this.getToolkit().getDefaultToolkit().getScreenSize().height;
		setBounds(width / 2 - 200, height / 2 - 200, 600, 430);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		

	}

	private void setUpUIComponent() {
		loginPanel = new LoginPanel();
		loginPanel.setLayout(null);
		getContentPane().add(loginPanel);


		userName = new JTextField();
		userName.setText("emp1001");
		userName.setBounds(330, 205, 170, 22);
		loginPanel.add(userName);

		password = new JPasswordField();
		password.setText("123456");
		password.setBounds(330, 240, 170, 22);
		loginPanel.add(password);

		login = new JButton("登陆");
		login.setBounds(360, 270, 60, 25);
		loginPanel.add(login);

		reset = new JButton("重置");
		reset.setBounds(440, 270, 60, 25);
		loginPanel.add(reset);

		set = new JButton("设置");
		set.setBounds(280, 270, 60, 25);
		loginPanel.add(set);

	}

	private void setUpComponentListen() {
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = userName.getText().trim();
				String pass = String.valueOf(password.getPassword());
				ResultSet rs = Dao.query("select * from tb_user where uid='"
						+ name + "' and password='" + pass + "'");
				try {
					if (rs != null && rs.next()) {
						new Main(rs.getString("username"));
						Login.this.dispose();
					} else {
						JOptionPane
								.showMessageDialog(Login.this, "输入用户名或密码错误！");
						password.setText("");
						return;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				userName.setText(null);
				password.setText(null);

			}
		});
		set.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new ConnDialog();
				Login.this.dispose();
			}
		});
		password.addKeyListener(new KeyAdapter() {
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == 10)
					login.doClick();// 此方法的效果等同于用户按下并随后释放按钮
			}
		});

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}

}