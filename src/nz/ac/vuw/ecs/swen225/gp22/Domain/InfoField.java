package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;

/**
 * Represents an info tile in the game.
 * 
 * @author sidoroyuri
 * 
 */
public class InfoField extends FreeTile {
    private LayeredTexture texture = Textures.Note; // Info field texture
    private String info; // Help text

    /**
     * Construct an info tile with a given position and help text.
     * 
     * @param location The position of the info tile.
     * @param info The help text.
     */
    public InfoField(IntPoint location, String info) {
        super(location, null);
        this.info = info;
    }

    @Override
    public LayeredTexture texture() {
        return super.texture().stack(texture); // Return the free tile texture with the info field texture
    }

    /**
     * Get the help text.
     * 
     * @return The help text.
     */
    public String info() {
        return info;
    }
}
