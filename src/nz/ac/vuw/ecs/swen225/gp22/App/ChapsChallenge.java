package nz.ac.vuw.ecs.swen225.gp22.App;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.*;
import nz.ac.vuw.ecs.swen225.gp22.Domain.*;
import nz.ac.vuw.ecs.swen225.gp22.Persistency.*;
import nz.ac.vuw.ecs.swen225.gp22.Recorder.*;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
		try {
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		var title = new JLabel("CHAPS CHALLENGE", SwingConstants.CENTER);
		title.setFont(largeFont);
		title.setBounds(0, (int)(-height*0.4), width, height);
		// JButton to start game
		var start = new JButton("Start");
		start.setBounds(width/4, (int)(height*0.25), width/2, height/10);
		start.setFont(smallFont);
		start.addActionListener(e->gameScreen());
		// JButton to resume saved game from file selector
		var load = new JButton("Load");
		load.setBounds(width/4, (int)(height*0.375), width/2, height/10);
		load.setFont(smallFont);
		load.addActionListener(e->loadGame());
		// JButton to view game help info
		var help = new JButton("Help");
		help.setBounds(width/4, (int)(height*0.5), width/2, height/10);
		help.setFont(smallFont);
		help.addActionListener(e->gameHelp());
		// JButton to view controls
		var controls = new JButton("Controls");
		controls.setBounds(width/4, (int)(height*0.625), width/2, height/10);
		controls.setFont(smallFont);
		controls.addActionListener(e->viewControls());
		// JButton to quit game
		var quit = new JButton("Quit");
		quit.setBounds(width/4, (int)(height*0.75), width/2, height/10);
		quit.setFont(smallFont);
		quit.addActionListener(e->System.exit(0));
		// adds components to panel
		panel.setLayout(null);
		panel.add(title);
		panel.add(start);
		panel.add(load);
		panel.add(help);
		panel.add(controls);
		panel.add(quit);
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
		var levelText = new JLabel("Level: " + level, SwingConstants.CENTER);
		levelText.setFont(largeFont);
		levelText.setBounds(0, (int)(-height*0.4), width, height);
		// Timer text
		var timerText = new JLabel("Timer: 60s", SwingConstants.CENTER);
		timerText.setFont(smallFont);
		timerText.setBounds(0, (int)(-height*0.325), width, height);
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
			timerText.setText("Timer: " + (float)Math.round(time*10)/10);
			time-=0.034;
			repaint();
			if (time <=0) {closePhase.run(); menuScreen();}
		});
		timer.start();
		// JButton to go back to menu
		var back = new JButton("Back");
		back.setBounds((int)(width*0.075), (int)(height*0.75), width/5, height/10);
		back.setFont(smallFont);
		back.addActionListener(e->menuScreen());
		// adds components to panel
		panel.setLayout(null);
		panel.add(levelText);
		panel.add(back);
		panel.add(timerText);
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
		var helpTitle = new JLabel("Help", SwingConstants.CENTER);
		helpTitle.setFont(largeFont);
		helpTitle.setBounds(0, (int)(-height*0.4), width, height);
		// JLabel to display help info
		var helpInfo = new JLabel("<html>"
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
				+ "View controls from the menu screen button</html>", SwingConstants.CENTER);
		helpInfo.setFont(smallFont);
		helpInfo.setBounds(0, -height/20, width, height);
		// JButton to go back to menu
		var back = new JButton("Back");
		back.setBounds((int)(width*0.075), (int)(height*0.75), width/5, height/10);
		back.setFont(smallFont);
		back.addActionListener(e->menuScreen());
		// adds components to panel
		panel.setLayout(null);
		panel.add(helpTitle);
		panel.add(helpInfo);
		panel.add(back);
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
		var controlsText = new JLabel("Controls", SwingConstants.CENTER);
		controlsText.setFont(largeFont);
		controlsText.setBounds(0, (int)(-height*0.4), width, height);
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
		var back = new JButton("Back");
		back.setBounds((int)(width*0.075), (int)(height*0.75), width/5, height/10);
		back.setFont(smallFont);
		back.addActionListener(e->menuScreen());
		// adds components to panel
		panel.setLayout(null);
		panel.add(controlsText);
		panel.add(controlsInfo);
		panel.add(controlsKeys);
		panel.add(back);
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
}
