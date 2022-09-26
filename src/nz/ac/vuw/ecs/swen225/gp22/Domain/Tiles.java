package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;

public class Tiles {
    private List<List<Tile>> tiles;
    
    public Tiles(List<List<Tile>> tiles) {
        this.tiles = tiles;
    }

    public List<List<Tile>> tiles() {
        return tiles;
    }
}
