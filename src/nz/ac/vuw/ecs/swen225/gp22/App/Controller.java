package nz.ac.vuw.ecs.swen225.gp22.App;

import java.awt.event.KeyEvent;
import nz.ac.vuw.ecs.swen225.gp22.Recorder.*;

/**
 * Manages user key input for controls
 * 
 * @author pratapshek 300565138
 */

public class Controller extends Keys{
	/**
	 * Constructs a controller to set actions for key input
	 * Parameter c to take in the chaps challenge object to call its methods
	 * 
	 * @param c chaps challenge object
	 */
	Controller(ChapsChallenge c) { 
		setAction(KeyEvent.VK_X,()->c.performAction("CTRL-X"),()->{},true);
		setAction(KeyEvent.VK_S,()->c.performAction("CTRL-S"),()->{},true);
		setAction(KeyEvent.VK_R,()->c.performAction("CTRL-R"),()->{},true);
		setAction(KeyEvent.VK_1,()->c.performAction("CTRL-1"),()->{},true);
		setAction(KeyEvent.VK_2,()->c.performAction("CTRL-2"),()->{},true);
		setAction(KeyEvent.VK_SPACE,()->c.performAction("SPACE"),()->{},false);
		setAction(KeyEvent.VK_ESCAPE,()->c.performAction("ESC"),()->{},false);
		setAction(KeyEvent.VK_UP, ()->{c.performAction("UP"); c.setCurrentMove(MoveDirection.UP);},
				()->{if(c.getCurrentMove().equals(MoveDirection.UP)) {c.setCurrentMove(MoveDirection.NONE);}}, false);
		setAction(KeyEvent.VK_DOWN, ()->{c.performAction("DOWN");c.setCurrentMove(MoveDirection.DOWN);},
				()->{if(c.getCurrentMove().equals(MoveDirection.DOWN)) {c.setCurrentMove(MoveDirection.NONE);}}, false);
		setAction(KeyEvent.VK_LEFT, ()->{c.performAction("LEFT");c.setCurrentMove(MoveDirection.LEFT);},
				()->{if(c.getCurrentMove().equals(MoveDirection.LEFT)) {c.setCurrentMove(MoveDirection.NONE);}}, false);
		setAction(KeyEvent.VK_RIGHT, ()->{c.performAction("RIGHT");c.setCurrentMove(MoveDirection.RIGHT);},
				()->{if(c.getCurrentMove().equals(MoveDirection.RIGHT)) {c.setCurrentMove(MoveDirection.NONE);}}, false);
	}
}
