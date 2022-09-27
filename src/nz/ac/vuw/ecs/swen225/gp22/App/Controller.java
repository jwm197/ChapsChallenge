package nz.ac.vuw.ecs.swen225.gp22.App;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;

import org.dom4j.DocumentException;

import nz.ac.vuw.ecs.swen225.gp22.Persistency.*;
import nz.ac.vuw.ecs.swen225.gp22.Domain.*;

public class Controller implements KeyListener{
	final ChapsChallenge chapsChallenge;
	
	Controller(ChapsChallenge c) { this.chapsChallenge = c; }	
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {		
		// Checks if control is held down
		if (e.isControlDown()) {
			// Ctrl+x exits the game, doesn't save
			if (e.getKeyCode()==KeyEvent.VK_X) { chapsChallenge.menuScreen(); }
			// Ctrl+s exits and saves the game
			if (e.getKeyCode()==KeyEvent.VK_S) {
				chapsChallenge.pause(true);
				String levelName = chapsChallenge.setLevelName();
				
				// DOMAIN/PERSISTENCY/RECORDER?
				HashMap<String, ObjectBuilder> levelData = new HashMap<>();
				// timer included in level data?
				try { new Persistency().saveXML(levelName, levelData); } 
				catch (ParserException e1) { e1.printStackTrace(); } 
				catch (IOException e1) { e1.printStackTrace(); } 
				catch (DocumentException e1) { e1.printStackTrace(); }
				
				// return to menu
				chapsChallenge.menuScreen();
			}
			// Ctrl+r file selector to resume a saved game
			if (e.getKeyCode()==KeyEvent.VK_R) { chapsChallenge.loadGame(); }
			// Ctrl+1 starts a new game at level 1
			if (e.getKeyCode()==KeyEvent.VK_1) { chapsChallenge.newGame("1"); }
			// Ctrl+2 starts a new game at level 2
			if (e.getKeyCode()==KeyEvent.VK_2) { chapsChallenge.newGame("2"); }
		}
		
		// Space pauses game
		if (e.getKeyCode()==KeyEvent.VK_SPACE) { chapsChallenge.pause(true); }
		// Esc resumes game
		if (e.getKeyCode()==KeyEvent.VK_ESCAPE) { chapsChallenge.pause(false); }
		
		// Moves chap up
		if (e.getKeyCode()==KeyEvent.VK_UP) {
			chapsChallenge.domainObject.model().player().
			movePlayer(null, Direction.UP, chapsChallenge.domainObject.model());
		}
		// Moves chap down
		if (e.getKeyCode()==KeyEvent.VK_DOWN) {
			chapsChallenge.domainObject.model().player().
			movePlayer(null, Direction.DOWN, chapsChallenge.domainObject.model());
		}
		// Moves chap left
		if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			chapsChallenge.domainObject.model().player().
			movePlayer(null, Direction.LEFT, chapsChallenge.domainObject.model());
		}
		// Moves chap right
		if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			chapsChallenge.domainObject.model().player().
			movePlayer(null, Direction.RIGHT, chapsChallenge.domainObject.model());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
