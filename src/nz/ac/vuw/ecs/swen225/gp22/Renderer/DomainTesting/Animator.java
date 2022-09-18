package nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.TextureSequence;

public interface Animator {
	void AnimateMoving(Entity target, 
					   TextureSequence images,
					   Position initPos, Position finalPos,
					   int duration, int frameDuration,
					   boolean isLooping, 
					   Runnable onCompletion);
	
	default void AnimateMoving(Entity target, TextureSequence images, Position pos, int duration, Runnable onCompletion) {
		AnimateMoving(target, images, target.getPos(), pos, duration, duration/images.frameCount(), false, onCompletion);
	}
	
	default void AnimateMoving(Entity target,
							   TextureSequence images, 
							   Position pos, 
							   int duration, int frameDuration, 
							   boolean isLooping, 
							   Runnable onCompletion) {
		AnimateMoving(target, images, target.getPos(), pos, duration, frameDuration, isLooping, onCompletion);
	}
	
	void AnimateStatic(Entity target, 
					   TextureSequence images,
					   int duration, int frameDuration,
					   boolean isLooping,
					   Runnable onCompletion);
	
	
	default void AnimateStatic(Entity target, TextureSequence images, int duration, Runnable onCompletion) {
		AnimateStatic(target, images, duration, duration/images.frameCount(), false, onCompletion);
	}
	
	public static final Animator NONE = new Animator() {
		public void AnimateMoving(Entity target,
								  TextureSequence images,
								  Position initPos, Position finalPos,
								  int duration, int frameDuration, 
								  boolean isLooping, 
								  Runnable onCompletion) {
			onCompletion.run();
		}
		public void AnimateStatic(Entity target,
								  TextureSequence images,
								  int duration, int frameDuration,
								  boolean isLooping,
								  Runnable onCompletion) {
			onCompletion.run();
		}
	};
}
