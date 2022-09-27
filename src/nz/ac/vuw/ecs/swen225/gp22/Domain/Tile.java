package nz.ac.vuw.ecs.swen225.gp22.Domain;

public interface Tile {
    LayeredTexture texture();
    Point location();
    void playerMovedTo(Model m);
    Boolean canPlayerMoveTo(Model m);
}
