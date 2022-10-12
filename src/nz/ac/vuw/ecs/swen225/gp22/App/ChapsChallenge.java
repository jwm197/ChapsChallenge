package nz.ac.vuw.ecs.swen225.gp22.App;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.*;
import nz.ac.vuw.ecs.swen225.gp22.Domain.*;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.*;
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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
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
	public static final Font MASSIVE_FONT = new Font("Trebuchet MS", Font.PLAIN, 54);
	public static final double delay = 1.0/60.0;

	// Private variables
	private static final long serialVersionUID = 1L;
	private Runnable closePhase = ()->{};
	private String level = "level1.xml";
	private float time;
	private BetterTimer timer;
	private boolean autoReplay;
	private boolean wait;
	
	// DOMAIN/RENDERER/RECORDER/PERSISTENCY
	private RenderPanel renderPanel;
	private Level domainLevel;
	private Recorder recorder;
	private MoveDirection currentMove;
	private Persistency persistency = new Persistency();
	
	// Sound
	private final AudioMixer soundMixer = new AudioMixer();
	private final AudioMixer musicMixer = new AudioMixer();
	
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
			public void windowClosed(WindowEvent e) { 
				closePhase.run(); 
				closeTheSounds();
			}
		});
	}			
	
	/**
	 * Screen for the menu.
	 */
	public void menuScreen() {
		closeTheSounds();
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
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		pack();
	}
	
	/**
	 * Screen for the game.
	 */
	public void gameScreen(String name) {		
		// Creates level (sets up domain/renderer/recorder)
		if (!newGame(name)) {return;}
		prepareMusic();
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
		
		timer = new BetterTimer((int)(delay*1000), ()->{
			// UPDATES DOMAIN/RENDERER/RECORDER
			// recorder
			if (currentMove!=MoveDirection.NONE) {
				performAction(currentMove.toString());
				if (animating() && !wait) {
					recorder.setPreviousMove(new RecordedMove(currentMove, time));
					wait = true;
					System.out.println(currentMove + " " + time);
				}
			}
			renderPanel.tick(); // RenderPanel must be ticked first to ensure animations that are finishing can be requeued by domain if desired
			if (wait && !animating()) wait = false;
			domainLevel.model().tick(); 
			domainLevel.model().setTime(time);
			
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
				timer.stop();
				gameEnd(false);
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
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"), "levels/"));
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
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"), "levels/"));
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
		prepareMusic();
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
		// Auto replay
		var autoReplayToggle = createButton("Auto Replay: " + (autoReplay?"ON":"OFF"), WIDTH/4, HEIGHT*3/4, WIDTH/5, HEIGHT/10, 
				SMALL_FONT, e->{});
		autoReplayToggle.addActionListener(e->{ 
			autoReplay=!autoReplay; if (autoReplay) {timer.start();} else {timer.stop();}
			autoReplayToggle.setText("Auto Replay: " + (autoReplay?"ON":"OFF")); repaint();});
		// Set speed
		var setSpeed = new JSlider(1, 10, 1);
		// Speed label
		var speedText = createLabel("Speed x" + recorder.getTickSpeed(), SwingConstants.CENTER, SMALL_FONT, 0, 0, WIDTH, HEIGHT);
		// Step move
		var stepMove = createButton("Step Move", WIDTH*3/4, HEIGHT*3/4, WIDTH/5, HEIGHT/10, SMALL_FONT, 
				e->{if (!autoReplay) stepMove();});
				
		renderPanel.setBackground(Color.DARK_GRAY);
		
		timer = new BetterTimer((int)(delay*1000), ()->{
			// UPDATES DOMAIN/RENDERER/RECORDER
			for (int i=0; i<recorder.getTickSpeed(); i++) {
			renderPanel.tick(); // RenderPanel must be ticked first to ensure animations that are finishing can be requeued by domain if desired
			domainLevel.model().tick(); 
			time-=delay;}
			// updating timer
			recorder.setTickSpeed(setSpeed.getValue());
			levelText.setText(levelNameFormat());
			timerText.setText(timerFormat());
			inventoryText.setText(inventoryFormat());
			autoReplayToggle.setText("Auto Replay: " + (autoReplay?"ON":"OFF"));
			speedText.setText("Speed x" + recorder.getTickSpeed());
			if (autoReplay) {
				if (recorder.peekNextMove()!=null) {
					if (time<=recorder.peekNextMove().time()) {
						stepMove();
					}
				} else if (!domainLevel.model().player().locked()) {
					timer.stop();
				}
			} else if (!domainLevel.model().player().locked()) {
				timer.stop();
			}
			
			// repaints gui and renderpanel
			repaint();
			
			// checks if ran out of time
			if (time <=0) {
				time = 0;
				timerText.setText("<html>TIMER:<br/>NO TIME LEFT</html>");
				repaint();
				int result = JOptionPane.showConfirmDialog(this,
						"<html>Player ran out of time!<br/>Would you like to replay <html>"+level.substring(0, level.length()-4)+"?", 
						"Replay Ended",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if(result == JOptionPane.YES_OPTION){ timer.stop(); recordedGame(level); }
				else { closePhase.run(); menuScreen(); }
			}
		});
		closePhase.run();
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
	 * Screen for when player has completed/failed the level
	 */
	public void gameEnd(boolean completed) {
		closeTheSounds();
		// Goes to level 2 once level 1 completed
		if (level.equals("level1.xml") && completed) {
			int result = JOptionPane.showConfirmDialog(this,
					"<html>Level 1 complete with: " + (float)Math.round(time*10)/10 + "s left!" + "<br/>Would you like to save recording?<html>", 
					"Level 1 Completed!",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.YES_OPTION){ saveRecording(); }
			timer.stop();
			gameScreen("level2.xml");
			return;
		}
		// Panel to stores components
		JPanel panel = new JPanel();
		JPanel bottomPanel = new JPanel();
		// JLabel for displaying help title
		var endTitle = createLabel(completed?"LEVEL COMPLETE!":"LEVEL FAILED!", SwingConstants.CENTER, LARGE_FONT, 0, (int)(-HEIGHT*0.4), WIDTH, HEIGHT);
		// JLabel to display help info
		var endInfo = createLabel(completed?"You completed the level with: " + (float)Math.round(time*10)/10 + "s left!"
				:"You failed the level by: " + (time==0?"No time left!":"Eaten by bug!"), 
				SwingConstants.CENTER, MASSIVE_FONT, 0, -HEIGHT/20, WIDTH, HEIGHT);
		// JButton to go back to menu
		var back = createButton("Back", (int)(WIDTH*0.075), (int)(HEIGHT*0.75), WIDTH/5, HEIGHT/10, SMALL_FONT, e->menuScreen());
		var retry = createButton("Retry", (int)(WIDTH*0.35), (int)(HEIGHT*0.75), WIDTH/5, HEIGHT/10, SMALL_FONT, e->{timer.stop(); gameScreen(level);});
		var saveRecording = createButton("Save Recording", (int)(WIDTH*0.5), (int)(HEIGHT*0.75), WIDTH/5, HEIGHT/10, SMALL_FONT, e->saveRecording());
		
		// adds components to panel
		panel.setLayout(new BorderLayout());
		panel.add(endTitle);
		panel.add(endInfo);
		addComponents(bottomPanel, back, retry, saveRecording);
		panel.add(bottomPanel, BorderLayout.SOUTH);
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
		else if (input.equals("CTRL-R")) { timer.stop(); loadGame(); }
		else if (input.equals("CTRL-1")) { if (timer!=null) {timer.stop();} gameScreen("level1.xml"); }
		else if (input.equals("CTRL-2")) { if (timer!=null) {timer.stop();} gameScreen("level2.xml"); }
		else if (input.equals("SPACE")) { timer.stop(); pauseTheSounds(); }
		else if (input.equals("ESC")) { timer.start(); resumeTheSounds(); }
		if (timer.isRunning()) {
			if (input.equals("UP")) { domainLevel.model().player().move(Direction.UP, domainLevel.model());}
			else if (input.equals("DOWN")) { domainLevel.model().player().move(Direction.DOWN, domainLevel.model());}
			else if (input.equals("LEFT")) { domainLevel.model().player().move(Direction.LEFT, domainLevel.model());}
			else if (input.equals("RIGHT")) { domainLevel.model().player().move(Direction.RIGHT, domainLevel.model()); }
		}
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
	private JLabel createLabel(String name, int i, Font f, int x, int y, int w, int h) {
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
		recorder = new Recorder(this, name);
		currentMove = MoveDirection.NONE;
		wait = false;
		
		// DOMAIN/RENDERER/RECORDER
        try{ domainLevel = persistency.loadXML("levels/", name, this); } 
        catch(Exception e){ e.printStackTrace(); return false;}
        
		time = domainLevel.model().time();
		renderPanel = new RenderPanel(); // RenderPanel extends JPanel
		domainLevel.model().bindMixer(soundMixer); //bind the global mixer object to the level so Domain can use audio
		renderPanel.bind(domainLevel.model());  // this can be done at any time allowing dynamic level switching
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
		try{ domainLevel = persistency.loadXML("levels/", recorderName, this); } 
        catch(Exception e){ e.printStackTrace(); return false;}
		
		time = domainLevel.model().time();
		renderPanel = new RenderPanel(); // RenderPanel extends JPanel
		domainLevel.model().bindMixer(soundMixer); //bind the global mixer object to the level so Domain can use audio
		renderPanel.bind(domainLevel.model());  // this can be done at any time allowing dynamic level switching
		level = name;
		return true;
	}
	
	/**
	 * Saves game to xml and exits
	 */
	public void saveAndExit() {
		timer.stop();
		String levelName = (String)JOptionPane.showInputDialog("Set Level Name: ", level.substring(0, level.length()-4) + "_save");
		if(levelName == null || (levelName != null && (levelName.equals("")))) {
		    System.out.println("Cancelled save");
		    menuScreen();
		    return;
		}
		
		// saves level state
		domainLevel.model().setTime(time);
		try { persistency.saveXML("levels/", level, "levels/", levelName + ".xml", domainLevel); } 
		catch (ParserException e1) { e1.printStackTrace(); } 
		catch (IOException e1) { e1.printStackTrace(); } 
		catch (DocumentException e1) { e1.printStackTrace(); }
		
		saveRecording();
		
		// return to menu
		menuScreen();
	}
	
	/**
	 * Saves recorded game to xml and exits
	 */
	public void saveRecording() {
		String levelName = (String)JOptionPane.showInputDialog("Set Recording Name: ", level.substring(0, level.length()-4) + "_recording");
		if(levelName == null || (levelName != null && (levelName.equals("")))) {
		    System.out.println("Cancelled save");
		    menuScreen();
		    return;
		}
		
		// save recording
		try { recorder.saveRecording("levels/", levelName + ".xml"); } 
		catch (DocumentException e) { e.printStackTrace(); } 
		catch (IOException e) { e.printStackTrace(); }
	}
	
	/**
	 * Formats level name to be displayed
	 * 
	 * @return formatted level name
	 */
	private String levelNameFormat() {
		if (level.equals("level1.xml")) { return "Level: 1"; } 
		else if (level.equals("level2.xml")) { return "Level: 2"; }
		else { return"Level: " + level.substring(0, level.length()-4); }
	}
	
	/**
	 * Formats timer to display
	 * 
	 * @return timer string to display
	 */
	private String timerFormat() {
		return "<html>TIMER:<br/>" + (float)Math.round(time*10)/10 + "s</html>";
	}
	
	/**
	 * Formats inventory information
	 * 
	 * @return inventory string to display
	 */
	private String inventoryFormat() {
		return "<html>INVENTORY:<br/><br/>Keys <br/>" + domainLevel.model().player().keys().size() 
				+ "<br/><br/>Treasure<br/>remaining <br/>" + domainLevel.model().treasure().size();
	}
	
	/**
	 * Steps move in recorded game.
	 * Public if fuzz testing requires it.
	 */
	public void stepMove() {
		if (!autoReplay) {
			if (timer.isRunning()) return;
		}
		if (!autoReplay && recorder.peekNextMove()!=null) {
			while (recorder.peekNextMove().direction()==MoveDirection.NONE) {
				System.out.println("SKIPPED NONE");
				recorder.stepMove();
			}
		}
		if (recorder.peekNextMove()==null) {
			System.out.println("NO MORE MOVES TO STEP");
			return;
		}
		timer.start();
		time = recorder.peekNextMove().time();
		System.out.println(recorder.peekNextMove().direction());
		recorder.stepMove();
	}
	
	/**
	 * Pauses the sounds
	 */
	public void pauseTheSounds() {
	    soundMixer.pauseAll();
	    musicMixer.pauseAll();
	}
	
	/**
	 * Resumes the sounds
	 */
	public void resumeTheSounds() {
		soundMixer.playAll();
		musicMixer.playAll();
	}
	
	/**
	 * Closes the sounds
	 */
	public void closeTheSounds() {
		soundMixer.closeAll();
		musicMixer.closeAll();
	}
	
	/**
	 * Prepares game music
	 */
	public void prepareMusic() {
		closeTheSounds();
//		Playable music = SoundLines.GAME.generate();
//		music.setVolume(40);
//		music.setLooping(true);
//		musicMixer.add(music);
	}
	
	/**
	 * Sets current move
	 * 
	 * @param m current move to be set
	 */
	public void setCurrentMove(MoveDirection m) {
		currentMove = m;
	}
	
	/**
	 * Gets current move
	 * 
	 * @return current move
	 */
	public MoveDirection getCurrentMove() {
		return currentMove;
	}
	
	// FUZZ TESTING ---------------------------------------------------------------------------------------------------
	
	/**
	 * Returns if the player is moving.
	 * For fuzz testing.
	 * 
	 * @return true if player is moving, false otherwise (e.g. if false then next move can be executed)
	 */
	public boolean animating() {
		return domainLevel.model().player().locked();
	}
	
	/**
	 * Gets the keys as a list of colors for fuzz.
	 * Fuzz cannot access key objects so needs to be colors as rgb ints.
	 * 
	 * @return the list of keys as colors
	 */
	public List<Integer> getPlayerKeys() {
		return domainLevel.model().player().keys().stream().map(k->k.color().getRGB()).collect(Collectors.toList());
	}
	
	/**
	 * Gets the player position as an int[][] for fuzz.
	 * 
	 * @return [0][0] is x, [0][1] is y position
	 */
	public int[][] getPlayerPosition() {
		IntPoint playerLoc = domainLevel.model().player().location();
		int[][] pos = new int[1][2];
		pos[0][0] = playerLoc.x();
		pos[0][1] = playerLoc.y();
		return pos;
	}
	
	/**
	 * Gets position of bugs for fuzz.
	 * 
	 * @return bug positions, [bugNum][0] is x, [bugNum][1] is y position
	 */
	public int[][] getBugPositions() {
		Collection<Entity> entities = domainLevel.model().entities().values();
		int[][] locations = new int[entities.size()][2];
		int count = 0;
		for (Entity e : entities) {
			if (e instanceof Bug b) {
				locations[count][0] = b.location().x();
				locations[count][1] = b.location().y();
				count++;
			}
		}
		return locations;
	}
	
	/**
	 * Gets number of treasure left for fuzz.
	 * 
	 * @return treasure left as int
	 */
	public int treasureLeft() {
		return domainLevel.model().treasure().size();
	}
	
	/**
	 * Gets keys for fuzz, x position, y position, and color as rgb int.
	 * 
	 * @return int[][] of keys, [keyNum][0] is x, [keyNum][1] is y, [keyNum][2] is rgb int
	 */
	public int[][] getKeys() {
		// number of keys
		int count = 0;
		for (List<Tile> tiles : domainLevel.model().tiles().tiles()) {
			count += tiles.stream().filter(t->t instanceof FreeTile f && f.item() instanceof Key).count();
		}
		
		// store information
		int[][] keys = new int[count][3];
		count = 0;
		for (List<Tile> tilesOfTiles : domainLevel.model().tiles().tiles()) {
			for (Tile tile : tilesOfTiles) {
				if (tile instanceof FreeTile f) {
					if (f.item() instanceof Key k) {
						keys[count][0] = f.location().x();
						keys[count][1] = f.location().y();
						keys[count][2] = k.color().getRGB();
						count++;
					}
				}
			}
		}
		return keys;
	}
	
	/**
	 * Gets treasure for fuzz, x position, y position.
	 * 
	 * @return int[][] of treasure, [treasureNum][0] is x, [treasureNum][1] is y
	 */
	public int[][] getTreasure() {
		// number of treasure
		int count = 0;
		for (List<Tile> tiles : domainLevel.model().tiles().tiles()) {
			count += tiles.stream().filter(t->t instanceof FreeTile f && f.item() instanceof Treasure).count();
		}
		
		// store information
		int[][] treasure = new int[count][3];
		count = 0;
		for (List<Tile> tilesOfTiles : domainLevel.model().tiles().tiles()) {
			for (Tile tile : tilesOfTiles) {
				if (tile instanceof FreeTile f) {
					if (f.item() instanceof Treasure t) {
						treasure[count][0] = f.location().x();
						treasure[count][1] = f.location().y();
						count++;
					}
				}
			}
		}
		return treasure;
	}
	
	/**
	 * Gets lockedDoors for fuzz, x position, y position, and color as rgb int.
	 * 
	 * @return int[][] of lockedDoors, [doorNum][0] is x, [doorNum][1] is y, [doorNum][2] is rgb int
	 */
	public int[][] getLockedDoors() {
		// number of locked doors
		int count = 0;
		for (List<Tile> tiles : domainLevel.model().tiles().tiles()) {
			count += tiles.stream().filter(t->t instanceof LockedDoor).count();
		}
		
		// store information
		int[][] lockedDoors = new int[count][3];
		count = 0;
		for (List<Tile> tilesOfTiles : domainLevel.model().tiles().tiles()) {
			for (Tile tile : tilesOfTiles) {
				if (tile instanceof LockedDoor l) {
					lockedDoors[count][0] = l.location().x();
					lockedDoors[count][1] = l.location().y();
					lockedDoors[count][2] = l.color().getRGB();
					count++;
				}
			}
		}
		return lockedDoors;
	}
	
	/**
	 * Returns exit lock position as an int[][] for fuzz.
	 * 
	 * @return [0][0] is x, [0][1] is y position
	 */
	public int[][] getExitLockPosition(){
		for (List<Tile> tilesOfTiles : domainLevel.model().tiles().tiles()) {
			for (Tile tile : tilesOfTiles) {
				if (tile instanceof ExitLock l) {
					int[][] pos = new int[1][2];
					pos[0][0] = l.location().x();
					pos[0][1] = l.location().y();
					return pos;
				}
			}
		}
		return null;
	}
	
	/**
	 * Checks if player can move to tile position for fuzz.
	 * 
	 * @param x x coordinate to move to
	 * @param y y coordinate to move to
	 * @return if player can move to that tile
	 */
	public boolean canMoveTo(int x, int y) {
		return domainLevel.model().tiles().getTile(new IntPoint(x, y)).canPlayerMoveTo(domainLevel.model());
	}
}
