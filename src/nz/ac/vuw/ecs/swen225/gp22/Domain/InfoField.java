package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;

public class InfoField extends FreeTile {
    private LayeredTexture texture = Textures.Note;
    private String info;

    public InfoField(IntPoint location, String info) {
        super(location, null);
        this.info = info;
    }

    public LayeredTexture texture() {
        return super.texture().stack(texture);
    }

    public String info() {
        return info;
    }
}
