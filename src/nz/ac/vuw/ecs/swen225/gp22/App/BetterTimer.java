package nz.ac.vuw.ecs.swen225.gp22.App;

/**
 * Better than the swing timer :)
 * 
 * @author pratapshek
 */

class BetterTimer {
    private int delay;
    private Runnable task;
    private boolean running;
    private java.util.Timer timer;
    public BetterTimer(int delay, Runnable task) {
        this.task = task;
        this.delay = delay;
    }

    public boolean isRunning() {
        return running;
    }

    public void start() {
        if (running) return;
        running = true;
        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(
            new java.util.TimerTask() {
                public void run() {
                    /* USE THIS ONLY IF YOU'RE INVOKING OUTSIDE OF SWING THREAD!
                    try {
                            SwingUtilities.invokeAndWait(task);
                    } catch (InvocationTargetException | InterruptedException e) {}
                    */



                    /* USE THIS IF INVOKING INSIDE SWING THREAD! */
                    task.run();
                }
            }, 0, delay);
    }

    public void stop() {
            if (!running) return;
            running = false;
            timer.cancel();
            timer.purge();
    }
}