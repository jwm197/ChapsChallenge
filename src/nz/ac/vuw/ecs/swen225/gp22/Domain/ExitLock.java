package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;

public class ExitLock extends WallTile {
    private LayeredTexture texture;

    public ExitLock(IntPoint location) {
        super(location);
    }

    public LayeredTexture texture() {
        return texture;
    }

    public void playerMovedTo(Model m) {
        if (canPlayerMoveTo(m)) {
            m.tiles().setTile(location(), new FreeTile(location(), null));
        }
    }

    public Boolean canPlayerMoveTo(Model m) {
        if (m.treasureCount() > 0) {
            throw new Error("Player can only move once all of the treasure has been picked up");
        }
        return true; 
    }
}
