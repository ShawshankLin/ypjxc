package com.jxc.internalWindow;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jxc.internalWindow.empManage.EmpAddPanel;
import com.jxc.internalWindow.empManage.EmpAlterPanel;

public class EmpManage extends JInternalFrame{
	public EmpManage(){
		setIconifiable(true);
		setClosable(true);
		setTitle("供应商管理");
		JTabbedPane tabPane = new JTabbedPane();
		final EmpAddPanel empAddPanel = new EmpAddPanel();
		final EmpAlterPanel empAlterPanel = new EmpAlterPanel();
		tabPane.addTab("员工信息添加", null, empAddPanel, "员工添加");
		tabPane.addTab("员工信息修改与删除", null, empAlterPanel, "修改与删除");
		getContentPane().add(tabPane);
		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				empAlterPanel.initComboBox();
			}
		});
		pack();
		setResizable(true);
		setMaximizable(true);
		setBounds(50, 50, 500,240);
		setVisible(true);
	}
}
