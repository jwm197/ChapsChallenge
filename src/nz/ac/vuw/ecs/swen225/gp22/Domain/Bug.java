package nz.ac.vuw.ecs.swen225.gp22.Domain;

public class Bug implements Entity {
    private LayeredTexture texture;
    private Point location;

    public Bug(Point location) {
        this.location=location;
    }

    public LayeredTexture texture() {
        return texture;
    }

    public Point location() {
        return location;
    }
}
