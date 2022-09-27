package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;

public record Level(Model model) {
    public static Level makeLevel(Player player, List<Entity> entities, int treasureCount, Tiles tiles, Runnable next, Runnable first) {
        return new Level(new Model() {
            private int tc = treasureCount;
            private Animator animator;

            public Player player() { return player; }
            public List<Entity> entities() { return entities; }
            public int treasureCount() { return tc; }
            public void decreaseTreasureCount() { tc--; }
            public Tiles tiles() { return tiles; }
            public void onGameOver() { first.run(); }
            public void onNextLevel() { next.run(); }
            public void bindAnimator(Animator a) { animator = a; }
            public Animator animator() { return animator; }
        });
    }

    public static Level level1() {
        return null; // replace with function in Persistency
    }

    public static Level level2() {
        return null; // replace with function in Persistency
    }
}
