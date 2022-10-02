package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;

public class Bug implements Entity {
    private LayeredTexture texture = Textures.Scrungle;
    private IntPoint location;

    public Bug(IntPoint location) {
        this.location=location;
    }

    public LayeredTexture texture() {
        return texture;
    }

    public IntPoint location() {
        return location;
    }
}
