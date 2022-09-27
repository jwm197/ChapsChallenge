package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;

public class Player implements Entity {
    private LayeredTexture texture;
    private Point location;
    private List<Key> keys;
    private Boolean locked;

    public Player(Point location) {
        this.location=location;
        locked = false;
    }

    public LayeredTexture texture() {
        return texture;
    }

    public Point location() {
        return location;
    }
    
    public List<Key> keys() {
        return keys;
    }

    public void movePlayer(Animator a, Direction d, Model m) {
        if (locked) return;
        locked = true;

        Point newPos = location.add(d.direction());
        m.tiles().getTile(newPos).playerMovedTo(m);
        a.Animate(this, null, newPos, 100, () -> {
            location = newPos;
            locked = false;
        });
    }
}
