package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;

record Level(Model model) {
    static Level makeLevel(Player player, List<Bug> bugs, List<Key> keys, List<Treasure> treasure, Tiles tiles, Runnable next, Runnable first) {
        return new Level(new Model() {
            public Player player() { return player; }
            public List<Bug> bugs() { return bugs; }
            public List<Key> keys() { return keys; }
            public List<Treasure> treasure() { return treasure; }
            public Tiles tiles() { return tiles; }
            public void onGameOver() { first.run(); }
            public void onNextLevel() { next.run(); }
            public void bindAnimator(Animator a) { return; } // unsure about this
        });
    }

    static Level level1() {
        return null; // replace with function in Persistency
    }

    static Level level2() {
        return null; // replace with function in Persistency
    }
}
