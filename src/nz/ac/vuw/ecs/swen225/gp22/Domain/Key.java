package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.awt.Color;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;

public class Key implements Item {
    private LayeredTexture texture;
    private Color color;

    public Key(Color color) {
        this.color = color;
    }
    
    public LayeredTexture texture() {
        return texture;
    }

    public Color color() {
        return color;
    }

    public void pickUp(Model m) {
        Player p = m.player();
        int keyCountBefore = p.keys().size();
        p.keys().add(this);
        int keyCountAfter = p.keys().size();
        assert keyCountBefore == keyCountAfter-1;
    }
}
