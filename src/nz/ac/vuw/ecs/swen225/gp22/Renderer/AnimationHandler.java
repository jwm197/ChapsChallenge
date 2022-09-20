package nz.ac.vuw.ecs.swen225.gp22.Renderer;

import java.awt.Graphics;
import java.util.HashMap;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting.Animator;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting.Entity;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting.Position;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.TextureSequence;

public class AnimationHandler implements Animator, Drawable {
	private HashMap<Entity, Animation> animations = new HashMap<>();
	
	@Override
	public void Animate(Entity entity, TextureSequence frames, Position<Integer> initPos, Position<Integer> finalPos,
			int duration, int frameDuration, boolean isLooping, Runnable onCompletion) {
		AnimationProperties properties = new AnimationProperties(
				frames, initPos.doubleValue(), finalPos.doubleValue(), duration, frameDuration, isLooping, onCompletion
		);
		animations.put(entity, new Animation(properties));
	}
	
	public boolean animating(Entity e) {
		return animations.containsKey(e);
	}
	
	public Animation get(Entity e) {
		return animations.get(e);
	}
	
	public void tick() {
		animations.forEach((e, a) -> a.tick());
		animations.entrySet().removeIf(e -> e.getValue().completed());
	}
	
	@Override
	public void draw(Graphics g, Renderer r) {
		animations.forEach((e, a) -> a.draw(g, r));
	}
}
