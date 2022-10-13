package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;
import java.util.Map;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.AudioMixer;

/**
 * An empty interface capturing the main aspects of a model.
 * 
 * @author Yuri Sidorov (300567814)
 * 
 */
public interface Model {
    /**
     * Get the player.
     * 
     * @return The player.
     */
    Player player();

    /**
     * Get the map of entities with their ids as their respective keys.
     * 
     * @return The map of entities.
     */
    Map<Integer, Entity> entities();

    /**
     * Get all the keys present on the tiles.
     * 
     * @return List of the keys present on the tiles.
     */
    List<Key> keys();

    /**
     * Get all the treasure present on the tiles.
     * 
     * @return List of the treasure present on the tiles.
     */
    List<Treasure> treasure();

    /**
     * Get all the tiles.
     * 
     * @return All the tiles.
     */
    Tiles tiles();

    /**
     * Go to the game over screen.
     */
    void onGameOver();

    /**
     * Go to the next level.
     */
    void onNextLevel();

    /**
     * Attach an Animator object to the model so that animations can be performed.
     * 
     * @param a The Animator object to attach.
     */
    void bindAnimator(Animator a);

    /**
     * Get the Animator object.
     * 
     * @return The Animator object.
     */
    Animator animator();

    /**
     * Attach a Mixer object to the model so that sounds can be played.
     * 
     * @param a The Mixer object to attach.
     */
    void bindMixer(AudioMixer m);

    /**
     * Get the Mixer object.
     * 
     * @return The Mixer object.
     */
    AudioMixer mixer();

    /**
     * Set the remaining time for the level.
     * 
     * @param t The remaining time in seconds.
     */
    void setTime(float t);

    /**
     * Get the remaining time for the level.
     * 
     * @return The remaining time in seconds.
     */
    float time();

    /**
     * Have all of the entities call their respective tick methods after each tick of the game's timer.
     */
    default void tick() {
        entities().values().forEach(e->e.tick(this));
    }
}
