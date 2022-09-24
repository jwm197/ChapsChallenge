package nz.ac.vuw.ecs.swen225.gp22.App;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener{
	final ChapsChallenge chapsChallenge;
	
	Controller(ChapsChallenge c){
		this.chapsChallenge = c;
	}	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

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
				System.out.println(levelName);
				// DOMAIN/PERSISTENCY/RECORDER?
				/* 
				 * timer included in level data?
				 * new Persistency().saveXML(levelName, levelData);
				 * 
				 */
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
		if (e.getKeyCode()==KeyEvent.VK_UP) {}
		// Moves chap down
		if (e.getKeyCode()==KeyEvent.VK_DOWN) {}
		// Moves chap left
		if (e.getKeyCode()==KeyEvent.VK_LEFT) {}
		// Moves chap right
		if (e.getKeyCode()==KeyEvent.VK_RIGHT) {}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Releasing key stops chap moving in that direction
		if (e.getKeyCode()==KeyEvent.VK_UP) {}
		if (e.getKeyCode()==KeyEvent.VK_DOWN) {}
		if (e.getKeyCode()==KeyEvent.VK_LEFT) {}
		if (e.getKeyCode()==KeyEvent.VK_RIGHT) {}
	}
}