package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.TextureSequence;

public interface Animator {
	void Animate(Entity entity, 
			     TextureSequence frames,
				 IntPoint initPos, IntPoint finalPos,
				 int duration, int frameDuration,
				 boolean isLooping, 
				 Runnable onCompletion);
	
	default void Animate(Entity entity, TextureSequence frames, IntPoint finalPos, int duration, Runnable onCompletion) {
		Animate(entity, frames, entity.location(), finalPos, duration, duration/frames.frameCount(), false, onCompletion);
	}
	
	default void Animate(Entity entity,
						 TextureSequence frames, 
						 IntPoint finalPos, 
						 int duration, int frameDuration, 
						 boolean isLooping, 
						 Runnable onCompletion) {
		Animate(entity, frames, entity.location(), finalPos, duration, frameDuration, isLooping, onCompletion);
	}
	
	//FIXME: make lambda
	public static final Animator NONE = new Animator() {
		public void Animate(Entity entity,
							TextureSequence frames,
							IntPoint initPos, IntPoint finalPos,
							int duration, int frameDuration, 
							boolean isLooping, 
							Runnable onCompletion) {
			onCompletion.run();
		}
	};
}
