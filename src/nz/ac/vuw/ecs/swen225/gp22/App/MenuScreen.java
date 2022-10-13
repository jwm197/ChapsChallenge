package nz.ac.vuw.ecs.swen225.gp22.App;

import javax.swing.*;
import java.awt.Font;

/**
 * JPanel for the menu screen
 * 
 * @author pratapshek
 *
 */

public class MenuScreen extends JPanel implements JHelper{
	// Private variables
	private ChapsChallenge cc;
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a menu screen
	 * 
	 * @param c chaps challenge game
	 */
	public MenuScreen(ChapsChallenge c){
		super();
		cc = c;
	}
	
	/**
	 * Makes the menu screen by adding components to a panel
	 * 
	 * @return returns the jpanel
	 */
	public JPanel make() {
		JPanel panel = new JPanel();
		// JLabel for displaying game title
		JLabel title = JHelper.createLabel("CHAPS CHALLENGE", SwingConstants.CENTER, LARGE_FONT, 0, (int)(-JHelper.HEIGHT*0.425), JHelper.WIDTH, JHelper.HEIGHT);
		final JPopupMenu menu = new JPopupMenu("Menu");
		JMenuItem lvl1 = new JMenuItem("Level 1");
		JMenuItem lvl2 = new JMenuItem("Level 2");
		lvl1.addActionListener(e->cc.gameScreen("level1.xml"));
		lvl2.addActionListener(e->cc.gameScreen("level2.xml"));
		menu.add(lvl1);
		menu.add(lvl2);
		// JButton to start game
		JButton startButton = JHelper.createButton("Start", JHelper.WIDTH/4, (int)(JHelper.HEIGHT*0.15), JHelper.WIDTH/2, JHelper.HEIGHT/10, SMALL_FONT, e->{});
		startButton.addActionListener(e->menu.show(startButton, JHelper.WIDTH/2, (int)(JHelper.HEIGHT*0.02)));
		
		// JButton to resume saved game from file selector
		JButton load = JHelper.createButton("Load Level", JHelper.WIDTH/4, (int)(JHelper.HEIGHT*0.275), JHelper.WIDTH/2, JHelper.HEIGHT/10, SMALL_FONT, e->cc.loadGame());
		// JButton to resume saved game from file selector
		JButton loadRecorder = JHelper.createButton("Load Recorded Game", JHelper.WIDTH/4, (int)(JHelper.HEIGHT*0.4), JHelper.WIDTH/2, JHelper.HEIGHT/10, SMALL_FONT, e->cc.loadRecordedGame());
		// JButton to view game help info
		JButton help = JHelper.createButton("Help", JHelper.WIDTH/4, (int)(JHelper.HEIGHT*0.525), JHelper.WIDTH/2, JHelper.HEIGHT/10, SMALL_FONT, e->cc.gameHelp());
		// JButton to view controls
		JButton controls = JHelper.createButton("Controls", JHelper.WIDTH/4, (int)(JHelper.HEIGHT*0.65), JHelper.WIDTH/2, JHelper.HEIGHT/10, SMALL_FONT, e->cc.viewControls());
		// JButton to quit game
		JButton quit = JHelper.createButton("Quit", JHelper.WIDTH/4, (int)(JHelper.HEIGHT*0.775), JHelper.WIDTH/2, JHelper.HEIGHT/10, SMALL_FONT, e->System.exit(0));
		panel.setLayout(null);
		JHelper.addComponents(panel, title, startButton, load, loadRecorder, help, controls, quit);
		return panel;
	}
}