package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;
import java.util.Map;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.AudioMixer;

public interface Model {
    Player player();
    Map<Integer, Entity> entities();
    List<Key> keys();
    List<Treasure> treasure();
    Tiles tiles();
    void onGameOver();
    void onNextLevel();
    void bindAnimator(Animator a);
    Animator animator();
    void bindMixer(AudioMixer m);
    AudioMixer mixer();
    void setTime(float t);
    float time();

    default void tick() {
        entities().values().forEach(e->e.tick(this));
    }
}
