package nz.ac.vuw.ecs.swen225.gp22.Renderer;

import java.awt.Graphics;
import java.util.HashMap;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Animator;
import nz.ac.vuw.ecs.swen225.gp22.Domain.IntPoint;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Entity;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.TextureSequence;

/**
 * Manages and queues the rolling stack of currently playing animation objects
 * 
 * @author anfri
 */
class AnimationHandler implements Animator, Drawable {
	private HashMap<Entity, Animation> animations = new HashMap<>();
	
	@Override
	public void Animate(Entity entity, TextureSequence frames, IntPoint initPos, IntPoint finalPos,
			int duration, int frameDuration, boolean isLooping, Runnable onCompletion) {
		
		//generate new properties and add the animation to the HashMap
		AnimationProperties properties = new AnimationProperties(
				frames, initPos.toDoublePoint(), finalPos.toDoublePoint(), duration, frameDuration, isLooping, onCompletion
		);
		animations.put(entity, new Animation(properties));
	}
	
	/**
	 * Check whether a given entity is currently being animated
	 * 
	 * @param e entity to check
	 * @return is animating
	 */
	boolean animating(Entity e) {
		return animations.containsKey(e);
	}
	
	/**
	 * Get the animation associated with an given entity
	 * 
	 * @param e entity to request
	 * @return animation associated with the entity in the HashMap
	 */
	Animation get(Entity e) {
		return animations.get(e);
	}
	
	/**
	 * Update all animations within the internal rolling stack
	 */
	void tick() {
		//tick all animations
		animations.forEach((e, a) -> a.tick());
		
		//remove completed animations from the HashMap
		animations.entrySet().removeIf(e -> e.getValue().completed());
	}
	
	@Override
	public void draw(Graphics g, Renderer r) {
		animations.forEach((e, a) -> a.draw(g, r));
	}

	/**
	 * Remove all currently playing animations
	 */
	void clear() {
		animations.clear();
	}
}
