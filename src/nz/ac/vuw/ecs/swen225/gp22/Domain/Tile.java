package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;

/**
 * An empty interface capturing the main aspects of a tile.
 * 
 * @author Yuri Sidorov (300567814)
 * 
 */
public interface Tile {
    /**
     * Get the tile's texture.
     * 
     * @return The tile's texture.
     */
    LayeredTexture texture();

    /**
     * Get the tile's position.
     * 
     * @return The tile's position.
     */
    IntPoint location();

    /**
     * Perform actions specific to the tile when the player moves to it.
     * 
     * @param m The model the tile is a part of.
     */
    void playerMovedTo(Model m);

    /**
     * Determine if the player can move to the tile.
     * 
     * @param m The model the tile is a part of.
     * @return True if the player can move to the tile and false if the player can't.
     */
    Boolean canPlayerMoveTo(Model m);
}
