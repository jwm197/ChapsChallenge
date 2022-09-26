package nz.ac.vuw.ecs.swen225.gp22.Domain;

/**
 * Domain module
 */

public class Domain {
    private Level level;

    public Domain() {
        level = Level.level1();
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
