package nz.ac.vuw.ecs.swen225.gp22.Domain;

public interface Animator {
    public void Animate(
        Entity entity,          // entity being animated
        TextureSequence frames, // animation to use
        Point initPos,          // starting position
        Point finalPos,         // ending position
        int duration,           // length of animation (in gameticks)
        int frameDuration,      // length of frame     (in gameticks)
        boolean isLooping,      // whether to loop the animation or pause on final frame
        Runnable onCompletion   // lambda callback method for completion
    );

    public void Animate(
        Entity entity,
        TextureSequence frames,
        Point finalPos,
        int duration,
        Runnable onCompletion
    );
}
