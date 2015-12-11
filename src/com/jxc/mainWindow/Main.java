package com.jxc.mainWindow;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.jxc.tool.SetUpComponent;

public class Main implements ActionListener {
	private JDesktopPane desktopPane;
	private JFrame frame;
	private Container cp;
	private JPanel westPanel, northPanel, southPanel;
	// private BackImage centerPanel;
	private CardLayout cl;
	private JLabel timeNow;
	private javax.swing.Timer t;
	// 背景图标签
	private JLabel backLabel;;
	private ImageIcon backIcon;
	// 头部标签
	private JLabel topLabel;
	/* 基本信息管理面板 */
	private JPanel panel1, baseManagePanel, panel1other;
	private JButton baseManagebut_1, drugManagebut_1, sellManagebut_1,
			sysManagebut_1;
	/* 药品管理面板 */
	private JPanel panel2, drugManagePanel, panel2top, panel2bot;
	private JButton baseManagebut_2, drugManagebut_2, sellManagebut_2,
			sysManagebut_2;
	/* 销售管理面板 */
	private JPanel panel3, sellManagePanel, panel3top, panel3bot;
	private JButton baseManagebut_3, drugManagebut_3, sellManagebut_3,
			sysManagebut_3;
	/* 系统管理面板 */
	private JPanel panel4, sysManagePanel, panel4top;
	private JButton baseManagebut_4, drugManagebut_4, sellManagebut_4,
			sysManagebut_4;
	// 创建窗体的Map类型集合对象
	private Map<String, JInternalFrame> ifs = new HashMap<String, JInternalFrame>();
	private String username;
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void main(String args[]){
		new Main("linxiaosheng");
	}
	public Main(String username) {
		this.username=username;
		init();
	}

	private void init() {
		frame = new JFrame("药品进销存管理系统");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		// 设置背景图片
		backIcon = new ImageIcon("images/back.png");
		backLabel = new JLabel(backIcon);
		backLabel.setVerticalAlignment(SwingConstants.TOP);// 指定背景图片标签沿Y轴的对齐方式
		backLabel.setHorizontalAlignment(SwingConstants.CENTER);// 返回标签内容沿X轴的对齐方式。
		frame.getLayeredPane().add(backLabel, new Integer(Integer.MIN_VALUE));
		backLabel.setBounds(0, 0, backIcon.getIconWidth(),
				backIcon.getIconHeight());// 设置背景标签的位置

		cp = frame.getContentPane();
		cp.setLayout(new BorderLayout());
		setUpUIComponent();
		((JPanel) cp).setOpaque(false);

		getTime();
		// 设置居中
		int width = frame.getToolkit().getDefaultToolkit().getScreenSize().width;
		int height = frame.getToolkit().getDefaultToolkit().getScreenSize().height;
		frame.setBounds(width / 2 - 350, height / 2 - 350, 790, 700);
		frame.setVisible(true);

	}

