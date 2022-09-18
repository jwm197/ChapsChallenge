package nz.ac.vuw.ecs.swen225.gp22.App;

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

class ChapsChallenge extends JFrame{
	private static final long serialVersionUID = 1L;
	Runnable closePhase = ()->{};
	int width = 1280;
	int height = 720;
	int level = 1;
	
	ChapsChallenge(){
		assert SwingUtilities.isEventDispatchThread();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		MenuScreen();
		setVisible(true);
		setResizable(false);
		addWindowListener(new WindowAdapter(){
			public void windowClosed(WindowEvent e) { closePhase.run(); }
		});
	}			
	
	private void MenuScreen() {
		// Panel to stores components
		JPanel panel = new JPanel();
		// JLabel for displaying game title
		var title = new JLabel("Chaps Challenge", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 48));
		title.setBounds(0, (int)(-height*0.4), width, height);
		// JButton to start game
		var start = new JButton("Start");
		start.setBounds(width/4, (int)(height*0.25), width/2, height/10);
		start.setFont(new Font("Arial", Font.PLAIN, 28));
		start.addActionListener(e->GameScreen());
		// JButton to resume saved game from file selector
		var load = new JButton("Load");
		load.setBounds(width/4, (int)(height*0.375), width/2, height/10);
		load.setFont(new Font("Arial", Font.PLAIN, 28));
		load.addActionListener(e->LoadGame());
		// JButton to view game help info
		var help = new JButton("Help");
		help.setBounds(width/4, (int)(height*0.5), width/2, height/10);
		help.setFont(new Font("Arial", Font.PLAIN, 28));
		help.addActionListener(e->GameHelp());
		// JButton to view controls
		var controls = new JButton("Controls");
		controls.setBounds(width/4, (int)(height*0.625), width/2, height/10);
		controls.setFont(new Font("Arial", Font.PLAIN, 28));
		controls.addActionListener(e->ViewControls());
		// JButton to quit game
		var quit = new JButton("Quit");
		quit.setBounds(width/4, (int)(height*0.75), width/2, height/10);
		quit.setFont(new Font("Arial", Font.PLAIN, 28));
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
	private void GameScreen() {
		// Panel to stores components
		JPanel panel = new JPanel();
		// JLabel to show level player is on
		var levelText = new JLabel("Level: " + level, SwingConstants.CENTER);
		levelText.setFont(new Font("Arial", Font.BOLD, 48));
		levelText.setBounds(0, (int)(-height*0.4), width, height);
		
		// DOMAIN??
		
		// JButton to go back to menu
		var back = new JButton("Back");
		back.setBounds((int)(width*0.075), (int)(height*0.75), width/5, height/10);
		back.setFont(new Font("Arial", Font.PLAIN, 28));
		back.addActionListener(e->MenuScreen());
		// adds components to panel
		panel.setLayout(null);
		panel.add(levelText);
		panel.add(back);
		closePhase.run();
		closePhase = ()->{remove(panel);};
		add(panel);
		setPreferredSize(getSize());
		pack();
	}
	private void LoadGame() {
		// Code borrowed from 
		// https://www.codejava.net/java-se/swing/show-simple-open-file-dialog-using-jfilechooser
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
	}
	private void GameHelp() {
		// Panel to stores components
		JPanel panel = new JPanel();
		// JLabel for displaying help title
		var helpTitle = new JLabel("Help", SwingConstants.CENTER);
		helpTitle.setFont(new Font("Arial", Font.BOLD, 48));
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
		helpInfo.setFont(new Font("Arial", Font.PLAIN, 28));
		helpInfo.setBounds(0, -height/20, width, height);
		// JButton to go back to menu
		var back = new JButton("Back");
		back.setBounds((int)(width*0.075), (int)(height*0.75), width/5, height/10);
		back.setFont(new Font("Arial", Font.PLAIN, 28));
		back.addActionListener(e->MenuScreen());
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
	private void ViewControls() {
		// Panel to stores components
		JPanel panel = new JPanel();
		// JLabel for displaying controls title
		var controlsText = new JLabel("Controls", SwingConstants.CENTER);
		controlsText.setFont(new Font("Arial", Font.BOLD, 48));
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
		controlsInfo.setFont(new Font("Arial", Font.PLAIN, 28));
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
		controlsKeys.setFont(new Font("Arial", Font.PLAIN, 28));
		controlsKeys.setBounds(-width/5, -height/20, width, height);
		// JButton to go back to menu
		var back = new JButton("Back");
		back.setBounds((int)(width*0.075), (int)(height*0.75), width/5, height/10);
		back.setFont(new Font("Arial", Font.PLAIN, 28));
		back.addActionListener(e->MenuScreen());
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
}
