package com.jxc.internalWindow;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jxc.internalWindow.cusManage.CusAddPanel;
import com.jxc.internalWindow.cusManage.CusAlterPanel;


public class CusManage extends JInternalFrame{
	public CusManage(){
		setIconifiable(true);
		setClosable(true);
		setTitle("客户管理");
		JTabbedPane tabPane = new JTabbedPane();
		final CusAddPanel cusAddPanel = new CusAddPanel();
		final CusAlterPanel cuslterPanel = new CusAlterPanel();
		tabPane.addTab("客户信息添加", null, cusAddPanel, "客户添加");
		tabPane.addTab("客户信息修改与删除", null, cuslterPanel, "修改与删除");
		getContentPane().add(tabPane);
		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				cuslterPanel.initComboBox();
			}
		});
		pack();
		setResizable(true);
		setMaximizable(true);
		setBounds(50, 50, 500,240);
		setVisible(true);
	}
}
