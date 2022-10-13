package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;

/**
 * An empty interface capturing the main aspects of an item.
 * 
 * @author Yuri Sidorov (300567814)
 * 
 */
public interface Item {
    /**
     * Get the item's texture.
     * 
     * @return The item's texture.
     */
    LayeredTexture texture();

    /**
     * Pick up the item.
     * 
     * @param m The model the item is a part of.
     */
    void pickUp(Model m);
}
