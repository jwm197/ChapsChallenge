package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;
import java.util.ArrayList;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;

public class Player implements Entity {
    private LayeredTexture texture = Textures.Scrungle;
    private IntPoint location;
    private List<Key> keys = new ArrayList<>();
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

    public void movePlayer(Direction d, Model m) {
        if (locked) return;
        locked = true;

        IntPoint newPos = location.add(d.direction());
        if (newPos.x()<0 || newPos.x()>=m.tiles().width()
        || newPos.y()<0 || newPos.y()>=m.tiles().height()) return;

        m.tiles().getTile(newPos).playerMovedTo(m);
        m.animator().Animate(this, texture, newPos, 30, () -> {
            location = newPos;
            locked = false;
        });
    }
}
