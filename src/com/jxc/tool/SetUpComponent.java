package com.jxc.tool;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class SetUpComponent {
	public SetUpComponent() {

	}
	// 设置组件位置并添加到容器中
	public SetUpComponent(JPanel panel, JComponent component, int gridx,
			int gridy, int gridwidth, int ipadx, boolean fill) {
		final GridBagConstraints gridBagConstrains = new GridBagConstraints();
		JPanel p = panel;
		gridBagConstrains.gridx = gridx;
		gridBagConstrains.gridy = gridy;
		gridBagConstrains.insets = new Insets(5, 1, 3, 1);
		if (gridwidth > 1)
			gridBagConstrains.gridwidth = gridwidth;
		if (ipadx > 0)
			gridBagConstrains.ipadx = ipadx;
		if (fill)
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
		p.add(component, gridBagConstrains);
	}
	// 设置组件位置并添加到容器中
	public  SetUpComponent(JPanel panel,JComponent component, int gridx, int gridy,
			int gridwidth, int ipadx, boolean fill ,boolean flag ) {
		final GridBagConstraints gridBagConstrains = new GridBagConstraints();
		JPanel p=panel;
		gridBagConstrains.gridx = gridx;
		gridBagConstrains.gridy = gridy;
		if (gridwidth > 1)
			gridBagConstrains.gridwidth = gridwidth;
		if (ipadx > 0){
			gridBagConstrains.ipadx = ipadx;
		}
		gridBagConstrains.insets = new Insets(5, 1, 3, 1);
		if (fill && flag){
			gridBagConstrains.weightx = 1;
			gridBagConstrains.weighty = 1;
			gridBagConstrains.fill = GridBagConstraints.BOTH;
		}
		if (fill && !flag){
			gridBagConstrains.weightx = 1;
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
		}
		p.add(component, gridBagConstrains);
	}
}
