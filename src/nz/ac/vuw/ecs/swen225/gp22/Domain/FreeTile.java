package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;

/**
 * Represents a free tile in the game.
 * 
 * @author sidoroyuri
 * 
 */
public class FreeTile implements Tile {
    private LayeredTexture texture = Textures.Floor; // Free tile texture
    private IntPoint location; // Position of the free tile in the game
    private Item item; // Item on the free tile

    /**
     * Construct a free tile with a given position and item.
     * 
     * @param location The position of the free tile.
     * @param item The item on the free tile.
     */
    public FreeTile(IntPoint location, Item item) {
        this.location = location;
        this.item = item;
    }

    @Override
    public LayeredTexture texture() {
        return item == null ? texture : texture.stack(item.texture()); // If the free tile has an item, return the item texture as well
    }

    @Override
    public IntPoint location() {
        return location;
    }
    
    /**
     * Get the item on the free tile.
     * 
     * @return The item on the free tile.
     */
    public Item item() {
        return item;
    }

    @Override
    public void playerMovedTo(Model m) {
        if (item == null) { return; }
        item.pickUp(m);
        item = null;
    }

    @Override
    public Boolean canPlayerMoveTo(Model m) {
        return true;
    }
}
