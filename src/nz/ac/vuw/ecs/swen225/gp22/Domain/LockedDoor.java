package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.awt.Color;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.Playable;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.SoundClips;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTextures;

/**
 * Represents a locked door tile in the game.
 * 
 * @author Yuri Sidorov (300567814)
 * 
 */
public class LockedDoor extends WallTile {
    private LayeredTexture texture = LayeredTextures.Lock; // Locked door tile texture
    private Color color; // Color of the lock

    /**
     * Construct a locked door tile with a given position and lock color.
     * 
     * @param location The position of the locked door tile.
     * @param color The color of the lock.
     */
    public LockedDoor(IntPoint location, Color color) {
        super(location);
        this.color = color;
    }

    @Override
    public LayeredTexture texture() {
        return texture.tint(color);
    }

    /**
     * Get the color of the lock.
     * 
     * @return The color of the lock.
     */
    public Color color() {
        return color;
    }

    @Override
    public void playerMovedTo(Model m) {
        Player p = m.player();
        int sizeBefore = p.keys().size();
        m.tiles().setTile(location(), new FreeTile(location(), null)); // Replace the locked door tile with an empty free tile
        Key key = p.keys().stream().filter(k -> k.color().equals(color)).findFirst().get(); // Get a key that's the same colour as the lock from the player
        assert (key.color().equals(color)):"Color of the key doesn't match the color of the lock";
        Playable doorUnlockSound = SoundClips.DoorUnlock.generate(); // Sound of unlocking the door
        doorUnlockSound.play();
        p.keys().remove(key); // Remove the key from the player's inventory
        int sizeAfter = p.keys().size();
        assert (sizeBefore == sizeAfter+1):"Player should have one less key in his inventory after unlocking a door";
    }

    @Override
    public Boolean canPlayerMoveTo(Model m) {
        Player p = m.player();
        if (p.keys().stream().filter(k -> k.color().equals(color)).toList().isEmpty()) {
            return false; // Player can't unlock the locked door if he doesn't have a key that's the same color as the lock
        }
        return true;
    }
}
