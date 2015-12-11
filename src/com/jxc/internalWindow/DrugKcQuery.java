package com.jxc.internalWindow;

import java.awt.BorderLayout;
import java.awt.Container;
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
import com.jxc.model.KuCunInfo;
import com.jxc.model.RuKuDetailInfo;
import com.jxc.tool.EvenOddRenderer;
import com.jxc.tool.Item;

public class DrugKcQuery extends JInternalFrame {
	private JTable table;
	private JPanel northPanel;
	private JLabel label;
	private JComboBox tjBox;
	private JTextField textField;
	private JButton qButton;
	private JButton allButton;

	public DrugKcQuery() {
		init();
		setUpCompent();

	}

	private void init() {
		setIconifiable(true);
		setClosable(true);
		setTitle("库存盘点");
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
		String[] tableHeads = new String[] { "药品编号", "药品名称", "入库数", "销售数",
				"库存数" };
		final DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setColumnIdentifiers(tableHeads);
		table = new JTable(model) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}// 表格不允许被编辑
		};
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		table.getColumnModel().getColumn(1).setPreferredWidth(160);
		table.setDefaultRenderer(Object.class, new EvenOddRenderer());
		final JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setAutoscrolls(true);
		cp.add(scrollPane);

		label = new JLabel("选择查询条件：");
		tjBox = new JComboBox();
		tjBox.addItem("药品编号");
		tjBox.addItem("药品名称");
		textField = new JTextField(30);
		qButton = new JButton("查询");
		qButton.addActionListener(new QButtonActionListener(model));
		allButton = new JButton("刷新");
		allButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				textField.setText("");
				List list = Dao.getKuCunInfos();
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
		KuCunInfo kcInfo;
		while (iterator.hasNext()) {
			List info = (List) iterator.next();
			Item item = new Item();
			item.setId((String) info.get(0));
			item.setName((String) info.get(1));
			kcInfo = Dao.getKuCunInfo(item);
			Vector rowData = new Vector();
			rowData.add(kcInfo.getDid().trim());
			ResultSet rs = Dao.query("select * from tb_drugInfo");
			String ypmc = null;
			try {
				if (rs != null && rs.next()) {
					ypmc = rs.getString("ypmc");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rowData.add(ypmc);
			rowData.add(kcInfo.getRks());
			rowData.add(kcInfo.getXss());
			rowData.add(kcInfo.getKcs());
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
				tj = "did";
			} else {
				tj = "did";
				ResultSet rs = Dao
						.query("select * from tb_drugInfo where ypmc='" + qStr
								+ "'");
				try {
					if (rs != null && rs.next()) {
						qStr = rs.getString("did");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			if (!qStr.isEmpty()) {
				List list = Dao.findForList("select * from tb_kucun where "
						+ tj + "='" + qStr + "'");
				updateTable(list, model);
			} else {
				allButton.doClick();
			}
		}

	}
}
