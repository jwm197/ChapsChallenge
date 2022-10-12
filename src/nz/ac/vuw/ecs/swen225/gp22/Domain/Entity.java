package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;

public interface Entity {
    LayeredTexture texture();
    IntPoint location();
    void move(Direction d, Model m);
    void tick(Model m);
}
