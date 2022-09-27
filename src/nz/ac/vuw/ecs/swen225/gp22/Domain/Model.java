package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;

public interface Model {
    Player player();
    List<Entity> entities();
    int treasureCount();
    void decreaseTreasureCount();
    Tiles tiles();
    void onGameOver();
    void onNextLevel();
    void bindAnimator(Animator a);    
    Animator animator();
}
