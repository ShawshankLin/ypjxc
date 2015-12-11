package com.jxc.login;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
public class LoginPanel extends JPanel {
	private ImageIcon icon;
	private int width,height;
	public LoginPanel() {
		super();
		icon = new ImageIcon("images/login.jpg");
		width = icon.getIconWidth();
		height = icon.getIconHeight();
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image img = icon.getImage();
		g.drawImage(img, 0, 0,getParent());
	}
}