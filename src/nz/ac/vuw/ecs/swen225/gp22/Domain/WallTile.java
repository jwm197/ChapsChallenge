package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;

public class WallTile implements Tile {
    private LayeredTexture texture = Textures.Wall;
    private IntPoint location;

    public WallTile(IntPoint location) {
        this.location = location;
    }

    public LayeredTexture texture() {
        return texture;
    }

    public IntPoint location() {
        return location;
    }

    public void playerMovedTo(Model m) {
        return;
    }

    public Boolean canPlayerMoveTo(Model m) {
        // throw new Error("Player can't move to wall");
        return false;
    }
}
