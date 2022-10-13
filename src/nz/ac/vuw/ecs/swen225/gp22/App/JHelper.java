package nz.ac.vuw.ecs.swen225.gp22.App;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public interface JHelper {
	public static final int WIDTH = 1080;
	public static final int HEIGHT = 720;
	public static final Font LARGE_FONT = new Font("Trebuchet MS", Font.BOLD, 54);
	public static final Font SMALL_FONT = new Font("Trebuchet MS", Font.PLAIN, 28);
	public static final Font MASSIVE_FONT = new Font("Trebuchet MS", Font.PLAIN, 54);

	/**
	 * Creates a JButton and sets its parameters.
	 * 
	 * @param name text
	 * @param x x pos
	 * @param y y pos
	 * @param w width
	 * @param h height
	 * @param f font
	 * @param l action on press
	 * @return the new JButton
	 */
	public static JButton createButton(String name, int x, int y, int w, int h, Font f, ActionListener l) {
		JButton b = new JButton(name);
		b.setBounds(x, y, w, h);
		b.setFont(f);
		b.addActionListener(l);
		return b;
	}
	
	/**
	 * Creates a JLabel and sets its parameters.
	 * 
	 * @param name text
	 * @param i alignment
	 * @param f font
	 * @param x x pos
	 * @param y y pos
	 * @param w width
	 * @param h height
	 * @return the new JLabel
	 */
	public static JLabel createLabel(String name, int i, Font f, int x, int y, int w, int h) {
		JLabel l = new JLabel(name, i);
		l.setBounds(x, y, w, h);
		l.setFont(f);
		return l;
	}
	
	/**
	 * Adds a list of components to a JPanel.
	 * 
	 * @param p panel to have components added
	 * @param cs components to be added
	 */
	public static void addComponents(JPanel p, Component...cs) {
		for (Component c :cs) { p.add(c); }
	}
	
	public JPanel make();
}
