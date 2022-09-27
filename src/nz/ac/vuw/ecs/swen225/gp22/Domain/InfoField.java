package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;

public class InfoField extends FreeTile {
    private LayeredTexture texture;
    private String info;

    public InfoField(IntPoint location, String info) {
        super(location, null);
        this.info = info;
    }

    public LayeredTexture texture() {
        return texture;
    }

    public String info() {
        return info;
    }
}
