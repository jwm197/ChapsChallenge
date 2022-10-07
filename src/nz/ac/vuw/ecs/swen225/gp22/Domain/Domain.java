package nz.ac.vuw.ecs.swen225.gp22.Domain;

/**
 * Domain module
 */

public class Domain {
    private Level level;

    public Domain() {
        level = Level.level("level1.xml");
    }

    public Level level() {
        if (level == null) throw new IllegalStateException("No level has been loaded");
        return level;
    }

    public void setLevel(String levelName) {
        this.level = Level.level(levelName);
    }
}
