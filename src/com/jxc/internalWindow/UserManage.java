package com.jxc.internalWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.jxc.dao.Dao;
import com.jxc.internalWindow.userManage.UserAddDialog;
import com.jxc.internalWindow.userManage.UserAlterDialog;
import com.jxc.model.UserInfo;
import com.jxc.tool.EvenOddRenderer;


public class UserManage extends JInternalFrame{
	private JTable table;
	private JPanel northPanel, cenpanel, southPanel;
	private JLabel label;
	private JComboBox tjBox;
	private JTextField textField;
	private JButton qButton, addButton, modifyButton, delButton;
	private JButton allButton;
	public  UserManage() {
		init();
		setUpCompent();

	}
	private void init() {
		setIconifiable(true);
		setClosable(true);
		setTitle("用户管理");
		pack();
		setResizable(true);
		setMaximizable(true);
		setBounds(50, 50, 520, 400);
		setVisible(true);
	}
	private void setUpCompent(){
		final DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}// 表格不允许被编辑
		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		String[] tableHeads = new String[] { "用户ID", "用户名", "密码"};
		model.setColumnIdentifiers(tableHeads);
		table.setDefaultRenderer(Object.class, new EvenOddRenderer());
		//table.setPreferredScrollableViewportSize(new Dimension(550, 400));
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {

				// 是否左建双击
				if (e.getClickCount() == 2
						&& SwingUtilities.isLeftMouseButton(e)) {
					modifyButton.doClick();
				}
			}
		});
		final JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setAutoscrolls(true);
		cenpanel = new JPanel();
		cenpanel.add(scrollPane);

		label = new JLabel("选择查询条件：");
		tjBox = new JComboBox();
		tjBox.addItem("用户ID");
		tjBox.addItem("用户名");
		textField = new JTextField(40);
		qButton = new JButton("查询");
		qButton.addActionListener(new QButtonActionListener(model));
		allButton = new JButton("刷新");
		allButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				List list = Dao.getUserInfos();
				updateTable(list, model);
			}
		});
		allButton.doClick();
		northPanel = new JPanel();
		northPanel.setOpaque(false);
		northPanel.add(label);
		northPanel.add(tjBox);
		northPanel.add(textField);
		northPanel.add(qButton);
		northPanel.add(allButton);

		addButton = new JButton("添加");
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new UserAddDialog();
			}
		});
		modifyButton = new JButton("修改");
		modifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (table.getSelectedRow() >= 0) {
					UserInfo userInfo = new UserInfo();
					userInfo.setUid((String) table.getValueAt(
							table.getSelectedRow(), 0));
					userInfo.setUsername((String) table.getValueAt(
							table.getSelectedRow(), 1));
					userInfo.setPassword((String) table.getValueAt(
							table.getSelectedRow(), 2));
					new UserAlterDialog(userInfo);
					
				}
				else{
					JOptionPane.showMessageDialog(UserManage.this, "请选择一行");
				}
			}
		});
		delButton = new JButton("删除");
		delButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int count = 0;
				if (table.getSelectedRow() >= 0) {
					int yn = JOptionPane.showConfirmDialog(UserManage.this,
							"是否确定删除？");
					if (yn != 0)
						return;
					String uid = (String) table.getValueAt(
							table.getSelectedRow(), 0);
					count = Dao.delUserInfo(uid);
				}
				else{
					JOptionPane.showMessageDialog(UserManage.this, "请选择一行");
					return;
				}
				if (count > 0) {
					JOptionPane.showMessageDialog(UserManage.this, "删除用户"
							+ table.getValueAt(table.getSelectedRow(), 1)
							+ "成功！");
				} else {
					JOptionPane.showMessageDialog(UserManage.this, "删除失败！");
				}
			}
		});
		southPanel = new JPanel();
		southPanel.add(addButton);
		southPanel.add(modifyButton);
		southPanel.add(delButton);

		this.setLayout(new BorderLayout());
		this.add(northPanel, BorderLayout.NORTH);
		this.add(cenpanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}
	private void updateTable(List list, final DefaultTableModel model) {
		int num = model.getRowCount();
		for (int i = 0; i < num; i++)
			model.removeRow(0);
		Iterator iterator = list.iterator();
		UserInfo userInfo;
		while (iterator.hasNext()) {
			List info = (List) iterator.next();
			userInfo= Dao.getUserInfo((String) info.get(0));
			Vector rowData = new Vector();
			rowData.add(userInfo.getUid().trim());
			rowData.add(userInfo.getUsername().trim());
			rowData.add(userInfo.getPassword().trim());
			model.addRow(rowData);
		}
	}

	private class QButtonActionListener implements ActionListener {
		private final DefaultTableModel model;

		public QButtonActionListener(DefaultTableModel model) {
			this.model = model;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			String qStr = textField.getText().trim();
			int num = tjBox.getSelectedIndex();
			String tj = null;
			if (num == 0) {
				tj = "uid";
			} else {
				tj = "username";
			}
			if (!qStr.isEmpty()) {
				List list = Dao.findForList("select * from tb_user where " + tj
						+ "='" + qStr + "'");
				updateTable(list, model);
			} else {
				allButton.doClick();
			}
		}

	}

}
