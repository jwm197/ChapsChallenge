package nz.ac.vuw.ecs.swen225.gp22.App;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.*;
import nz.ac.vuw.ecs.swen225.gp22.Domain.*;
import nz.ac.vuw.ecs.swen225.gp22.Persistency.*;
import nz.ac.vuw.ecs.swen225.gp22.Recorder.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.dom4j.DocumentException;

/**
 * Manages GUI for the Chaps Challenge game
 * 
 * @author pratapshek
 */

public class ChapsChallenge extends JFrame{
	// Final variables
	public static final int WIDTH = 1080;
	public static final int HEIGHT = 720;
	public static final Font LARGE_FONT = new Font("Trebuchet MS", Font.BOLD, 54);
	public static final Font SMALL_FONT = new Font("Trebuchet MS", Font.PLAIN, 28);
	public static final double delay = 0.017;
	
	// Private variables
	private static final long serialVersionUID = 1L;
	private Runnable closePhase = ()->{};
	private String level = "level1.xml";
	private float time = 60;
	private Timer timer;
	private boolean autoReplay;
	MoveDirection currentMove;
	private Runnable afterMove;
	
	// DOMAIN/RENDERER/RECORDER
	RenderPanel renderPanel;
	Domain domainObject = new Domain();
	Recorder recorder;
	
