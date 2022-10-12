package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.awt.Color;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;

public class Key implements Item {
    private LayeredTexture texture = Textures.Key;
    private Color color;

    public Key(Color color) {
        this.color = color;
    }
    
    public LayeredTexture texture() {
        return texture.tint(color);
    }

    public Color color() {
        return color;
    }

    public void pickUp(Model m) {
        Player p = m.player();
        int playerKeyCountBefore = p.keys().size();
        int modelKeyCountBefore = m.keys().size();
        p.keys().add(this);
        m.keys().remove(this);
        int playerKeyCountAfter = p.keys().size();
        int modelKeyCountAfter = m.keys().size();
        assert playerKeyCountBefore == playerKeyCountAfter-1;
        assert modelKeyCountBefore == modelKeyCountAfter+1;
    }
}
