package nz.ac.vuw.ecs.swen225.gp22.Domain;

public interface Item {
    LayeredTexture texture();
    void pickUp(Model m);
}
