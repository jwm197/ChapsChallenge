package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;
import java.util.Map;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.AudioMixer;

/**
 * Represents a level of the game.
 * 
 * @author sidoroyuri
 * 
 */
public record Level(Model model) {
    public static Level makeLevel(Player player, Map<Integer, Entity> entities, List<Key> keys, List<Treasure> treasure, Tiles tiles, Runnable next, Runnable gameOver) {
        return new Level(new Model() {
            private Animator animator; // Animator to perform animations
            private AudioMixer mixer; // Mixer to play sounds
            private float time; // Time remaining in seconds

            @Override
            public Player player() { return player; }
            @Override
            public Map<Integer, Entity> entities() { return entities; }
            @Override
            public List<Key> keys() { return keys; }
            @Override
            public List<Treasure> treasure() { return treasure; }
            @Override
            public Tiles tiles() { return tiles; }
            @Override
            public void onGameOver() { gameOver.run(); }
            @Override
            public void onNextLevel() { next.run(); }
            @Override
            public void bindAnimator(Animator a) { animator = a; }
            @Override
            public Animator animator() { return animator; }
            @Override
            public void bindMixer(AudioMixer m) { mixer = m; }
            @Override
            public AudioMixer mixer() { return mixer; }
            @Override
            public void setTime(float t) { time = t; }
            @Override
            public float time() { return time; }
        });
    }
}
