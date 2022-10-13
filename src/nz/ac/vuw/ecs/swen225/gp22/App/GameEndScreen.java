package nz.ac.vuw.ecs.swen225.gp22.App;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * JPanel for the game end screen
 * 
 * @author pratapshek
 *
 */

public class GameEndScreen extends JPanel implements JHelper{
	// Private variables
	private ChapsChallenge cc;
	private boolean completed;
	private float time;
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a game end screen
	 * 
	 * @param c chaps challenge game
	 * @param b whether level was completed
	 * @param t time left in game
	 */
	public GameEndScreen(ChapsChallenge c, boolean b, float t){
		super();
		cc = c;
		completed = b;
		time = t;
	}
	
	/**
	 * Makes the game screen by adding components to a panel
	 * 
	 * @return returns the jpanel
	 */
	public JPanel make() {
		JPanel panel = new JPanel();
		JPanel bottomPanel = new JPanel();
		// JLabel for displaying help title
		var endTitle = JHelper.createLabel(completed?"LEVEL COMPLETE!":"LEVEL FAILED!", SwingConstants.CENTER, LARGE_FONT, 0, (int)(-JHelper.HEIGHT*0.4), JHelper.WIDTH, JHelper.HEIGHT);
		// JLabel to display help info
		var endInfo = JHelper.createLabel(completed?"You completed the level with: " + (float)Math.round(time*10)/10 + "s left!"
				:"You failed the level by: " + (time==0?"No time left!":"Eaten by bug!"), 
				SwingConstants.CENTER, MASSIVE_FONT, 0, -JHelper.HEIGHT/20, JHelper.WIDTH, JHelper.HEIGHT);
		// JButton to go back to menu
		var back = JHelper.createButton("Back", (int)(JHelper.WIDTH*0.075), (int)(JHelper.HEIGHT*0.75), JHelper.WIDTH/5, JHelper.HEIGHT/10, SMALL_FONT, e->cc.menuScreen());
		var retry = JHelper.createButton("Retry", (int)(JHelper.WIDTH*0.35), (int)(JHelper.HEIGHT*0.75), JHelper.WIDTH/5, JHelper.HEIGHT/10, SMALL_FONT, e->cc.retry());
		var saveRecording = JHelper.createButton("Save Recording", (int)(JHelper.WIDTH*0.5), (int)(JHelper.HEIGHT*0.75), JHelper.WIDTH/5, JHelper.HEIGHT/10, SMALL_FONT, e->cc.saveRecording());
		
		// adds components to panel
		panel.setLayout(new BorderLayout());
		panel.add(endTitle);
		panel.add(endInfo);
		JHelper.addComponents(bottomPanel, back, retry, saveRecording);
		panel.add(bottomPanel, BorderLayout.SOUTH);
		return panel;
	}
}