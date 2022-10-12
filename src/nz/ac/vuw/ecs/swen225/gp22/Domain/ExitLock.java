package nz.ac.vuw.ecs.swen225.gp22.Domain;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.Playable;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.SoundClips;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTextures;

/**
 * Represents an exit lock tile in the game.
 * 
 * @author sidoroyuri
 * 
 */
public class ExitLock extends WallTile {
    private LayeredTexture texture = LayeredTextures.TreasureLock; // Exit lock tile texture

    /**
     * Construct an exit lock tile with a given position.
     * 
     * @param location The position of the exit lock tile.
     */
    public ExitLock(IntPoint location) {
        super(location);
    }

    @Override
    public LayeredTexture texture() {
        return texture;
    }

    @Override
    public void playerMovedTo(Model m) {
        Playable doorUnlockSound = SoundClips.DoorUnlock.generate(); // Sound of unlocking the exit lock
        doorUnlockSound.play();
        m.tiles().setTile(location(), new FreeTile(location(), null)); // Replace the exit lock tile with an empty free tile
    }

    @Override
    public Boolean canPlayerMoveTo(Model m) {
        if (m.treasure().size() > 0) {
            // throw new Error("Player can only move once all of the treasure has been picked up");
            return false; // Player can't unlock the exit lock if all of the treasure hasn't been picked up
        }
        return true; 
    }
}
