package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;

public class FreeTile implements Tile {
    private LayeredTexture texture = Textures.Floor;
    private IntPoint location;
    private Item item;

    public FreeTile(IntPoint location, Item item) {
        this.location = location;
        this.item = item;
    }

    public LayeredTexture texture() {
        return item == null ? texture : texture.stack(item.texture());
    }

    public IntPoint location() {
        return location;
    }
    
    public Item item() {
        return item;
    }

    public void playerMovedTo(Model m) {
        if (item == null) { return; }
        item.pickUp(m);
        item = null;
    }

    public Boolean canPlayerMoveTo(Model m) {
        return true;
    }
}
