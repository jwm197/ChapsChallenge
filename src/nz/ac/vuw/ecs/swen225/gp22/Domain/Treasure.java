package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;

public class Treasure implements Item {
    private LayeredTexture texture;
    
    public LayeredTexture texture() {
        return texture;
    }

    public void pickUp(Model m) {
        int treasureCountBefore = m.treasureCount();
        m.decreaseTreasureCount();
        int treasureCountAfter = m.treasureCount();
        assert treasureCountBefore == treasureCountAfter+1;
        assert treasureCountAfter >= 0;
    }
}
