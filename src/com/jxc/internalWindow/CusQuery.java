package com.jxc.internalWindow;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.jxc.dao.Dao;
import com.jxc.model.CusInfo;
import com.jxc.tool.EvenOddRenderer;
import com.jxc.tool.Item;

public class CusQuery extends JInternalFrame {
	private JTable table;
	private JPanel northPanel;
	private JLabel label;
	private JComboBox tjBox;
	private JTextField textField;
	private JButton qButton;
	private JButton allButton;

	public CusQuery() {
		init();
		setUpCompent();

	}

	private void init() {
		setIconifiable(true);
		setClosable(true);
		setTitle("客户查询");
		pack();
		setResizable(true);
		setMaximizable(true);
		setBounds(50, 50, 520, 400);
		setVisible(true);
	}

	private void setUpCompent() {
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());

		table = new JTable();
		String[] tableHeads = new String[] { "客户ID", "客户名称", "联系人", "性别", "年龄",
				"联系方式", "邮	件", "开户银行", "银行账号", "客户住址" };
		final DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setColumnIdentifiers(tableHeads);
		table = new JTable(model) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}// 表格不允许被编辑
		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setDefaultRenderer(Object.class, new EvenOddRenderer());
		final JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setAutoscrolls(true);
		cp.add(scrollPane);

		label = new JLabel("选择查询条件：");
		tjBox = new JComboBox();
		tjBox.addItem("客户编号");
		tjBox.addItem("客户名称");
		textField = new JTextField(30);
		qButton = new JButton("查询");
		qButton.addActionListener(new QButtonActionListener(model));
		allButton = new JButton("刷新");
		allButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				textField.setText("");
				List list = Dao.getCusInfos();
				updateTable(list, model);
			}
		});
		allButton.doClick();
		northPanel = new JPanel();
		northPanel.add(label);
		northPanel.add(tjBox);
		northPanel.add(textField);
		northPanel.add(qButton);
		northPanel.add(allButton);
		cp.add(northPanel, BorderLayout.NORTH);

	}

	private void updateTable(List list, final DefaultTableModel model) {
		int num = model.getRowCount();
		for (int i = 0; i < num; i++)
			model.removeRow(0);
		Iterator iterator = list.iterator();
		CusInfo cusInfo;
		while (iterator.hasNext()) {
			List info = (List) iterator.next();
			Item item = new Item();
			item.setId((String) info.get(0));
			item.setName((String) info.get(1));
			cusInfo = Dao.getCusInfo(item);
			Vector rowData = new Vector();
			rowData.add(cusInfo.getCid().trim());
			rowData.add(cusInfo.getCname().trim());
			rowData.add(cusInfo.getLxr().trim());
			rowData.add(cusInfo.getSex().trim());
			rowData.add(Integer.valueOf(cusInfo.getAge()).toString().trim());
			rowData.add(cusInfo.getTel().trim());
			rowData.add(cusInfo.getEmail().trim());
			rowData.add(cusInfo.getKhyh().trim());
			rowData.add(cusInfo.getYhzh().trim());
			rowData.add(cusInfo.getAddress().trim());
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
				tj = "cid";
			} else {
				tj = "cname";
			}
			if (!qStr.isEmpty()) {
				List list = Dao.findForList("select * from tb_cusInfo where "
						+ tj + "='" + qStr + "'");
				updateTable(list, model);
			} else {
				allButton.doClick();
			}
		}

	}
}
