package nz.ac.vuw.ecs.swen225.gp22.Domain;

public class Key implements Item {
    private Point location;

    public Key(Point location) {
        this.location = location;
    }
    
    public Point location() {
        return location;
    }
}