	private void setUpUIComponent() {
		desktopPane = new JDesktopPane();
		desktopPane.setOpaque(false);
		cp.add(desktopPane);

		// 上方面板
		northPanel = new JPanel();
		northPanel.setBackground(new Color(222, 239, 240));
		topLabel = new JLabel(new ImageIcon("images/topback.png"));
		northPanel.add(topLabel);

		// 左侧导航面板
		westPanel = new JPanel();
		cl = new CardLayout();
		westPanel.setLayout(cl);
		// 面板1组件
		panel1 = new JPanel(new BorderLayout());
		baseManagebut_1 = new JButton("信息管理");
		baseManagePanel = new JPanel(new GridLayout(6, 1));
		baseManagePanel.add(createSonButton("CusManage", "客户信息管理"));
		baseManagePanel.add(createSonButton("GysManage", "供应商信息管理"));
		baseManagePanel.add(createSonButton("EmpManage", "员工信息管理"));
		baseManagePanel.add(createSonButton("CusQuery", "客户查询"));
		baseManagePanel.add(createSonButton("GysQuery", "供应商查询"));
		baseManagePanel.add(createSonButton("EmpQuery", "员工查询"));
		panel1other = new JPanel(new GridLayout(3, 1));
		drugManagebut_1 = createFatherButton("药品管理");
		drugManagebut_1.addActionListener(new DrugManagePanel());
		sellManagebut_1 = createFatherButton("销售管理");
		sellManagebut_1.addActionListener(new SellManagepanel());
		sysManagebut_1 = createFatherButton("系统管理");
		sysManagebut_1.addActionListener(new SysManagepanel());
		panel1other.add(drugManagebut_1);
		panel1other.add(sellManagebut_1);
		panel1other.add(sysManagebut_1);
		panel1.add(baseManagebut_1, "North");
		panel1.add(baseManagePanel, "Center");
		panel1.add(panel1other, "South");
		// 面板2组件
		panel2 = new JPanel(new BorderLayout());
		panel2top = new JPanel(new GridLayout(2, 1));
		baseManagebut_2 = createFatherButton("信息管理");
		baseManagebut_2.addActionListener(new BaseManagePanel());
		drugManagebut_2 = new JButton("药品管理");
		panel2top.add(baseManagebut_2);
		panel2top.add(drugManagebut_2);
		drugManagePanel = new JPanel(new GridLayout(4, 1));
		drugManagePanel.add(createSonButton("DrugManage", "药品信息管理"));
		drugManagePanel.add(createSonButton("DrugRK", "药品入库"));
		drugManagePanel.add(createSonButton("DrugQuery", "药品查询"));
		drugManagePanel.add(createSonButton("DrugRkQuery", "药品入库查询"));
		panel2bot = new JPanel(new GridLayout(2, 1));
		sellManagebut_2 = createFatherButton("销售管理");
		sellManagebut_2.addActionListener(new SellManagepanel());
		sysManagebut_2 = createFatherButton("系统管理");
		sysManagebut_2.addActionListener(new SysManagepanel());
		panel2bot.add(sellManagebut_2);
		panel2bot.add(sysManagebut_2);
		panel2.add(panel2top, "North");
		panel2.add(drugManagePanel, "Center");
		panel2.add(panel2bot, "South");
		// 面板3组件
		panel3 = new JPanel(new BorderLayout());
		panel3top = new JPanel(new GridLayout(3, 1));
		baseManagebut_3 = createFatherButton("信息管理");
		baseManagebut_3.addActionListener(new BaseManagePanel());
		drugManagebut_3 = createFatherButton("药品管理");
		drugManagebut_3.addActionListener(new DrugManagePanel());
		sellManagebut_3 = new JButton("销售管理");
		panel3top.add(baseManagebut_3);
		panel3top.add(drugManagebut_3);
		panel3top.add(sellManagebut_3);
		sellManagePanel = new JPanel(new GridLayout(3, 1));
		sellManagePanel.add(createSonButton("DrugSell", "销售录入"));
		sellManagePanel.add(createSonButton("DrugSellQuery", "销售查询"));
		sellManagePanel.add(createSonButton("DrugKcQuery", "库存盘点"));
		panel3bot = new JPanel(new GridLayout(1, 1));
		sysManagebut_3 = createFatherButton("系统管理");
		sysManagebut_3.addActionListener(new SysManagepanel());
		panel3bot.add(sysManagebut_3);
		panel3.add(panel3top, "North");
		panel3.add(sellManagePanel, "Center");
		panel3.add(panel3bot, "South");
		// 面板4组件
		panel4 = new JPanel(new BorderLayout());
		panel4top = new JPanel(new GridLayout(4, 1));
		baseManagebut_4 = createFatherButton("信息管理");
		baseManagebut_4.addActionListener(new BaseManagePanel());
		drugManagebut_4 = createFatherButton("药品管理");
		drugManagebut_4.addActionListener(new DrugManagePanel());
		sellManagebut_4 = createFatherButton("销售管理");
		sellManagebut_4.addActionListener(new SellManagepanel());
		sysManagebut_4 = new JButton("系统管理");
		panel4top.add(baseManagebut_4);
		panel4top.add(drugManagebut_4);
		panel4top.add(sellManagebut_4);
		panel4top.add(sysManagebut_4);
		sysManagePanel = new JPanel(new GridLayout(4, 1));
		sysManagePanel.add(createSonButton("UserManage", "用户管理"));
		panel4.add(panel4top, "North");
		panel4.add(sysManagePanel, "Center");

		westPanel.add(panel1, "1");
		westPanel.add(panel2, "2");
		westPanel.add(panel3, "3");
		westPanel.add(panel4, "4");

		cp.add(northPanel, BorderLayout.NORTH);
		cp.add(westPanel, BorderLayout.WEST);

	}

