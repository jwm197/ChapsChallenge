package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTextures;

/**
 * Represents an exit tile in the game.
 * 
 * @author Yuri Sidorov (300567814)
 * 
 */
public class Exit extends FreeTile {
    private LayeredTexture texture = LayeredTextures.Exit; // Exit tile texture

    /**
     * Construct an exit tile with a given position.
     * 
     * @param location The position of the exit tile.
     */
    public Exit(IntPoint location) {
        super(location, null);
    }

    @Override
    public LayeredTexture texture() {
        return texture;
    }

    @Override
    public void playerMovedTo(Model m) {
        m.onNextLevel();
    }
}
