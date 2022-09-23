package nz.ac.vuw.ecs.swen225.gp22.App;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.*;
import nz.ac.vuw.ecs.swen225.gp22.Domain.*;
import nz.ac.vuw.ecs.swen225.gp22.Persistency.*;
import nz.ac.vuw.ecs.swen225.gp22.Recorder.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ChapsChallenge extends JFrame{
	private static final long serialVersionUID = 1L;
	Runnable closePhase = ()->{};
	int width = 1280;
	int height = 720;
	Font largeFont = new Font("Trebuchet MS", Font.BOLD, 54);
	Font smallFont = new Font("Trebuchet MS", Font.PLAIN, 28);
	int level = 1;
	float time = 60;
	// DOMAIN/RENDERER
	/*
	 * RenderPanel renderPanel;
	 * DomainObject domainObject;
	 */
	
	ChapsChallenge(){
		assert SwingUtilities.isEventDispatchThread();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try { UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); } 
		catch (ClassNotFoundException e1) { e1.printStackTrace(); } 
		catch (InstantiationException e1) { e1.printStackTrace(); } 
		catch (IllegalAccessException e1) { e1.printStackTrace(); } 
		catch (UnsupportedLookAndFeelException e1) { e1.printStackTrace(); }
		menuScreen();
		setVisible(true);
		setResizable(false);
		addWindowListener(new WindowAdapter(){
			public void windowClosed(WindowEvent e) { closePhase.run(); }
		});
	}			
	
	public void menuScreen() {
		// Panel to stores components
		JPanel panel = new JPanel();
		// JLabel for displaying game title
		var title = createLabel("CHAPS CHALLENGE", SwingConstants.CENTER, largeFont, 0, (int)(-height*0.4), width, height);
		// JButton to start game
		var start = createButton("Start", width/4, (int)(height*0.25), width/2, height/10, smallFont, e->gameScreen());
		// JButton to resume saved game from file selector
		var load = createButton("Load", width/4, (int)(height*0.375), width/2, height/10, smallFont, e->loadGame());
		// JButton to view game help info
		var help = createButton("Help", width/4, (int)(height*0.5), width/2, height/10, smallFont, e->gameHelp());
		// JButton to view controls
		var controls = createButton("Controls", width/4, (int)(height*0.625), width/2, height/10, smallFont, e->viewControls());
		// JButton to quit game
		var quit = createButton("Quit", width/4, (int)(height*0.75), width/2, height/10, smallFont, e->System.exit(0));
		// adds components to panel
		panel.setLayout(null);
		addComponents(panel, title, start, load, help, controls, quit);
		closePhase.run();
		closePhase = ()->{remove(panel);};
		add(panel);
		setPreferredSize(new Dimension(width,height));
		pack();
	}
	public void gameScreen() {
		// Panel to stores components
		JPanel panel = new JPanel();
		// JLabel to show level player is on
		var levelText = createLabel("Level: " + level, SwingConstants.CENTER, largeFont, 0, (int)(-height*0.4), width, height);
		// Timer text
		var timerText = createLabel("Timer: 60s", SwingConstants.CENTER, smallFont, 0, (int)(-height*0.325), width, height);
		time = 60;
		// DOMAIN/RENDERER
		/*
		 * renderPanel = new RenderPanel(); // RenderPanel extends JPanel
		 * renderPanel.bind(domainObject);  // this can be done at any time allowing dynamic level switching
		 */

		Timer timer = new Timer(34,unused->{
			assert SwingUtilities.isEventDispatchThread();
			// DOMAIN/RENDERER
			/*
			 * renderPanel.tick(); // RenderPanel must be ticked first to ensure animations that are finishing can be requeued by domain if desired
			 * domainObject.tick();
			 * renderPanel.repaint();
			 */
			
			// updating timer
			time-=0.034;
			timerText.setText("Timer: " + (float)Math.round(time*10)/10);
			repaint();
			if (time <=0) {
				time = 0;
				timerText.setText("Timer: NO TIME LEFT");
				repaint();
				int result = JOptionPane.showConfirmDialog(this,
						"<html>You ran out of time!<br/>Would you like to retry level <html>"+level+"?", 
						"Level Failed!",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if(result == JOptionPane.YES_OPTION){ gameScreen(); }
				else if (result == JOptionPane.NO_OPTION){ closePhase.run(); menuScreen(); }
				else { closePhase.run(); menuScreen(); }
			}
		});
		timer.start();
		// JButton to go back to menu
		var back = createButton("Back", (int)(width*0.075), (int)(height*0.75), width/5, height/10, smallFont, e->menuScreen());
		// adds components to panel
		panel.setLayout(null);
		addComponents(panel, levelText, back, timerText);
		closePhase.run();
		closePhase = ()->{remove(panel); timer.stop();};
		add(panel);
		setPreferredSize(getSize());
		pack();
	}
	public void loadGame() {
		// Code borrowed from 
		// https://www.codejava.net/java-se/swing/show-simple-open-file-dialog-using-jfilechooser
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		    // PERSISTENCY
		    /*
		     *  loadXML(selectedFile);
		     */
		}
	}
	public void gameHelp() {
		// Panel to stores components
		JPanel panel = new JPanel();
		// JLabel for displaying help title
		var helpTitle = createLabel("Help", SwingConstants.CENTER, largeFont, 0, (int)(-height*0.4), width, height);
		// JLabel to display help info
		var helpInfo = createLabel("<html>"
				+ "Complete the level within the time limit<br/>"
				+ "Countdown is displayed at the top<br/>"
				+ "You cannot enter locked doors<br/>"
				+ "Pick up keys to unlock doors<br/>"
				+ "Inventory is displayed to the side<br/>"
				+ "Inventory shows collected keys<br/>"
				+ "Info tile will display help text<br/>"
				+ "Main objective is to collect all treasures<br/>"
				+ "Exit lock will be open when all treasures are collected<br/>"
				+ "Enter exit tile once unlocked to pass level<br/>"
				+ "View controls from the menu screen button</html>", 
				SwingConstants.CENTER, smallFont, 0, -height/20, width, height);
		// JButton to go back to menu
		var back = createButton("Back", (int)(width*0.075), (int)(height*0.75), width/5, height/10, smallFont, e->menuScreen());
		// adds components to panel
		panel.setLayout(null);
		addComponents(panel, helpTitle, helpInfo, back);
		closePhase.run();
		closePhase = ()->{remove(panel);};
		add(panel);
		setPreferredSize(getSize());
		pack();
	}
	public void viewControls() {
		// Panel to stores components
		JPanel panel = new JPanel();
		// JLabel for displaying controls title
		var controlsText = createLabel("Controls", SwingConstants.CENTER, largeFont, 0, (int)(-height*0.4), width, height);
		// JLabel to display controls info
		var controlsInfo = new JLabel("<html>"
				+ "Exit Without Saving:<br/>"
				+ "Save and Exit:<br/>"
				+ "Resume Saved Game:<br/>"
				+ "Start Level 1:<br/>"
				+ "Start Level 2:<br/>"
				+ "Pause:<br/>"
				+ "Unpause:<br/>"
				+ "Move Chap Up:<br/>"
				+ "Move Chap Down:<br/>"
				+ "Move Chap Left:<br/>"
				+ "Move Chap Right:</html>", SwingConstants.LEFT);
		controlsInfo.setFont(smallFont);
		controlsInfo.setBounds(width/5, -height/20, width, height);
		// JLabel to display controls keys
		var controlsKeys = new JLabel("<html>"
				+ "CTRL+X<br/>"
				+ "CTRL+S<br/>"
				+ "CTRL+R<br/>"
				+ "CTRL+1<br/>"
				+ "CTRL+2<br/>"
				+ "SPACE<br/>"
				+ "ESC<br/>"
				+ "UP<br/>"
				+ "DOWN<br/>"
				+ "LEFT<br/>"
				+ "RIGHT</html>", SwingConstants.RIGHT);
		controlsKeys.setFont(smallFont);
		controlsKeys.setBounds(-width/5, -height/20, width, height);
		// JButton to go back to menu
		var back = createButton("Back", (int)(width*0.075), (int)(height*0.75), width/5, height/10, smallFont, e->menuScreen());
		// adds components to panel
		panel.setLayout(null);
		addComponents(panel, controlsText, controlsInfo, controlsKeys, back);
		closePhase.run();
		closePhase = ()->{remove(panel);};
		add(panel);
		setPreferredSize(getSize());
		pack();
	}
	// FUZZ
	/*
	public Game testInput(Direction dir) {
		game.move(dir);
		return game;
	}
	*/
	public JButton createButton(String name, int x, int y, int w, int h, Font f, ActionListener l) {
		JButton b = new JButton(name);
		b.setBounds(x,y,w,h);
		b.setFont(f);
		b.addActionListener(l);
		return b;
	}
	public JLabel createLabel(String name, int i, Font f, int x, int y, int w, int h) {
		JLabel l = new JLabel(name, i);
		l.setBounds(x,y,w,h);
		l.setFont(f);
		return l;
	}
	public void addComponents(JPanel p, Component...cs) {
		for (Component c :cs) { p.add(c); }
	}
}
