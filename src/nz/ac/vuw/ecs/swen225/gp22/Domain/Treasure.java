package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.Playable;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.SoundClips;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;

/**
 * Represents a treasure item in the game.
 * 
 * @author sidoroyuri
 * 
 */
public class Treasure implements Item {
    private LayeredTexture texture = Textures.Treasure; // Treasure texture
    
    @Override
    public LayeredTexture texture() {
        return texture;
    }

    @Override
    public void pickUp(Model m) {
        int treasureCountBefore = m.treasure().size();
        Playable treasurePickupSound = SoundClips.TreasurePickup.generate(); // Sound of picking up the treasure
        treasurePickupSound.play();
        m.treasure().remove(this); // Remove the treasure from the model
        int treasureCountAfter = m.treasure().size();
        assert treasureCountBefore == treasureCountAfter+1;
    }
}
