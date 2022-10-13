package nz.ac.vuw.ecs.swen225.gp22.App;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

/**
 * Key listener for key input
 * 
 * @author pratapshek
 *
 */

class Keys implements KeyListener {
  private Map<Integer,Runnable> actionsPressed = new HashMap<>();
  private Map<Integer,Runnable> ctrlActionsPressed = new HashMap<>();
  private Map<Integer,Runnable> actionsReleased = new HashMap<>();
  
  /**
   * Sets action for keycode
   * 
   * @param keyCode key code for key input
   * @param onPressed runnable for when key pressed
   * @param onReleased runnable for when key released
   * @param ctrl if key input requires ctrl to be held
   */
  public void setAction(int keyCode,Runnable onPressed,Runnable onReleased, boolean ctrl){
	if (ctrl) { 
		ctrlActionsPressed.put(keyCode,onPressed);
	} else { 
		actionsPressed.put(keyCode,onPressed); 
		actionsReleased.put(keyCode,onReleased);
	}
  }
  
  /**
   * Event for key typed
   */
  public void keyTyped(KeyEvent e){}
  
  /**
   * Event for key pressed
   */
  public void keyPressed(KeyEvent e){
    assert SwingUtilities.isEventDispatchThread();
    actionsPressed.getOrDefault(e.getKeyCode(),()->{}).run();
    if (e.isControlDown()) { ctrlActionsPressed.getOrDefault(e.getKeyCode(),()->{}).run(); }
  }
  
  /**
   * Event for key released
   */
  public void keyReleased(KeyEvent e){
    assert SwingUtilities.isEventDispatchThread();
    actionsReleased.getOrDefault(e.getKeyCode(),()->{}).run();
  }
}