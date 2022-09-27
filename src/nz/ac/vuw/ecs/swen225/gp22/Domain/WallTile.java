package nz.ac.vuw.ecs.swen225.gp22.Domain;

public class WallTile {
    private LayeredTexture texture;
    private Point location;

    public WallTile(Point location) {
        this.location = location;
    }

    public LayeredTexture texture() {
        return texture;
    }

    public Point location() {
        return location;
    }

    public void playerMovedTo(Model m) {
        if (canPlayerMoveTo(m)) {
            return;
        }
    }

    public Boolean canPlayerMoveTo(Model m) {
        throw new Error("Player can't move to wall");
    }
}
