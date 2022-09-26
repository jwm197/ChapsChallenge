package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;

public interface Model {
    Player player();
    List<Bug> bugs();
    List<Key> keys();
    List<Treasure> treasure();
    Tiles tiles();
    void onGameOver();
    void onNextLevel();
    void bindAnimator(Animator a);    
}
