package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;

/**
 * Represents a wall tile in the game.
 * 
 * @author Yuri Sidorov (300567814)
 * 
 */
public class WallTile implements Tile {
    private LayeredTexture texture = Textures.Wall; // Wall tile texture
    private IntPoint location; // Position of the wall tile in the game

    /**
     * Construct a wall tile with a given position.
     * 
     * @param location The position of the wall tile.
     */
    public WallTile(IntPoint location) {
        this.location = location;
    }

    @Override
    public LayeredTexture texture() {
        return texture;
    }

    @Override
    public IntPoint location() {
        return location;
    }

    @Override
    public void playerMovedTo(Model m) {
        throw new IllegalStateException("Player can't move to a wall tile");
    }

    @Override
    public Boolean canPlayerMoveTo(Model m) {
        return false;
    }
}
