package nz.ac.vuw.ecs.swen225.gp22.App;

import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

/**
 * An improved implementation of a thread-safe swing timer utilising java.util.timer
 * 
 * <p>
 * This timer utilises timer.scheduleAtFixedRate to ensure that evens are on average evenly spaced.
 * It is far more performant compared to javax.swing.timer as it guarentees average time rather than minimum time.
 * <\p>
 *
 * @author anfri
 */
class BetterTimer {
    private int delay;
    private Runnable task;
    private boolean running;
    private Timer timer; //internal timer reference 
    
    /**
     * Constructor
     * 
     * @param delay delay between tasks
     * @param task task to be performed
     */
    public BetterTimer(int delay, Runnable task) {
        this.task = task;
        this.delay = delay;
    }
    
    /**
     * Returns the state of the timer
     * 
     * @return if the timer is currently active
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Starts the timer
     */
    public void start() {
        //only start if not running already
        if (running) return;
        running = true;
        
        //generate new interal timer object
        timer = new Timer();
        
        //schedule repeating task
        timer.scheduleAtFixedRate(
            //anon class must be used as TimerTask is annoyingly a single-method abstract class :(
            new TimerTask() {
                public void run() {
                    try {
                        SwingUtilities.invokeAndWait(task); //run on swing thread
                    } catch (InvocationTargetException | InterruptedException e) {} //skip event on exception
                }
            }, 0, delay //0 = start immediately
        );
    }

    /**
     * Stops the timer
     */
    public void stop() {
        //only stop if running
        if (!running) return;
        running = false;
        
        //stop and purge all events on the timer so they can be gc'd safely
        //unfortunately, whilst timers can handle multiple event loops, they cannot be stopped independently, hense the one timer per timertask structure
        timer.cancel();
        timer.purge();
    }
}
