package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;

public class Player implements Entity {
    private Point location;
    private List<Key> keys;
    public Player(Point location) {
        this.location=location;
    }
    public Point location() {
        return location;
    }
    public List<Key> getKeys() {
        return keys;
    }
}
