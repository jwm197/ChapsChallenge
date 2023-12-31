package nz.ac.vuw.ecs.swen225.gp22.App;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.*;
import nz.ac.vuw.ecs.swen225.gp22.Domain.*;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.*;
import nz.ac.vuw.ecs.swen225.gp22.Persistency.*;
import nz.ac.vuw.ecs.swen225.gp22.Recorder.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
 * @author pratapshek 300565138
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
	private boolean replay;
	
	// DOMAIN/RENDERER/RECORDER/PERSISTENCY/FUZZ
	private RenderPanel renderPanel;
	private Level domainLevel;
	private Recorder recorder;
	private MoveDirection currentMove;
	private Persistency persistency = new Persistency();
	private Fuzzer fuzzer = Fuzzer.NONE;

	// Sound
	private final AudioMixer soundMixer = new AudioMixer();
	private final AudioMixer musicMixer = new AudioMixer();
	
	/**
	 * Constructs a new chaps challenge
	 */
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
		JPanel panel = new MenuScreen(this).make();
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
		replay = false;
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
		var levelText = JHelper.createLabel(levelNameFormat(), SwingConstants.CENTER, LARGE_FONT, 0, -HEIGHT*2/5, WIDTH, HEIGHT);
		// Left text
		var leftText = JHelper.createLabel(leftTextFormat(), SwingConstants.CENTER, SMALL_FONT, 0, -HEIGHT*13/40, WIDTH, HEIGHT);
		// Right text
		var rightText = JHelper.createLabel(rightTextFormat(), SwingConstants.CENTER, SMALL_FONT, 0, 0, WIDTH, HEIGHT);
		// Bottom text
		var bottomText = JHelper.createLabel("", SwingConstants.CENTER, SMALL_FONT, 0, 0, WIDTH, HEIGHT);
		// Controller for keys
		panel.addKeyListener(new Controller(this));
		
		timer = new BetterTimer((int)(delay*1000), ()->{
			// UPDATES DOMAIN/RENDERER/RECORDER/FUZZ
			fuzzer.nextMove();
			// recorder
			if (currentMove!=MoveDirection.NONE) {
				performAction(currentMove.toString());
				if (animating() && !wait) {
					recorder.setPreviousPlayerMove(new RecordedMove(currentMove, time));
					wait = true;
					System.out.println("<move time=\"" + time + "\">" + currentMove + "</move>");
				}
			}
			
			HashMap<Integer, MoveDirection> moves = new HashMap<>();
			for (Map.Entry<Integer, Entity> entry : domainLevel.model().entities().entrySet()) {
				if (entry.getValue() instanceof Bug b) {
					int id = entry.getKey();
					moves.put(id, MoveDirection.valueOf(b.direction().toString()));
				}
			}
			recorder.setPreviousBugMove(new BugsMove(time, moves));
			
			renderPanel.tick(); // RenderPanel must be ticked first to ensure animations that are finishing can be requeued by domain if desired
			if (wait && !animating()) wait = false;
			domainLevel.model().tick(); 
			domainLevel.model().setTime(time);
			
			// updating timer
			time-=delay;
			levelText.setText(levelNameFormat());
			leftText.setText(leftTextFormat());
			rightText.setText(rightTextFormat());
			
			// display info field text
			bottomText.setText("");
			if (domainLevel.model().tiles().getTile(domainLevel.model().player().location()) instanceof InfoField i) {
				bottomText.setText(i.info());
			}
			
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
		leftPanel.add(leftText);
		panel.add(leftPanel, BorderLayout.WEST);
		rightPanel.add(rightText);
		panel.add(rightPanel, BorderLayout.EAST);
		bottomPanel.add(bottomText);
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
		replay = true;
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
		var levelText = JHelper.createLabel(levelNameFormat(), SwingConstants.CENTER, LARGE_FONT, 0, -HEIGHT*2/5, WIDTH, HEIGHT);
		// Left text
		var leftText = JHelper.createLabel(leftTextFormat(), SwingConstants.CENTER, SMALL_FONT, 0, -HEIGHT*13/40, WIDTH, HEIGHT);
		// Right text
		var rightText = JHelper.createLabel(rightTextFormat(), SwingConstants.CENTER, SMALL_FONT, 0, 0, WIDTH, HEIGHT);
		// JButton to go back to menu
		var back = JHelper.createButton("Back", WIDTH*3/40, HEIGHT*3/4, WIDTH/5, HEIGHT/10, SMALL_FONT, e->menuScreen());
		// Auto replay
		var autoReplayToggle = JHelper.createButton("Auto Replay: " + (autoReplay?"ON":"OFF"), WIDTH/4, HEIGHT*3/4, WIDTH/5, HEIGHT/10, 
				SMALL_FONT, e->{});
		autoReplayToggle.addActionListener(e->{ 
			autoReplay=!autoReplay; 
			if (autoReplay) {timer.start();} 
			else {
				while (domainLevel.model().player().locked() && !domainLevel.model().player().isDead()){
					for (int i=0; i<recorder.getTickSpeed(); i++) {
						renderPanel.tick(); // RenderPanel must be ticked first to ensure animations that are finishing can be requeued by domain if desired
						time-=delay;
					}
				} 
			}
			autoReplayToggle.setText("Auto Replay: " + (autoReplay?"ON":"OFF")); repaint();});
		// Set speed
		var setSpeed = new JSlider(1, 5, 1);
		// Speed label
		var speedText = JHelper.createLabel("Speed x" + recorder.getTickSpeed(), SwingConstants.CENTER, SMALL_FONT, 0, 0, WIDTH, HEIGHT);
		// Step move
		var stepMove = JHelper.createButton("Step Move", WIDTH*3/4, HEIGHT*3/4, WIDTH/5, HEIGHT/10, SMALL_FONT, 
				e->{if (!autoReplay) {stepMove();} else {System.out.println("CANNOT STEP WHILE AUTO REPLAY IS ON");}});
				
		renderPanel.setBackground(Color.DARK_GRAY);
		
		timer = new BetterTimer((int)(delay*1000), ()->{
			// UPDATES DOMAIN/RENDERER/RECORDER
			for (int i=0; i<recorder.getTickSpeed(); i++) {				
				renderPanel.tick(); // RenderPanel must be ticked first to ensure animations that are finishing can be requeued by domain if desired
				time-=delay;
				for (Map.Entry<Integer, Entity> e : domainLevel.model().entities().entrySet()) {
					if (e.getValue() instanceof Bug b) {
						if (time<=recorder.peekNextBugMove().getTime()) {
							recorder.stepMoveBugs(this);
						}
					}
				}
			}
			
			// updating timer
			recorder.setTickSpeed(setSpeed.getValue());
			levelText.setText(levelNameFormat());
			leftText.setText(leftTextFormat());
			rightText.setText(rightTextFormat());
			autoReplayToggle.setText("Auto Replay: " + (autoReplay?"ON":"OFF"));
			speedText.setText("Speed x" + recorder.getTickSpeed());
			// Player moves
			if (autoReplay) {
				if (recorder.peekNextPlayerMove()!=null) {
					if (domainLevel.model().player().isDead()) replayRecording();
					if (time<=recorder.peekNextPlayerMove().time() && !domainLevel.model().player().locked()) {
						stepMove();
					}
				} else if (!domainLevel.model().player().locked()) {
					replayRecording();
				}
			} else {		
				if (!domainLevel.model().player().locked()) { timer.stop(); } 
				else if (recorder.peekNextPlayerMove() == null) { replayRecording(); }
			}
			
			// repaints gui and renderpanel
			repaint();
			
			// checks if ran out of time
			if (time <=0) {
				time = 0;
				leftText.setText("<html>TIMER:<br/>"
						+ "NO TIME LEFT</html>"
						+ "<br/><br/>TREASURE<br/>REMAINING:<br/>" 
						+ domainLevel.model().treasure().size() + "</html>");
				repaint();
				replayRecording();
			}
		});
		closePhase.run();
		panel.setLayout(new BorderLayout());
		closePhase = ()->{remove(panel); timer.stop();};
		// adds components to panel
		panel.add(renderPanel, BorderLayout.CENTER);
		topPanel.add(levelText);
		panel.add(topPanel, BorderLayout.NORTH);
		leftPanel.add(leftText);
		panel.add(leftPanel, BorderLayout.WEST);
		rightPanel.add(rightText);
		panel.add(rightPanel, BorderLayout.EAST);
		JHelper.addComponents(bottomPanel, back, autoReplayToggle, speedText, setSpeed, stepMove);
		panel.add(bottomPanel, BorderLayout.SOUTH);
		add(panel);
		setPreferredSize(getSize());
		pack();
		panel.requestFocus();
	}

	/**
	 * Listener for when autoreplay is toggled
	 *  
	 * @param l auto replay button
	 */
	public void autoReplayListener(JButton l) {
		autoReplay=!autoReplay; 
		if (autoReplay) {timer.start();} 
		else {
			while (domainLevel.model().player().locked()){
				for (int i=0; i<recorder.getTickSpeed(); i++) {
					renderPanel.tick(); // RenderPanel must be ticked first to ensure animations that are finishing can be requeued by domain if desired
					time-=delay;
				}
			} 
			timer.stop();
		}
		l.setText("Auto Replay: " + (autoReplay?"ON":"OFF")); repaint();
	}
	
	/**
	 * Listener for when step move is clicked
	 */
	public void stepMoveListener() {
		if (!autoReplay) {stepMove();}
		else {System.out.println("CANNOT STEP WHILE AUTO REPLAY IS ON");}
	}
	
	/**
	 * Gives the player option of replaying a recording
	 */
	public void replayRecording() {
		int result = JOptionPane.showConfirmDialog(this,
				"<html>Recording ended!<br/>Would you like to replay <html>"+level.substring(0, level.length()-4)+"?", 
				"Replay Ended",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if(result == JOptionPane.YES_OPTION){ timer.stop(); recordedGame(level); }
		else { closePhase.run(); menuScreen(); }
	}
	
	/**
	 * Screen to view game help.
	 */
	public void gameHelp() {
		// Panel to stores components
		JPanel panel = new GameHelpScreen(this).make();
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
		JPanel panel = new ViewControlsScreen(this).make();
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
		fuzzer.end();
		if (fuzzer!=Fuzzer.NONE) return;
		if (replay) return;
		closeTheSounds();
		if (completed) { soundMixer.add(SoundClips.PlayerWin.generate()); } 
		else { soundMixer.add(SoundClips.PlayerLose.generate()); }
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
		JPanel panel = new GameEndScreen(this, completed, time).make();
		closePhase.run();
		closePhase = ()->{remove(panel);};
		add(panel);
		setPreferredSize(getSize());
		pack();
	}
	
	public void retry() {
		timer.stop(); 
		gameScreen(level);
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
		if (timer.isRunning() && (input.equals("UP") || input.equals("DOWN") || input.equals("LEFT") || input.equals("RIGHT"))) {
			domainLevel.model().player().move(Direction.valueOf(input), domainLevel.model());
		}
	}
	
	/**
	 * Creates a new game.
	 * 
	 * @param name level name
	 * @return whether it was successful or not
	 */
	public boolean newGame(String name) {
		// resets timer
		recorder = new Recorder(name);
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
		recorder = new Recorder(name);
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
		try { persistency.saveLevel("levels/", level, "levels/", levelName + ".xml", domainLevel); } 
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
	public String levelNameFormat() {
		if (level.equals("level1.xml")) { return "Level: 1"; } 
		else if (level.equals("level2.xml")) { return "Level: 2"; }
		else { return"Level: " + level.substring(0, level.length()-4); }
	}
	
	/**
	 * Formats timer to display and treasure remaining
	 * 
	 * @return string to display on left panel
	 */
	public String leftTextFormat() {
		return "<html>TIME LEFT:<br/>" 
				+ (float)Math.round(time*10)/10 + "s"
				+ "<br/><br/>TREASURE<br/>REMAINING:<br/>" 
				+ domainLevel.model().treasure().size() + "</html>";
	}
	
	/**
	 * Formats inventory information with keys
	 * 
	 * @return string to display on right panel
	 */
	public String rightTextFormat() {
		return "<html>KEYS<br/>COLLECTED:<br/><br/>" 
				+ "Red - " + domainLevel.model().player().keys().stream().filter(k->k.color().equals(Color.RED)).count() + "<br/>" 
				+ "Yellow - " + domainLevel.model().player().keys().stream().filter(k->k.color().equals(Color.YELLOW)).count() + "<br/>"
				+ "Green - " + domainLevel.model().player().keys().stream().filter(k->k.color().equals(Color.GREEN)).count() + "<br/>"
				+ "Blue - " + domainLevel.model().player().keys().stream().filter(k->k.color().equals(Color.BLUE)).count()
				+ "</html>";
	}
	
	/**
	 * Steps move in recorded game.
	 * Public if fuzz testing requires it.
	 */
	public void stepMove() {
		if (!autoReplay) {
			if (timer.isRunning() || domainLevel.model().player().locked()) return;
		}
		if (!autoReplay && recorder.peekNextPlayerMove()!=null) {
			while (recorder.peekNextPlayerMove().direction()==MoveDirection.NONE) {
				System.out.println("SKIPPED NONE");
				recorder.stepMovePlayer(this);
			}
		}
		if (recorder.peekNextPlayerMove()==null) {
			System.out.println("NO MORE MOVES TO STEP");
			replayRecording();
			return;
		}
		timer.start();
		time = recorder.peekNextPlayerMove().time();
		recorder.stepMovePlayer(this);
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
		Playable music = SoundLines.Music.generate().setVolume(100).setLooping(true);
		musicMixer.add(music);
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
	
	/**
	 * Moves bugs for recorder
	 * 
	 * @param bugMoves bugs with move mapped to them
	 */
	public void moveBugs(Map<Integer, MoveDirection> bugMoves) {
		for (Map.Entry<Integer, MoveDirection> b : bugMoves.entrySet()) {
			domainLevel.model().entities().get(b.getKey()).move(Direction.valueOf(b.getValue().toString()), domainLevel.model());
		}
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
	public Pair<Integer, Integer> getPlayerPosition() {
		IntPoint playerLoc = domainLevel.model().player().location();
		return new Pair<>(playerLoc.x(), playerLoc.y());
	}

	/**
	 * Gets position of bugs for fuzz.
	 *
	 * @return bug positions, [bugNum][0] is x, [bugNum][1] is y position
	 */
	public List<Pair<Integer, Integer>> getBugPositions() {
		Collection<Entity> entities = domainLevel.model().entities().values();
		return entities.stream()
					   .filter(e -> e instanceof Bug)
					   .map(b -> new Pair<>(b.location().x(), b.location().y()))
					   .toList();
	}

	/**
	 * Gets number of treasure left for fuzz.
	 *
	 * @return treasure left as int
	 */
	public int treasureLeft() {
		return domainLevel.model().treasure().size();
	}

	public void bindFuzzer(Fuzzer fuzzer) {
		this.fuzzer = fuzzer;
	}

	/**
	 * Gets keys for fuzz, x position, y position, and color as rgb int.
	 *
	 * @return int[][] of keys, [keyNum][0] is x, [keyNum][1] is y, [keyNum][2] is rgb int
	 */
	public List<Pair<Integer, Integer>> getItems() {
		return domainLevel.model()
				          .tiles()
				          .tiles()
						  .stream()
						  .flatMap(ts -> ts.stream())
						  .filter(t -> t instanceof FreeTile f && f.item() != null)
						  .map(t -> new Pair<>(t.location().x(), t.location().y()))
						  .toList();
	}

	/**
	 * Returns exit lock position as an int[][] for fuzz.
	 *
	 * @return [0][0] is x, [0][1] is y position
	 */
	public Pair<Integer, Integer> getExit() {
		return domainLevel.model()
						  .tiles()
						  .tiles()
						  .stream()
						  .flatMap(ts -> ts.stream())
						  .filter(t -> t instanceof Exit)
						  .map(t -> new Pair<>(t.location().x(), t.location().y()))
						  .findAny()
						  .get();
	}

	/**
	 * Checks if player can move to tile position for fuzz.
	 *
	 * @param x x coordinate to move to
	 * @param y y coordinate to move to
	 * @return if player can move to that tile
	 */
	public boolean canMoveTo(Pair<Integer, Integer> loc) {
		return domainLevel.model()
						  .tiles()
						  .getTile(new IntPoint(loc.first(), loc.second()))
						  .canPlayerMoveTo(domainLevel.model());
	}

	/**
	 * Returns tiles as a boolean array
	 *
	 * @param level level to return tiles array
	 * @return boolean array
	 */
	public List<List<Pair<Integer, Integer>>> tilesArray(){
		return domainLevel.model()
				          .tiles()
				          .tiles()
				          .stream()
				          .map(ts ->
				              ts.stream()
				                 .map(t -> new Pair<>(t.location().x(), t.location().y()))
				                 .toList()
				              )
				          .toList();
	}
}