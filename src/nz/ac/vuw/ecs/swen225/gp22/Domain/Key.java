package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.awt.Color;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.Playable;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.SoundClips;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;

/**
 * Represents a key item in the game.
 * 
 * @author Yuri Sidorov (300567814)
 * 
 */
public class Key implements Item {
    private LayeredTexture texture = Textures.Key; // Key texture
    private Color color; // Color of the key

    /**
     * Construct a key item with a given color.
     * 
     * @param color The color of the key.
     */
    public Key(Color color) {
        this.color = color;
    }
    
    @Override
    public LayeredTexture texture() {
        return texture.tint(color); // Return the key texture tinted with the provided color
    }

    /**
     * Get the color of the key.
     * 
     * @return The color of the key.
     */
    public Color color() {
        return color;
    }

    @Override
    public void pickUp(Model m) {
        Player p = m.player();
        int playerKeyCountBefore = p.keys().size();
        int modelKeyCountBefore = m.keys().size();
        Playable keyPickupSound = SoundClips.KeyPickup.generate(); // Sound of picking up the key
        keyPickupSound.play();
        p.keys().add(this); // Add the key to the player's inventory
        m.keys().remove(this); // Remove the key from the model
        int playerKeyCountAfter = p.keys().size();
        int modelKeyCountAfter = m.keys().size();
        assert (playerKeyCountBefore == playerKeyCountAfter-1):"Player should have an additional key in his inventory after picking one up";
        assert (modelKeyCountBefore == modelKeyCountAfter+1):"Model should have one less key due to the player picking one up";
    }
}
