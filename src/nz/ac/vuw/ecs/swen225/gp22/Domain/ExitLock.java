package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;

public class ExitLock extends WallTile {
    private LayeredTexture texture = Textures.Scrungle;

    public ExitLock(IntPoint location) {
        super(location);
    }

    public LayeredTexture texture() {
        return texture;
    }

    public void playerMovedTo(Model m) {
        m.tiles().setTile(location(), new FreeTile(location(), null));
    }

    public Boolean canPlayerMoveTo(Model m) {
        if (m.treasure().size() > 0) {
            // throw new Error("Player can only move once all of the treasure has been picked up");
            return false;
        }
        return true; 
    }
}
