package com.jxc.internalWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import com.jxc.dao.Dao;
import com.jxc.model.CusInfo;
import com.jxc.model.DrugInfo;
import com.jxc.model.SellDetailInfo;
import com.jxc.model.SellMainInfo;
import com.jxc.tool.Item;
import com.jxc.tool.SetUpComponent;

public class DrugSell extends JInternalFrame {
	private JPanel panel;
	private final JTable table;
	private final JTextField xsrqField = new JTextField(); // 销售时间
	private final JTextField jsrField = new JTextField(); // 经手人
	private final JTextField lxrField = new JTextField(); // 联系人
	private final JComboBox cusBox = new JComboBox(); // 客户
	private final JTextField sidField = new JTextField();// 销售单号
	private final JTextField pzsField = new JTextField("0"); // 品种数量
	private final JTextField xsslField = new JTextField("0");// 销售总数
	private final JTextField xsjeField = new JTextField("0");// 合计金额
	private Date xsrqDate;
	private JComboBox ypBox;
	private final JScrollPane scrollPanel;

	public DrugSell() {
		super();
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		getContentPane().setLayout(new BorderLayout());
		setTitle("销售单");
		setBounds(50, 50, 700, 400);

		panel = new JPanel(new GridBagLayout());
		this.add(panel);

		new SetUpComponent(panel, new JLabel("销售单号："), 0, 0, 1, 0, false, false);
		sidField.setFocusable(false);
		new SetUpComponent(panel, sidField, 1, 0, 1, 140, true, false);

		new SetUpComponent(panel, new JLabel("客户："), 2, 0, 1, 0, false, false);
		cusBox.setPreferredSize(new Dimension(160, 21));
		// 供应商下拉选择框的选择事件
		cusBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doCusSelectAction();
			}
		});
		new SetUpComponent(panel, cusBox, 3, 0, 1, 1, true, false);

		new SetUpComponent(panel, new JLabel("联系人："), 4, 0, 1, 0, false, false);
		lxrField.setFocusable(false);
		new SetUpComponent(panel, lxrField, 5, 0, 1, 80, true, false);

		new SetUpComponent(panel, new JLabel("销售时间："), 0, 1, 1, 0, false, false);
		xsrqField.setFocusable(false);
		new SetUpComponent(panel, xsrqField, 1, 1, 1, 1, true, false);

		new SetUpComponent(panel, new JLabel("合计金额："), 2, 1, 1, 0, false, false);
		xsjeField.setFocusable(false);
		new SetUpComponent(panel, xsjeField, 3, 1, 1, 1, true, false);

		new SetUpComponent(panel, new JLabel("经手人："), 4, 1, 1, 0, false, false);
		new SetUpComponent(panel, jsrField, 5, 1, 1, 1, true, false);

		ypBox = new JComboBox();
		ypBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DrugInfo drugInfo = (DrugInfo) ypBox.getSelectedItem();
				// 如果选择有效就更新表格
				if (drugInfo != null && drugInfo.getDid() != null) {
					updateTable();
				}
			}
		});

		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		initTable();
		// 添加事件完成品种数量、货品总数、合计金额的计算
		table.addContainerListener(new computeInfo());
		scrollPanel = new JScrollPane(table);
		scrollPanel.setPreferredSize(new Dimension(380, 200));
		new SetUpComponent(panel, scrollPanel, 0, 2, 6, 1, true, true);

		new SetUpComponent(panel, new JLabel("品种数量："), 0, 3, 1, 0, false, false);
		pzsField.setFocusable(false);
		new SetUpComponent(panel, pzsField, 1, 3, 1, 1, true, false);

		new SetUpComponent(panel, new JLabel("货品总数："), 2, 3, 1, 0, false, false);
		xsslField.setFocusable(false);
		new SetUpComponent(panel, xsslField, 3, 3, 1, 1, true, false);

		// 单击添加按钮在表格中添加新的一行
		JButton addButton = new JButton("添加");
		addButton.addActionListener(new AddActionListener());
		new SetUpComponent(panel, addButton, 4, 3, 1, 1, false, false);

		// 单击入库按钮保存进货信息
		JButton sellButton = new JButton("销售");
		sellButton.addActionListener(new SellActionListener());
		new SetUpComponent(panel, sellButton, 5, 3, 1, 1, false, false);
		// 添加窗体监听器，完成初始化
		addInternalFrameListener(new initTasks());
	}

	// 初始化表格
	private void initTable() {
		String[] columnNames = { "商品名称", "商品编号", "销售价", "数量", "用途", "生产日期",
				"保质期" };
		((DefaultTableModel) table.getModel())
				.setColumnIdentifiers(columnNames);
		TableColumn column = table.getColumnModel().getColumn(0);
		column.setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(150);
		final DefaultCellEditor editor = new DefaultCellEditor(ypBox);
		editor.setClickCountToStart(2);
		column.setCellEditor(editor);
	}

	// 初始化入库单编号文本框的方法
	private void initSid() {
		java.sql.Date date = new java.sql.Date(xsrqDate.getTime());
		String maxId = Dao.getSellMainMaxId(date);
		sidField.setText(maxId);
	}

	// 清除空行
	private synchronized void clearEmptyRow() {
		DefaultTableModel dftm = (DefaultTableModel) table.getModel();
		for (int i = 0; i < table.getRowCount(); i++) {
			DrugInfo info2 = (DrugInfo) table.getValueAt(i, 0);
			if (info2 == null || info2.getDid() == null
					|| info2.getDid().isEmpty()) {
				dftm.removeRow(i);
			}
		}
	}

	// 停止表格单元的编辑
	private void stopTableCellEditing() {
		TableCellEditor cellEditor = table.getCellEditor();
		if (cellEditor != null)
			cellEditor.stopCellEditing();
	}

	// 初始化商品下拉选择框
	private void initYpBox() {
		List list = new ArrayList();
		ResultSet set = Dao.query(" select * from tb_druginfo"
				+ " where did in (select did from tb_kucun where kcs>0)");
		ypBox.removeAllItems();
		ypBox.addItem(new DrugInfo());
		for (int i = 0; table != null && i < table.getRowCount(); i++) {
			DrugInfo drugInfo = (DrugInfo) table.getValueAt(i, 0);
			if (drugInfo != null && drugInfo.getDid() != null)
				list.add(drugInfo.getDid());
		}
		try {
			while (set.next()) {
				DrugInfo drugInfo = new DrugInfo();
				drugInfo.setDid(set.getString("did").trim());
				// 如果表格中以存在同样商品，商品下拉框中就不再包含该商品
				if (list.contains(drugInfo.getDid()))
					continue;
				drugInfo.setYpmc(set.getString("ypmc").trim());
				drugInfo.setXsj(Float.parseFloat(set.getString("xsj").trim()));
				drugInfo.setJhj(Float.parseFloat(set.getString("jhj").trim()));
				drugInfo.setSl(Integer.parseInt(set.getString("sl").trim()));
				drugInfo.setYt(set.getString("yt").trim());
				drugInfo.setScrq(set.getString("scrq").trim());
				drugInfo.setBzq(set.getString("bzq").trim());
				drugInfo.setSccj(set.getString("sccj").trim());
				ypBox.addItem(drugInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	class SellActionListener implements ActionListener { // 销售按钮的事件监听器
		public void actionPerformed(ActionEvent e) {
			// 结束表格中没有编写的单元
			stopTableCellEditing();
			// 清除空行
			clearEmptyRow();
			String xsslStr = xsslField.getText(); // 货品总数
			String pzsStr = pzsField.getText(); // 品种数
			String xsjeStr = xsjeField.getText(); // 合计金额
			String jsrStr = jsrField.getText().trim(); // 经手人
			ResultSet emprs = Dao.query("select * from tb_empInfo where ename='"
					+ jsrStr + "'");
			String eid = null;
			try {
				if (emprs != null && emprs.next()) {
					eid = emprs.getString("eid");
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String xsrqStr = xsrqDate.toLocaleString(); // 入库时间
			String sid = sidField.getText(); // 单号
			String cusName = cusBox.getSelectedItem().toString();// 供应商名字
			ResultSet cusrs = Dao
					.query("select * from tb_cusInfo where cname='" + cusName
							+ "'");
			String cid = null;
			try {
				if (cusrs != null && cusrs.next()) {
					cid = cusrs.getString("cid");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (jsrStr == null || jsrStr.isEmpty()) {
				JOptionPane.showMessageDialog(DrugSell.this, "请填写经手人");
				return;
			}
			if (table.getRowCount() <= 0) {
				JOptionPane.showMessageDialog(DrugSell.this, "填加入库商品");
				return;
			}
			SellMainInfo sellMain = new SellMainInfo(sid,cid,
					Integer.parseInt(pzsStr), Integer.parseInt(xsslStr),
					Float.parseFloat(xsjeStr), xsrqStr, eid);
			Set<SellDetailInfo> set = sellMain.getTabSellDetails();
			int rows = table.getRowCount();
			for (int i = 0; i < rows; i++) {
				DrugInfo druginfo = (DrugInfo) table.getValueAt(i, 0);
				SellDetailInfo detail = new SellDetailInfo();
				detail.setSid(sellMain.getSid());
				detail.setDid(druginfo.getDid());
				Integer num = Integer.parseInt(table.getValueAt(i, 3)
						.toString());
				detail.setSl(num.intValue());
				detail.setXsj(druginfo.getXsj());
				Float f=num.intValue() * druginfo.getXsj();
				detail.setJe(f);
				set.add(detail);
			}
			boolean rs = Dao.insertDrugSellInfo(sellMain);
			if (rs) {
				JOptionPane.showMessageDialog(DrugSell.this, "销售完成");
				DefaultTableModel dftm = new DefaultTableModel();
				table.setModel(dftm);
				initTable();
				pzsField.setText("0");
				xsslField.setText("0");
				xsjeField.setText("0");
			}
		}
	}
	// 客户选择时更新联系人字段
	private void doCusSelectAction() {
		Item item = (Item) cusBox.getSelectedItem();
		CusInfo cusInfo = Dao.getCusInfo(item);
		lxrField.setText(cusInfo.getLxr());
		initYpBox();
	}

	// 窗体的初始化任务
	private final class initTasks extends InternalFrameAdapter {
		public void internalFrameActivated(InternalFrameEvent e) {
			super.internalFrameActivated(e);
			initTimeField();
			initCusField();
			initSid();
			initYpBox();
		}

		private void initCusField() {// 初始化客户字段
			List cusInfos = Dao.getCusInfos();
			for (Iterator iter = cusInfos.iterator(); iter.hasNext();) {
				List list = (List) iter.next();
				Item item = new Item();
				item.setId(list.get(0).toString().trim());
				item.setName(list.get(1).toString().trim());
				cusBox.addItem(item);
			}
			doCusSelectAction();
		}

		private void initTimeField() {// 启动进货时间线程
			new Thread(new Runnable() {
				public void run() {
					try {
						while (true) {
							xsrqDate = new Date();
							xsrqField.setText(xsrqDate.toLocaleString());
							Thread.sleep(100);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
	// 根据药品下拉框的选择，更新表格当前行的内容
	private synchronized void updateTable() {
		DrugInfo drugInfo = (DrugInfo) ypBox.getSelectedItem();
		int row = table.getSelectedRow();
		if (row >= 0 && drugInfo != null) {
			table.setValueAt(drugInfo.getDid(), row, 1);
			table.setValueAt(drugInfo.getXsj(), row, 2);
			table.setValueAt("0", row, 3);
			table.setValueAt(drugInfo.getYt(), row, 4);
			table.setValueAt(drugInfo.getScrq(), row, 5);
			table.setValueAt(drugInfo.getBzq(), row, 6);
			table.editCellAt(row, 3);
		}
	}
	class AddActionListener implements ActionListener { // 添加按钮的事件监听器
		public void actionPerformed(ActionEvent e) {
			// 初始化票号
			initSid();
			// 结束表格中没有编写的单元
			stopTableCellEditing();
			// 如果表格中还包含空行，就再添加新行
			for (int i = 0; i < table.getRowCount(); i++) {
				DrugInfo info = (DrugInfo) table.getValueAt(i, 0);
				// if (table.getValueAt(i, 0) == null)
				// return;
			}
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.addRow(new Vector());
			initYpBox();
		}
	}
	// 在事件中计算品种数量、货品总数、合计金额
		private final class computeInfo implements ContainerListener {
			public void componentRemoved(ContainerEvent e) {
				// 清除空行
				clearEmptyRow();
				// 计算代码
				int rows = table.getRowCount();
				int count = 0;
				double money = 0.0;
				// 计算品种数量
				DrugInfo column = null;
				if (rows > 0)
					column = (DrugInfo) table.getValueAt(rows - 1, 0);
				if (rows > 0 && (column == null || column.getDid().isEmpty()))
					rows--;
				// 计算货品总数和金额
				for (int i = 0; i < rows; i++) {
					Float column2 = (Float) table.getValueAt(i, 2);
					String column3 = (String) table.getValueAt(i, 3);
					int c3 = (column3 == null || column3.isEmpty()) ? 0 : Integer
							.parseInt(column3);
					float c2 = (column2 == null) ? 0 : column2.floatValue();
					count += c3;
					money += c3 * c2;
				}

				pzsField.setText(rows + "");
				xsslField.setText(count + "");
				xsjeField.setText(money + "");
				// /////////////////////////////////////////////////////////////////
			}

			public void componentAdded(ContainerEvent e) {
				
			}
		}

}
