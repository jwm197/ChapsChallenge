package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;

public interface Tile {
    LayeredTexture texture();
    IntPoint location();
    void playerMovedTo(Model m);
    Boolean canPlayerMoveTo(Model m);
}
