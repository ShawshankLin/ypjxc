package com.jxc.internalWindow;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import com.jxc.internalWindow.drugManage.*;

public class DrugManage extends JInternalFrame{
	public DrugManage() {
		setIconifiable(true);
		setClosable(true);
		setTitle("药品管理");
		JTabbedPane tabPane = new JTabbedPane();
		final DrugAddPanel drugAddPanel = new DrugAddPanel();
		final DrugAlterPanel drugAlterPanel = new DrugAlterPanel();
		tabPane.addTab("药品信息添加", null, drugAddPanel, "药品添加");
		tabPane.addTab("药品信息修改与删除", null, drugAlterPanel, "修改与删除");
		getContentPane().add(tabPane);
		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				drugAlterPanel.initSccjBox();
				drugAlterPanel.initDrugBox();
			}
		});
		//初始化供应商下拉选择框
		addInternalFrameListener(new InternalFrameAdapter(){
			public void internalFrameActivated(InternalFrameEvent e) {
				super.internalFrameActivated(e);
				drugAddPanel.initSccjBox();
			}
		});
		pack();
		setBounds(50, 50, 500,220);
		setResizable(true);
		setMaximizable(true);
		setVisible(true);
	}
}
