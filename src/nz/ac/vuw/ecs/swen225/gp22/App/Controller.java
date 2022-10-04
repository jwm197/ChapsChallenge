package nz.ac.vuw.ecs.swen225.gp22.App;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;

import org.dom4j.DocumentException;

import nz.ac.vuw.ecs.swen225.gp22.Persistency.*;
import nz.ac.vuw.ecs.swen225.gp22.Recorder.*;
import nz.ac.vuw.ecs.swen225.gp22.Domain.*;

/**
 * Manages user key input for controls
 * 
 * @author pratapshek
 */

public class Controller extends Keys{
	Controller(ChapsChallenge c) { 
		setAction(KeyEvent.VK_X,()->c.performAction("CTRL-X"),()->{},true);
		setAction(KeyEvent.VK_S,()->c.performAction("CTRL-S"),()->{},true);
		setAction(KeyEvent.VK_R,()->c.performAction("CTRL-R"),()->{},true);
		setAction(KeyEvent.VK_1,()->c.performAction("CTRL-1"),()->{},true);
		setAction(KeyEvent.VK_2,()->c.performAction("CTRL-2"),()->{},true);
		setAction(KeyEvent.VK_SPACE,()->c.performAction("SPACE"),()->{},false);
		setAction(KeyEvent.VK_ESCAPE,()->c.performAction("ESC"),()->{},false);
		setAction(KeyEvent.VK_UP, ()->{c.performAction("UP");c.currentMove=MoveDirection.UP;},
				()->{if(c.currentMove==MoveDirection.UP) {c.currentMove=MoveDirection.NONE;}}, false);
		setAction(KeyEvent.VK_DOWN, ()->{c.performAction("DOWN");c.currentMove=MoveDirection.DOWN;},
				()->{if(c.currentMove==MoveDirection.DOWN) {c.currentMove=MoveDirection.NONE;}}, false);
		setAction(KeyEvent.VK_LEFT, ()->{c.performAction("LEFT");c.currentMove=MoveDirection.LEFT;},
				()->{if(c.currentMove==MoveDirection.LEFT) {c.currentMove=MoveDirection.NONE;}}, false);
		setAction(KeyEvent.VK_RIGHT, ()->{c.performAction("RIGHT");c.currentMove=MoveDirection.RIGHT;},
				()->{if(c.currentMove==MoveDirection.RIGHT) {c.currentMove=MoveDirection.NONE;}}, false);
	}
}
