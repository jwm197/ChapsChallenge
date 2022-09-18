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
		title.setFont(new Font("Arial", Font.BOLD, 36));
		title.setBounds(0, (int)(-height*0.4), width, height);
		// JButton to start game
		var start = new JButton("Start");
		start.setBounds(width/4, (int)(height*0.25), width/2, height/10);
		start.setFont(new Font("Arial", Font.BOLD, 24));
		start.addActionListener(e->GameScreen());
		// JButton to resume saved game from file selector
		var load = new JButton("Load");
		load.setBounds(width/4, (int)(height*0.375), width/2, height/10);
		load.setFont(new Font("Arial", Font.BOLD, 24));
		load.addActionListener(e->LoadGame());
		// JButton to view game rules
		var rules = new JButton("Rules");
		rules.setBounds(width/4, (int)(height*0.5), width/2, height/10);
		rules.setFont(new Font("Arial", Font.BOLD, 24));
		rules.addActionListener(e->GameRules());
		// JButton to view controls
		var controls = new JButton("Controls");
		controls.setBounds(width/4, (int)(height*0.625), width/2, height/10);
		controls.setFont(new Font("Arial", Font.BOLD, 24));
		controls.addActionListener(e->ViewControls());
		// JButton to quit game
		var quit = new JButton("Quit");
		quit.setBounds(width/4, (int)(height*0.75), width/2, height/10);
		quit.setFont(new Font("Arial", Font.BOLD, 24));
		quit.addActionListener(e->System.exit(0));
		// adds components to panel
		panel.setLayout(null);
		panel.add(title);
		panel.add(start);
		panel.add(load);
		panel.add(rules);
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
		levelText.setFont(new Font("Arial", Font.BOLD, 36));
		levelText.setBounds(0, (int)(-height*0.4), width, height);
		// JButton to go back to menu
		var back = new JButton("Back");
		back.setBounds((int)(width*0.075), (int)(height*0.75), width/5, height/10);
		back.setFont(new Font("Arial", Font.BOLD, 24));
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
	private void GameRules() {
		// Panel to stores components
		JPanel panel = new JPanel();
		// JLabel for displaying rules
		var title = new JLabel("Rules", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 36));
		title.setBounds(0, (int)(-height*0.4), width, height);
		// JButton to go back to menu
		var back = new JButton("Back");
		back.setBounds((int)(width*0.075), (int)(height*0.75), width/5, height/10);
		back.setFont(new Font("Arial", Font.BOLD, 24));
		back.addActionListener(e->MenuScreen());
		// adds components to panel
		panel.setLayout(null);
		panel.add(title);
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
		// JLabel for displaying controls
		var controlsText = new JLabel("Controls", SwingConstants.CENTER);
		controlsText.setFont(new Font("Arial", Font.BOLD, 36));
		controlsText.setBounds(0, (int)(-height*0.4), width, height);
		// JButton to go back to menu
		var back = new JButton("Back");
		back.setBounds((int)(width*0.075), (int)(height*0.75), width/5, height/10);
		back.setFont(new Font("Arial", Font.BOLD, 24));
		back.addActionListener(e->MenuScreen());
		// adds components to panel
		panel.setLayout(null);
		panel.add(controlsText);
		panel.add(back);
		closePhase.run();
		closePhase = ()->{remove(panel);};
		add(panel);
		setPreferredSize(getSize());
		pack();
	}
}
