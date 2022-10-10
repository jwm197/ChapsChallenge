package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.AudioMixer;

public interface Model {
    Player player();
    List<Entity> entities();
    List<Key> keys();
    List<Treasure> treasure();
    Tiles tiles();
    void onGameOver();
    void onNextLevel();
    void bindAnimator(Animator a);
    Animator animator();
    void bindMixer(AudioMixer m);
    AudioMixer mixer();

    default void tick() {
        entities().forEach(e->e.tick(this));
    }
}
