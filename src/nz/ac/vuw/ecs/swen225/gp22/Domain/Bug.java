package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;

public class Bug implements Entity {
    private LayeredTexture texture;
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
