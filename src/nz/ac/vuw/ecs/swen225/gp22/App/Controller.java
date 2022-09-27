package nz.ac.vuw.ecs.swen225.gp22.App;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;

import org.dom4j.DocumentException;

import nz.ac.vuw.ecs.swen225.gp22.Persistency.*;
import nz.ac.vuw.ecs.swen225.gp22.Domain.*;

/**
 * Manages user key input for controls
 * 
 * @author pratapshek
 */

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
			if (e.getKeyCode()==KeyEvent.VK_X) { chapsChallenge.performAction("CTRL-X"); }
			// Ctrl+s exits and saves the game
			if (e.getKeyCode()==KeyEvent.VK_S) { chapsChallenge.performAction("CTRL-S"); }
			// Ctrl+r file selector to resume a saved game
			if (e.getKeyCode()==KeyEvent.VK_R) { chapsChallenge.performAction("CTRL-R"); }
			// Ctrl+1 starts a new game at level 1
			if (e.getKeyCode()==KeyEvent.VK_1) { chapsChallenge.performAction("CTRL-1"); }
			// Ctrl+2 starts a new game at level 2
			if (e.getKeyCode()==KeyEvent.VK_2) { chapsChallenge.performAction("CTRL-2"); }
		}
		
		// Space pauses game
		if (e.getKeyCode()==KeyEvent.VK_SPACE) { chapsChallenge.performAction("SPACE"); }
		// Esc resumes game
		if (e.getKeyCode()==KeyEvent.VK_ESCAPE) { chapsChallenge.performAction("ESC"); }
		
		// Moves chap up
		if (e.getKeyCode()==KeyEvent.VK_UP) { chapsChallenge.performAction("UP"); }
		// Moves chap down
		if (e.getKeyCode()==KeyEvent.VK_DOWN) { chapsChallenge.performAction("DOWN"); }
		// Moves chap left
		if (e.getKeyCode()==KeyEvent.VK_LEFT) { chapsChallenge.performAction("LEFT"); }
		// Moves chap right
		if (e.getKeyCode()==KeyEvent.VK_RIGHT) { chapsChallenge.performAction("RIGHT"); }
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
