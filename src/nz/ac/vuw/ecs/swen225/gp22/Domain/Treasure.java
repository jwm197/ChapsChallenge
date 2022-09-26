package nz.ac.vuw.ecs.swen225.gp22.Domain;

public class Treasure implements Item {
    private Point location;

    public Treasure(Point location) {
        this.location = location;
    }
    
    public Point location() {
        return location;
    }
}
