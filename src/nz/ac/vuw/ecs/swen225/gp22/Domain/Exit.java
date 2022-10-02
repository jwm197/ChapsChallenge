package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTextures;

public class Exit extends FreeTile {
    private LayeredTexture texture = LayeredTextures.Exit;

    public Exit(IntPoint location) {
        super(location, null);
    }

    public LayeredTexture texture() {
        return texture;
    }

    public void playerMovedTo(Model m) {
        m.onNextLevel();
    }
}
