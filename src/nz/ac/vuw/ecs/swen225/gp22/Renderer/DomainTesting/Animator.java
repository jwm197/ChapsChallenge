package nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.TextureSequence;

public interface Animator {
	void Animate(Entity entity, 
					   TextureSequence frames,
					   Position<Integer> initPos, Position<Integer> finalPos,
					   int duration, int frameDuration,
					   boolean isLooping, 
					   Runnable onCompletion);
	
	default void Animate(Entity entity, TextureSequence frames, Position<Integer> pos, int duration, Runnable onCompletion) {
		Animate(entity, frames, entity.getPos(), pos, duration, duration/frames.frameCount(), false, onCompletion);
	}
	
	default void Animate(Entity entity,
							   TextureSequence frames, 
							   Position<Integer> pos, 
							   int duration, int frameDuration, 
							   boolean isLooping, 
							   Runnable onCompletion) {
		Animate(entity, frames, entity.getPos(), pos, duration, frameDuration, isLooping, onCompletion);
	}
	
	default void Animate(Entity entity, TextureSequence frames, int duration, Runnable onCompletion) {
		Animate(entity, frames, entity.getPos(), entity.getPos(), duration, duration/frames.frameCount(), false, onCompletion);
	}
	
	public static final Animator NONE = new Animator() {
		public void Animate(Entity entity,
								  TextureSequence frames,
								  Position<Integer> initPos, Position<Integer> finalPos,
								  int duration, int frameDuration, 
								  boolean isLooping, 
								  Runnable onCompletion) {
			onCompletion.run();
		}
	};
}
