package nz.ac.vuw.ecs.swen225.gp22.Renderer;

import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting.Animator;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting.Entity;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting.Position;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.TextureSequence;

public class AnimationHandler implements Animator, Drawable {
	private Set<Animation> animations = new HashSet<Animation>();
	
	@Override
	public void Animate(Entity entity, TextureSequence frames, Position<Integer> initPos, Position<Integer> finalPos,
			int duration, int frameDuration, boolean isLooping, Runnable onCompletion) {
		AnimationProperties properties = new AnimationProperties(
				entity, frames, initPos.doubleValue(), finalPos.doubleValue(), frameDuration, frameDuration, isLooping, onCompletion
		);
		animations.add(new Animation(properties));
	}
	
	public void tick() {
		animations.forEach(a -> a.tick());
		animations.removeIf(a -> a.completed());
	}
	
	@Override
	public void draw(Graphics g, Renderer r) {
		animations.forEach(a -> a.draw(g, r));
	}
	
	
}
