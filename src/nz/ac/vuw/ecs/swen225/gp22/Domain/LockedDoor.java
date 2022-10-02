package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.awt.Color;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;

public class LockedDoor extends WallTile {
    private LayeredTexture texture = Textures.Scrungle;
    private Color color;

    public LockedDoor(IntPoint location, Color color) {
        super(location);
        this.color = color;
    }

    public LayeredTexture texture() {
        return texture;
    }

    public Color color() {
        return color;
    }

    public void playerMovedTo(Model m) {
        Player p = m.player();
        int sizeBefore = p.keys().size();
        m.tiles().setTile(location(), new FreeTile(location(), null));
        Key key = p.keys().stream().filter(k -> k.color().equals(color)).findFirst().get();
        assert key.color().equals(color);
        p.keys().remove(key);
        int sizeAfter = p.keys().size();
        assert sizeBefore == sizeAfter+1;
    }

    public Boolean canPlayerMoveTo(Model m) {
        Player p = m.player();
        if (p.keys().stream().filter(k -> k.color().equals(color)).toList().isEmpty()) {
            // throw new Error("Player must have key of the right color to move");
            return false;
        }
        return true;
    }
}
