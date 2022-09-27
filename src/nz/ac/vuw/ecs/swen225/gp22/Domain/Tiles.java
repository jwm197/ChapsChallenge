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

    public Tile getTile(Point p) {
        return tiles.get(p.x()).get(p.y());
    }

    public void setTile(Point p, Tile t) {
        tiles.get(p.x()).set(p.y(),t);
    }
}
