package nz.ac.vuw.ecs.swen225.gp22.Domain;

public class FreeTile implements Tile {
    private LayeredTexture texture;
    private Point location;
    private Item item;

    public FreeTile(Point location, Item item) {
        this.location = location;
        this.item = item;
    }

    public LayeredTexture texture() {
        return texture;
    }

    public Point location() {
        return location;
    }
    
    public Item item() {
        return item;
    }

    public void playerMovedTo(Model m) {
        if (canPlayerMoveTo(m)) {
            if (item == null) { return; }
            item.pickUp(m);
            item = null;
        }
    }

    public Boolean canPlayerMoveTo(Model m) {
        return true;
    }
}
