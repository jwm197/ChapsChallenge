package nz.ac.vuw.ecs.swen225.gp22.App;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

class Keys implements KeyListener {
  private Map<Integer,Runnable> actionsPressed = new HashMap<>();
  private Map<Integer,Runnable> ctrlActionsPressed = new HashMap<>();
  private Map<Integer,Runnable> actionsReleased = new HashMap<>();
  public void setAction(int keyCode,Runnable onPressed,Runnable onReleased, boolean ctrl){
	if (ctrl) { 
		ctrlActionsPressed.put(keyCode,onPressed);
	} else { 
		actionsPressed.put(keyCode,onPressed); 
		actionsReleased.put(keyCode,onReleased);
	}
  }
  public void keyTyped(KeyEvent e){}
  public void keyPressed(KeyEvent e){
    assert SwingUtilities.isEventDispatchThread();
    actionsPressed.getOrDefault(e.getKeyCode(),()->{}).run();
    if (e.isControlDown()) { ctrlActionsPressed.getOrDefault(e.getKeyCode(),()->{}).run(); }
  }
  public void keyReleased(KeyEvent e){
    assert SwingUtilities.isEventDispatchThread();
    actionsReleased.getOrDefault(e.getKeyCode(),()->{}).run();
  }
}