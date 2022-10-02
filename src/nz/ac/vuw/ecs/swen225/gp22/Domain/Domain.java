package nz.ac.vuw.ecs.swen225.gp22.Domain;

/**
 * Domain module
 */

public class Domain {
    private Level level;

    public Domain() {
        level = Level.level("level1");
    }

    public Level level() {
        return level;
    }

    public void setLevel(String levelName) {
        this.level = Level.level(levelName);
    }
}