	public ChapsChallenge(){
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
	
	/**
	 * Screen for the menu.
	 */
	public void menuScreen() {
		// Panel to stores components
		JPanel panel = new JPanel();
		// JLabel for displaying game title
		var title = createLabel("CHAPS CHALLENGE", SwingConstants.CENTER, LARGE_FONT, 0, (int)(-HEIGHT*0.425), WIDTH, HEIGHT);
		// JButton to start game
		var start = createButton("Start", WIDTH/4, (int)(HEIGHT*0.15), WIDTH/2, HEIGHT/10, SMALL_FONT, e->gameScreen("level1.xml"));
		// JButton to resume saved game from file selector
		var load = createButton("Load Level", WIDTH/4, (int)(HEIGHT*0.275), WIDTH/2, HEIGHT/10, SMALL_FONT, e->loadGame());
		// JButton to resume saved game from file selector
		var loadRecorder = createButton("Load Recorded Game", WIDTH/4, (int)(HEIGHT*0.4), WIDTH/2, HEIGHT/10, SMALL_FONT, e->loadRecordedGame());
		// JButton to view game help info
		var help = createButton("Help", WIDTH/4, (int)(HEIGHT*0.525), WIDTH/2, HEIGHT/10, SMALL_FONT, e->gameHelp());
		// JButton to view controls
		var controls = createButton("Controls", WIDTH/4, (int)(HEIGHT*0.65), WIDTH/2, HEIGHT/10, SMALL_FONT, e->viewControls());
		// JButton to quit game
		var quit = createButton("Quit", WIDTH/4, (int)(HEIGHT*0.775), WIDTH/2, HEIGHT/10, SMALL_FONT, e->System.exit(0));
		// adds components to panel
		panel.setLayout(null);
		addComponents(panel, title, start, load, loadRecorder, help, controls, quit);
		closePhase.run();
		closePhase = ()->{remove(panel);};
		add(panel);
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		pack();
	}
	
	/**
	 * Screen for the game.
	 */
	public void gameScreen(String name) {
		afterMove = ()->{};
		
		// Creates level (sets up domain/renderer/recorder)
		if (!newGame(name)) {return;}
		renderPanel.setBackground(Color.DARK_GRAY);
		
		// Panels to stores components
		JPanel panel = new JPanel();
		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT/12));
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(WIDTH/6, HEIGHT));
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(WIDTH/6, HEIGHT));
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT/14));

		// JLabel to show level player is on
		var levelText = createLabel(levelNameFormat(), SwingConstants.CENTER, LARGE_FONT, 0, -HEIGHT*2/5, WIDTH, HEIGHT);
		// Timer text
		var timerText = createLabel(timerFormat(), SwingConstants.CENTER, SMALL_FONT, 0, -HEIGHT*13/40, WIDTH, HEIGHT);
		// Inventory text
		var inventoryText = createLabel(inventoryFormat(), SwingConstants.CENTER, SMALL_FONT, 0, 0, WIDTH, HEIGHT);
		// JButton to go back to menu
		var back = createButton("Back", WIDTH*3/40, HEIGHT*3/4, WIDTH/5, HEIGHT/10, SMALL_FONT, e->menuScreen());
		// Controller for keys
		panel.addKeyListener(new Controller(this));
		
		timer = new Timer((int)(delay*1000),unused->{
			assert SwingUtilities.isEventDispatchThread();
			// UPDATES DOMAIN/RENDERER/RECORDER
			renderPanel.tick(); // RenderPanel must be ticked first to ensure animations that are finishing can be requeued by domain if desired
			// recorder
			recorder.setPreviousMove(new RecordedMove(currentMove, time));
			if (currentMove!=MoveDirection.NONE) performAction(currentMove.toString());
			//currentMove = MoveDirection.NONE;
			
			// updating timer
			time-=delay;
			levelText.setText(levelNameFormat());
			timerText.setText(timerFormat());
			inventoryText.setText(inventoryFormat());
			
			// repaints gui and renderpanel
			repaint();
			
			// checks if ran out of time
			if (time <=0) {
				time = 0;
				timerText.setText("<html>TIMER:<br/>NO TIME LEFT</html>");
				repaint(); 
				int result = JOptionPane.showConfirmDialog(this,
						"<html>You ran out of time!<br/>Would you like to retry <html>"+level.substring(0,level.length()-4)+"?", 
						"Level Failed!",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if(result == JOptionPane.YES_OPTION){ timer.stop(); gameScreen(level); }
				else if (result == JOptionPane.NO_OPTION){ closePhase.run(); menuScreen(); }
				else { closePhase.run(); menuScreen(); }
			}
		});
		closePhase.run();
		timer.start();
		panel.setLayout(new BorderLayout());
		closePhase = ()->{remove(panel); timer.stop();};
		// adds components to panel
		panel.add(renderPanel, BorderLayout.CENTER);
		topPanel.add(levelText);
		panel.add(topPanel, BorderLayout.NORTH);
		leftPanel.add(timerText);
		panel.add(leftPanel, BorderLayout.WEST);
		rightPanel.add(inventoryText);
		panel.add(rightPanel, BorderLayout.EAST);
		bottomPanel.add(back);
		panel.add(bottomPanel, BorderLayout.SOUTH);
		add(panel);
		setPreferredSize(getSize());
		pack();
		panel.requestFocus();
	}
	
	/**
	 * Loads game from user selected xml file.
	 */
	public void loadGame() {
		// Code borrowed from 
		// https://www.codejava.net/java-se/swing/show-simple-open-file-dialog-using-jfilechooser
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"),"levels/"));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getName());
		    gameScreen(selectedFile.getName());
		}
	}
	
	/**
	 * Loads recorded game from user selected xml file.
	 */
	public void loadRecordedGame() {
		// Code borrowed from 
		// https://www.codejava.net/java-se/swing/show-simple-open-file-dialog-using-jfilechooser
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"),"levels/"));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getName());
		    recordedGame(selectedFile.getName());
		}
	}
	
	/**
	 * Replays a recorded game
	 * 
	 * @param name
	 */
	public void recordedGame(String name) {
		// Creates new recorded game
		if (!newRecordedGame(name)) return;
		
		// Panels to stores components
		JPanel panel = new JPanel();
		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT/12));
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(WIDTH/6, HEIGHT));
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(WIDTH/6, HEIGHT));
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT/14));

		
		// JLabel to show level player is on
		var levelText = createLabel(levelNameFormat(), SwingConstants.CENTER, LARGE_FONT, 0, -HEIGHT*2/5, WIDTH, HEIGHT);
		// Timer text
		var timerText = createLabel(timerFormat(), SwingConstants.CENTER, SMALL_FONT, 0, -HEIGHT*13/40, WIDTH, HEIGHT);
		// Inventory text
		var inventoryText = createLabel(inventoryFormat(), SwingConstants.CENTER, SMALL_FONT, 0, 0, WIDTH, HEIGHT);
		// JButton to go back to menu
		var back = createButton("Back", WIDTH*3/40, HEIGHT*3/4, WIDTH/5, HEIGHT/10, SMALL_FONT, e->menuScreen());
		// Auto replay
		var autoReplayToggle = createButton("Auto Replay: " + (autoReplay?"ON":"OFF"), WIDTH/4, HEIGHT*3/4, WIDTH/5, HEIGHT/10, 
				SMALL_FONT, e->{autoReplay=!autoReplay; stepMove();});
		// Set speed
		var setSpeed = new JSlider(1,10,1);
		// Speed label
		var speedText = createLabel("Speed x" + recorder.getTickSpeed(), SwingConstants.CENTER, SMALL_FONT, 0, 0, WIDTH, HEIGHT);
		// Step move
		var stepMove = createButton("Step Move", WIDTH*3/4, HEIGHT*3/4, WIDTH/5, HEIGHT/10, SMALL_FONT, 
				e->{if (!autoReplay) stepMove();});
				
		renderPanel.setBackground(Color.DARK_GRAY);
		
		timer = new Timer((int)(delay*1000),unused->{
			// UPDATES DOMAIN/RENDERER/RECORDER
			for (int i=0; i<recorder.getTickSpeed(); i++) {
			renderPanel.tick(); // RenderPanel must be ticked first to ensure animations that are finishing can be requeued by domain if desired
			time-=delay;}
			// updating timer
			recorder.setTickSpeed(setSpeed.getValue());
			levelText.setText(levelNameFormat());
			timerText.setText(timerFormat());
			inventoryText.setText(inventoryFormat());
			autoReplayToggle.setText("Auto Replay: " + (autoReplay?"ON":"OFF"));
			speedText.setText("Speed x" + recorder.getTickSpeed());
			
			// repaints gui and renderpanel
			repaint();
			
			// checks if ran out of time
			if (time <=0) {
				time = 0;
				timerText.setText("<html>TIMER:<br/>NO TIME LEFT</html>");
				repaint();
				int result = JOptionPane.showConfirmDialog(this,
						"<html>Player ran out of time!<br/>Would you like to replay <html>"+level.substring(0,level.length()-4)+"?", 
						"Replay Ended",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if(result == JOptionPane.YES_OPTION){ timer.stop(); recordedGame(level); }
				else if (result == JOptionPane.NO_OPTION){ closePhase.run(); menuScreen(); }
				else { closePhase.run(); menuScreen(); }
			}
		});
		closePhase.run();
		timer.start();
		panel.setLayout(new BorderLayout());
		closePhase = ()->{remove(panel); timer.stop();};
		// adds components to panel
		panel.add(renderPanel, BorderLayout.CENTER);
		topPanel.add(levelText);
		panel.add(topPanel, BorderLayout.NORTH);
		leftPanel.add(timerText);
		panel.add(leftPanel, BorderLayout.WEST);
		rightPanel.add(inventoryText);
		panel.add(rightPanel, BorderLayout.EAST);
		addComponents(bottomPanel, back, autoReplayToggle, speedText, setSpeed, stepMove);
		panel.add(bottomPanel, BorderLayout.SOUTH);
		add(panel);
		setPreferredSize(getSize());
		pack();
		panel.requestFocus();
	}
	
	/**
	 * Screen to view game help.
	 */
	public void gameHelp() {
		// Panel to stores components
		JPanel panel = new JPanel();
		// JLabel for displaying help title
		var helpTitle = createLabel("Help", SwingConstants.CENTER, LARGE_FONT, 0, (int)(-HEIGHT*0.4), WIDTH, HEIGHT);
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
				SwingConstants.CENTER, SMALL_FONT, 0, -HEIGHT/20, WIDTH, HEIGHT);
		// JButton to go back to menu
		var back = createButton("Back", (int)(WIDTH*0.075), (int)(HEIGHT*0.75), WIDTH/5, HEIGHT/10, SMALL_FONT, e->menuScreen());
		// adds components to panel
		panel.setLayout(null);
		addComponents(panel, helpTitle, helpInfo, back);
		closePhase.run();
		closePhase = ()->{remove(panel);};
		add(panel);
		setPreferredSize(getSize());
		pack();
	}
	
	/**
	 * Screen to view controls.
	 */
	public void viewControls() {
		// Panel to stores components
		JPanel panel = new JPanel();
		// JLabel for displaying controls title
		var controlsText = createLabel("Controls", SwingConstants.CENTER, LARGE_FONT, 0, (int)(-HEIGHT*0.4), WIDTH, HEIGHT);
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
		controlsInfo.setFont(SMALL_FONT);
		controlsInfo.setBounds(WIDTH/5, -HEIGHT/20, WIDTH, HEIGHT);
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
		controlsKeys.setFont(SMALL_FONT);
		controlsKeys.setBounds(-WIDTH/5, -HEIGHT/20, WIDTH, HEIGHT);
		// JButton to go back to menu
		var back = createButton("Back", (int)(WIDTH*0.075), (int)(HEIGHT*0.75), WIDTH/5, HEIGHT/10, SMALL_FONT, e->menuScreen());
		// adds components to panel
		panel.setLayout(null);
		addComponents(panel, controlsText, controlsInfo, controlsKeys, back);
		closePhase.run();
		closePhase = ()->{remove(panel);};
		add(panel);
		setPreferredSize(getSize());
		pack();
	}
	
	/**
	 * Takes input and performs the appropriate action.
	 * Used for moving player, loading, saving, exiting game.
	 * It is also used for fuzz testing.
	 * 
	 * @param input user input
	 */
	public void performAction(String input) {
		if (input.equals("CTRL-X")) { menuScreen(); }
		else if (input.equals("CTRL-S")) { saveAndExit();  }
		else if (input.equals("CTRL-R")) { loadGame(); }
		else if (input.equals("CTRL-1")) { timer.stop(); gameScreen("level1.xml"); }
		else if (input.equals("CTRL-2")) { timer.stop(); gameScreen("level2.xml"); }
		else if (input.equals("SPACE")) { pause(true); }
		else if (input.equals("ESC")) { pause(false); }
		else if (input.equals("UP")) { domainObject.level().model().player().movePlayer(Direction.UP, domainObject.level().model(), afterMove); }
		else if (input.equals("DOWN")) { domainObject.level().model().player().movePlayer(Direction.DOWN, domainObject.level().model(), afterMove); }
		else if (input.equals("LEFT")) { domainObject.level().model().player().movePlayer(Direction.LEFT, domainObject.level().model(), afterMove); }
		else if (input.equals("RIGHT")) { domainObject.level().model().player().movePlayer(Direction.RIGHT, domainObject.level().model(), afterMove); }
		System.out.println(input + ", Player pos: " + domainObject.level().model().player().location().x() + " "
				+ domainObject.level().model().player().location().y());
	}
	
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
	private JButton createButton(String name, int x, int y, int w, int h, Font f, ActionListener l) {
		JButton b = new JButton(name);
		b.setBounds(x,y,w,h);
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
	private JLabel createLabel(String name, int i, Font f, int x, int y, int w, int h) {
		JLabel l = new JLabel(name, i);
		l.setBounds(x,y,w,h);
		l.setFont(f);
		return l;
	}
	
	/**
	 * Adds a list of components to a JPanel.
	 * 
	 * @param p panel to have components added
	 * @param cs components to be added
	 */
	private void addComponents(JPanel p, Component...cs) {
		for (Component c :cs) { p.add(c); }
	}
	
	/**
	 * Creates a new game.
	 * 
	 * @param name level name
	 * @return whether it was successful or not
	 */
	public boolean newGame(String name) {
		// resets timer
		time = 60;
		recorder = new Recorder(this, name);
		currentMove = MoveDirection.NONE;
		
		// DOMAIN/RENDERER/RECORDER
		domainObject.setLevel(name);
		if (domainObject.level() == null) return false;
		renderPanel = new RenderPanel(); // RenderPanel extends JPanel
		renderPanel.bind(domainObject.level().model());  // this can be done at any time allowing dynamic level switching
		level = name;
		return true;
	}
	
	/**
	 * Loads a recorded game.
	 * 
	 * @param name name of recorded file
	 * @return whether it was successful or not
	 */
	public boolean newRecordedGame(String name) {
		// resets timer and recorder
		time = 60;
		recorder = new Recorder(this, name);
		recorder.setTickSpeed(1);
		autoReplay = false;
		
		// DOMAIN/RENDERER/RECORDER
		// Load recorder for xml
		try { recorder.loadRecording("levels/", name); } 
		catch (DocumentException e) { e.printStackTrace(); menuScreen(); return false;} 
		catch (IOException e) { e.printStackTrace(); menuScreen(); return false;}
		String recorderName = recorder.getRecordingLevelName();
		
		// Load level from xml
		domainObject.setLevel(recorderName);
		if (domainObject.level() == null) return false;
		
		renderPanel = new RenderPanel(); // RenderPanel extends JPanel
		renderPanel.bind(domainObject.level().model());  // this can be done at any time allowing dynamic level switching
		level = name;
		return true;
	}
	
	/**
	 * Pauses/unpauses game which stops timer.
	 * 
	 * @param p boolean which decides whether to pause or unpause
	 */
	public void pause(Boolean p) {
		if (p) { timer.stop(); } 
		else { timer.start(); }
		System.out.println(p?"Paused":"Unpaused");
	}
	
	/**
	 * Saves game to xml and exits
	 */
	public void saveAndExit() {
		pause(true);
		String levelName = (String)JOptionPane.showInputDialog("Set Level Name: ", level.substring(0,level.length()-4));
		if(levelName == null || (levelName != null && (levelName.equals("")))) {
		    System.out.println("Cancelled save");
		    menuScreen();
		    return;
		}
		
		// DOMAIN/PERSISTENCY/RECORDER?
		try { new Persistency().saveXML("levels/", levelName + ".xml", domainObject.level()); } 
		catch (ParserException e1) { e1.printStackTrace(); } 
		catch (IOException e1) { e1.printStackTrace(); } 
		catch (DocumentException e1) { e1.printStackTrace(); }
		
		// save recording
		
		// return to menu
		menuScreen();
	}
	
	/**
	 * Formats level name to be displayed
	 * 
	 * @return formatted level name
	 */
	private String levelNameFormat() {
		if (level.equals("level1.xml")) { return "Level: 1"; } 
		else if (level.equals("level2.xml")) { return "Level: 2"; }
		else { return"Level: " + level.substring(0,level.length()-4); }
	}
	
	/**
	 * Formats timer to display
	 * 
	 * @return timer string to display
	 */
	private String timerFormat() {
		return "<html>TIMER:<br/>" + (float)Math.round(time*10)/10 + "s</html>";
	}
	
	private String inventoryFormat() {
		return "<html>INVENTORY:<br/><br/>Keys <br/>" + domainObject.level().model().player().keys().size() 
				+ "<br/><br/>Treasure<br/>remaining <br/>" + domainObject.level().model().treasure().size();
	}
	
	/**
	 * Steps move in recorded game.
	 * Public if fuzz testing requires it.
	 */
	public void stepMove() {
		if (autoReplay) {afterMove = ()->stepMove();}
		else { afterMove = ()->timer.stop();}
		timer.start();
		recorder.stepMove();
	}
}
