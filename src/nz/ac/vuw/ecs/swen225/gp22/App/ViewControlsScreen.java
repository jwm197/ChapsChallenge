package nz.ac.vuw.ecs.swen225.gp22.App;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * JPanel for the view controls screen
 * 
 * @author pratapshek
 *
 */

public class ViewControlsScreen extends JPanel implements JHelper{
	// Private variables
	private ChapsChallenge cc;
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a view controls screen
	 * 
	 * @param c chaps challenge game
	 */
	public ViewControlsScreen(ChapsChallenge c){
		super();
		cc = c;
	}
	
	/**
	 * Makes the view controls screen by adding components to a panel
	 * 
	 * @return returns the jpanel
	 */
	public JPanel make() {
		JPanel panel = new JPanel();
		// JLabel for displaying controls title
		var controlsText = JHelper.createLabel("Controls", SwingConstants.CENTER, LARGE_FONT, 0, (int)(-JHelper.HEIGHT*0.4), JHelper.WIDTH, JHelper.HEIGHT);
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
		controlsInfo.setBounds(JHelper.WIDTH/5, -JHelper.HEIGHT/20, JHelper.WIDTH, JHelper.HEIGHT);
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
		controlsKeys.setBounds(-JHelper.WIDTH/5, -JHelper.HEIGHT/20, JHelper.WIDTH, JHelper.HEIGHT);
		// JButton to go back to menu
		var back = JHelper.createButton("Back", (int)(JHelper.WIDTH*0.075), (int)(JHelper.HEIGHT*0.75), JHelper.WIDTH/5, JHelper.HEIGHT/10, SMALL_FONT, e->cc.menuScreen());
		// adds components to panel
		panel.setLayout(null);
		JHelper.addComponents(panel, controlsText, controlsInfo, controlsKeys, back);
		return panel;
	}
}