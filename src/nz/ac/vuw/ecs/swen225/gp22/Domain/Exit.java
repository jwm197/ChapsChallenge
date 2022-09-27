package nz.ac.vuw.ecs.swen225.gp22.Domain;

public class Exit extends FreeTile {
    private LayeredTexture texture;

    public Exit(Point location) {
        super(location, null);
    }

    public LayeredTexture texture() {
        return texture;
    }

    public void playerMovedTo(Model m) {
        if (canPlayerMoveTo(m)) {
            m.onNextLevel();
        }
    }
}
