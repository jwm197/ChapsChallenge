package nz.ac.vuw.ecs.swen225.gp22.Renderer;

import java.awt.Graphics;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting.Entity;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting.Position;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.TextureSequence;

record AnimationProperties(
		Entity entity,
   		TextureSequence frames,
   		Position<Double> startPos, Position<Double> endPos,
   		int duration, int frameDuration,
   		boolean isLooping,
   		Runnable onCompletion) {}

class Animation implements Drawable {
	private final AnimationProperties properties;
	private int tick = 0;
	private int frame = 0;
	private Position<Double> position;
	private boolean completed = false;
	
	Animation(AnimationProperties properties) {
		this.properties = properties;
		this.position = properties.startPos();
	}
	
	public boolean completed() {
		return completed;
	}
	
	public double percentage() {
		return ((double)tick)/properties.duration();
	}
	
	public void tick() {
		tick++;
		position = Position.tween(properties.startPos(), properties.endPos(), percentage());
		if (tick%properties.frameDuration() == 0 && (properties.isLooping() || frame != properties.frames().frameCount()-1)) {
			frame = (frame+1)%properties.frames().frameCount();
		}
		
		if (tick >= properties.duration()) {
			properties.onCompletion().run();
			completed = true;
		}
	}

	@Override
	public void draw(Graphics g, Renderer r) {
		r.drawTexture(g, properties.frames().get(frame), position);
	}
}