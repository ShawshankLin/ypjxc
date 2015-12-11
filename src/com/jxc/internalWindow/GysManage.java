package com.jxc.internalWindow;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.jxc.internalWindow.gysManage.GysAddPanel;
import com.jxc.internalWindow.gysManage.GysAlterPanel;

public class GysManage extends JInternalFrame{
	public GysManage() {
		setIconifiable(true);
		setClosable(true);
		setTitle("供应商管理");
		JTabbedPane tabPane = new JTabbedPane();
		final GysAddPanel gysAddPanel = new GysAddPanel();
		final GysAlterPanel gysAlterPanel = new GysAlterPanel();
		tabPane.addTab("供应商信息添加", null, gysAddPanel, "供应商添加");
		tabPane.addTab("供应商信息修改与删除", null, gysAlterPanel, "修改与删除");
		getContentPane().add(tabPane);
		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				gysAlterPanel.initComboBox();
			}
		});
		pack();
		setResizable(true);
		setMaximizable(true);
		setBounds(50, 50, 510,260);
		setVisible(true);
	}
}
