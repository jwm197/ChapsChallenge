package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;

public record Level(Model model) {
    public static Level makeLevel(Player player, List<Entity> entities, List<Key> keys, List<Treasure> treasure, Tiles tiles, Runnable next, Runnable gameOver) {
        return new Level(new Model() {
            private Animator animator;

            public Player player() { return player; }
            public List<Entity> entities() { return entities; }
            public List<Key> keys() { return keys; }
            public List<Treasure> treasure() { return treasure; }
            public Tiles tiles() { return tiles; }
            public void onGameOver() { gameOver.run(); }
            public void onNextLevel() { next.run(); }
            public void bindAnimator(Animator a) { animator = a; }
            public Animator animator() { return animator; }
        });
    }
}
