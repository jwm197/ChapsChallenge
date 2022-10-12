package nz.ac.vuw.ecs.swen225.gp22.Renderer;

import java.awt.Graphics;

import nz.ac.vuw.ecs.swen225.gp22.Domain.DoublePoint;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.TextureSequence;
/**
 * Record class to handle Animation Constants
 *
 * @author anfri
 */
record AnimationProperties(
   	TextureSequence frames,
   	DoublePoint startPos, DoublePoint endPos,
   	int duration, int frameDuration,
   	boolean isLooping,
   	Runnable onCompletion) {}

/**
 * Animation object representing an animation on the animation stack
 * 
 * @author anfri
 */
class Animation implements Drawable {
	private final AnimationProperties properties;
	private int tick = 0;
	private int frame = 0;
	private DoublePoint position;
	private boolean completed = false;
	
	
	/**
	 * Constructor
	 * 
	 * @param properties property constants of the animation
	 */
	Animation(AnimationProperties properties) {
		this.properties = properties;
		this.position = properties.startPos();
	}

	/**
	 * Returns whether the animation is complete
	 * 
	 * @return completed
	 */
	boolean completed() {
		return completed;
	}

	/**
	 * Returns the percentage completion of the animation
	 * 
	 * @return percentage of completion
	 */
	double percentage() {
		return ((double)tick)/properties.duration();
	}
	
	/**
	 * Returns the tweened position of the subject being animated
	 * 
	 * @return position
	 */
	DoublePoint position() {
		return position;
	}
	
	/**
	 * Updates the animation to the new frame position and updates the current images accordingly
	 */
	void tick() {
		tick++;
		
		//updates the position based on the tween proportion between the start and end positions
		position = DoublePoint.tween(properties.startPos(), properties.endPos(), percentage());
		
		if (tick%properties.frameDuration() == 0           //if the animation is queued for a frame change
		&& (properties.isLooping()                         //and either the animation is looping
		|| frame != properties.frames().frameCount()-1)) { //or the animation hasn't reached the final frame
			frame = (frame+1)%properties.frames().frameCount(); //progress to the next frame
		}

		//if the animation is complete
		if (tick >= properties.duration()) {
			//run the callback and update state accordingly
			properties.onCompletion().run();
			completed = true;
		}
	}

	@Override
	public void draw(Graphics g, Renderer r) {
		r.drawTexture(g, properties.frames().get(frame), position);
	}
}