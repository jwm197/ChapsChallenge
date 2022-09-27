package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;

public class Exit extends FreeTile {
    private LayeredTexture texture;

    public Exit(IntPoint location) {
        super(location, null);
    }

    public LayeredTexture texture() {
        return texture;
    }

    public void playerMovedTo(Model m) {
        if (canPlayerMoveTo(m)) {
            m.onNextLevel();
        }
    }
}