	/** *********************辅助方法************************* */
	// 得到系统当前时间
	public void getTime() {
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		t = new Timer(1000, this);// 每隔1000毫秒计时一次
		t.start();
		int hours = Calendar.getInstance().getTime().getHours();
		String hello;
		if (hours < 12)
			hello = "早上好！";
		else if (hours > 12 && hours < 18)
			hello = "下午好！";
		else
			hello = "晚上好！";
		timeNow = new JLabel(hello+username+",现在是："+Calendar.getInstance().getTime().toLocaleString());// 取得系统时间
		//timeNow.setFont(new Font("微软雅黑", Font.BOLD, 14));
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(timeNow, BorderLayout.EAST);
		southPanel.add(p1);
		frame.add(southPanel, BorderLayout.SOUTH);
	}

	private JButton createFatherButton(String text) {
		JButton button = new JButton(text, new ImageIcon("images/打开.png"));
		button.setVerticalTextPosition(JButton.CENTER);
		button.setHorizontalTextPosition(JButton.LEFT);
		return button;
	}

	// 为内部窗体添加Action的方法
	private JButton createSonButton(String fName, String cName) {
		String imgUrl = "images/" + cName + ".png";
		Icon icon = new ImageIcon(imgUrl);
		Action action = new openFrameAction(fName, cName, icon);
		JButton button = new JButton(action);
		Cursor myCursor = new Cursor(Cursor.HAND_CURSOR);// 手状光标
		button.setCursor(myCursor);
		button.setVerticalTextPosition(JButton.BOTTOM);
		button.setHorizontalTextPosition(JButton.CENTER);
		button.setMargin(new Insets(0, 0, 0, 0));// 设置边框与标签的距离
		// button.setHideActionText(true);//不显示Action文本
		button.setFocusPainted(false);// 设置焦点状态
		button.setBorderPainted(false);// 不绘制按钮边框
		button.setContentAreaFilled(false);// 图片将绘制按钮内容区域
		return button;
	}

	// 获取内部窗体的唯一实例对象
	private JInternalFrame getIFrame(String frameName) {
		JInternalFrame jf = null;
		if (!ifs.containsKey(frameName)) {
			try {

				Class fClass = Class.forName("com.jxc.internalWindow."
						+ frameName);
				Constructor constructor = fClass.getConstructor(null);
				jf = (JInternalFrame) constructor.newInstance(null);
				jf.setVisible(true);
				ifs.put(frameName, jf);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			jf = ifs.get(frameName);
		return jf;
	}

	// 主窗体菜单项的单击事件监听器
	protected final class openFrameAction extends AbstractAction {
		private String frameName = null;

		private openFrameAction() {
		}

		public openFrameAction(String frameName, String cname, Icon icon) {
			this.frameName = frameName;
			putValue(Action.NAME, cname);
			putValue(Action.SHORT_DESCRIPTION, cname);
			putValue(Action.SMALL_ICON, icon);
		}

		public void actionPerformed(final ActionEvent e) {
			JInternalFrame jf = getIFrame(frameName);
			// 在内部窗体闭关时，从内部窗体容器ifs对象中清除该窗体。
			jf.addInternalFrameListener(new InternalFrameAdapter() {
				public void internalFrameClosed(InternalFrameEvent e) {
					ifs.remove(frameName);
				}
			});
			if (jf.getDesktopPane() == null) {

				desktopPane.add(jf);
				jf.setVisible(true);
			}
			try {
				jf.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}
	}

	// 创建内部类来监听每个事件源产生的事件
	private class DrugManagePanel implements ActionListener {// 添加

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			cl.show(westPanel, "2");// 显示库存管理面板
		}

	}

	private class BaseManagePanel implements ActionListener {// 添加

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			cl.show(westPanel, "1");// 显示基本信息管理面板
		}

	}

	private class SellManagepanel implements ActionListener {// 添加

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			cl.show(westPanel, "3");// 显示销售面板
		}

	}

	private class SysManagepanel implements ActionListener {// 添加

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			cl.show(westPanel, "4");// 显示系统管理面板
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
