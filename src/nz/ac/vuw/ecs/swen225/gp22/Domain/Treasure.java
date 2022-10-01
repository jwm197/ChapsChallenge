package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;

public class Treasure implements Item {
    private LayeredTexture texture = Textures.Scrungle;
    
    public LayeredTexture texture() {
        return texture;
    }

    public void pickUp(Model m) {
        int treasureCountBefore = m.treasure().size();
        m.treasure().remove(this);
        int treasureCountAfter = m.treasure().size();
        assert treasureCountBefore == treasureCountAfter+1;
        assert treasureCountAfter >= 0;
    }
}
