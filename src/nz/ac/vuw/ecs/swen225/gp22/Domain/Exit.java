package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;

public class Exit extends FreeTile {
    private LayeredTexture texture = Textures.Scrungle;

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
