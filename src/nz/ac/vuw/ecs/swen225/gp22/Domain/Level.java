package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;
import java.util.Map;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.AudioMixer;

public record Level(Model model) {
    public static Level makeLevel(Player player, Map<Integer, Entity> entities, List<Key> keys, List<Treasure> treasure, Tiles tiles, Runnable next, Runnable gameOver) {
        return new Level(new Model() {
            private Animator animator;
            private AudioMixer mixer;
            private float time;

            public Player player() { return player; }
            public Map<Integer, Entity> entities() { return entities; }
            public List<Key> keys() { return keys; }
            public List<Treasure> treasure() { return treasure; }
            public Tiles tiles() { return tiles; }
            public void onGameOver() { gameOver.run(); }
            public void onNextLevel() { next.run(); }
            public void bindAnimator(Animator a) { animator = a; }
            public Animator animator() { return animator; }
            public void bindMixer(AudioMixer m) { mixer = m; }
            public AudioMixer mixer() { return mixer; }
            public void setTime(float t) { time = t; }
            public float time() { return time; }
        });
    }
}
