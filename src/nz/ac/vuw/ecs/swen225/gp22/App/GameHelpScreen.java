package nz.ac.vuw.ecs.swen225.gp22.App;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * JPanel for the game help screen
 * 
 * @author pratapshek
 *
 */

public class GameHelpScreen extends JPanel implements JHelper{
	// Private variables
	private ChapsChallenge cc;
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a game help screen
	 * 
	 * @param c chaps challenge game
	 */
	public GameHelpScreen(ChapsChallenge c){
		super();
		cc = c;
	}
	
	/**
	 * Makes the game screen by adding components to a panel
	 * 
	 * @return returns the jpanel
	 */
	public JPanel make() {
		JPanel panel = new JPanel();
		// JLabel for displaying help title
		var helpTitle = JHelper.createLabel("Help", SwingConstants.CENTER, LARGE_FONT, 0, (int)(-JHelper.HEIGHT*0.4), JHelper.WIDTH, JHelper.HEIGHT);
		// JLabel to display help info
		var helpInfo = JHelper.createLabel("<html>"
				+ "Complete the level within the time limit<br/>"
				+ "Countdown is displayed at the top<br/>"
				+ "You cannot enter locked doors<br/>"
				+ "Pick up keys to unlock doors<br/>"
				+ "Inventory is displayed to the side<br/>"
				+ "Inventory shows collected keys<br/>"
				+ "Info tile will display help text<br/>"
				+ "Main objective is to collect all treasures<br/>"
				+ "Exit lock will be open when all treasures are collected<br/>"
				+ "Avoid bugs as they will eat you<br/>"
				+ "Enter exit tile once unlocked to pass level<br/>"
				+ "View controls from the menu screen button</html>", 
				SwingConstants.CENTER, SMALL_FONT, 0, -JHelper.HEIGHT/20, JHelper.WIDTH, JHelper.HEIGHT);
		// JButton to go back to menu
		var back = JHelper.createButton("Back", (int)(JHelper.WIDTH*0.075), (int)(JHelper.HEIGHT*0.75), JHelper.WIDTH/5, JHelper.HEIGHT/10, SMALL_FONT, e->cc.menuScreen());
		// adds components to panel
		panel.setLayout(null);
		JHelper.addComponents(panel, helpTitle, helpInfo, back);
		return panel;
	}
}