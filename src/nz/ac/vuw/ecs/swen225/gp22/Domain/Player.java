package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;

public class Player implements Entity {
    private LayeredTexture texture;
    private IntPoint location;
    private List<Key> keys;
    private Boolean locked;

    public Player(IntPoint location) {
        this.location=location;
        locked = false;
    }

    public LayeredTexture texture() {
        return texture;
    }

    public IntPoint location() {
        return location;
    }
    
    public List<Key> keys() {
        return keys;
    }

    public void movePlayer(Animator a, Direction d, Model m) {
        if (locked) return;
        locked = true;

        IntPoint newPos = location.add(d.direction());
        m.tiles().getTile(newPos).playerMovedTo(m);
        a.Animate(this, null, newPos, 100, () -> {
            location = newPos;
            locked = false;
        });
    }
}
