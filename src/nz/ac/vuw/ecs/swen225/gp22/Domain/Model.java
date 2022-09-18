package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;

public interface Model {
    Player player();
    List<Entity> entities();
    List<Key> keys();
    List<Treasure> treasure();
    void remove(Entity e);
    void remove(Key k);
    void remove(Treasure t);
    Tiles tiles();
    void onGameOver();
    void onNextLevel();
}
