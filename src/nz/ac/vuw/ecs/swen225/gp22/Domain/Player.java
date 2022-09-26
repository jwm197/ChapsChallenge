package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;

public class Player implements Entity {
    private LayeredTexture texture;
    private Point location;
    private List<Key> keys;

    public Player(Point location) {
        this.location=location;
    }

    public LayeredTexture texture() {
        return texture;
    }

    public Point location() {
        return location;
    }
    
    public List<Key> getKeys() {
        return keys;
    }
}
