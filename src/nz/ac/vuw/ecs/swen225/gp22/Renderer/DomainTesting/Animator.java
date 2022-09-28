package nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.TextureSequence;

@Deprecated
public interface Animator {
	void Animate(Entity entity, 
			     TextureSequence frames,
				 Position<Integer> initPos, Position<Integer> finalPos,
				 int duration, int frameDuration,
				 boolean isLooping, 
				 Runnable onCompletion);
	
	default void Animate(Entity entity, TextureSequence frames, Position<Integer> finalPos, int duration, Runnable onCompletion) {
		Animate(entity, frames, entity.getPos(), finalPos, duration, duration/frames.frameCount(), false, onCompletion);
	}
	
	default void Animate(Entity entity,
						 TextureSequence frames, 
						 Position<Integer> finalPos, 
						 int duration, int frameDuration, 
						 boolean isLooping, 
						 Runnable onCompletion) {
		Animate(entity, frames, entity.getPos(), finalPos, duration, frameDuration, isLooping, onCompletion);
	}
	
	//FIXME: make lambda
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
