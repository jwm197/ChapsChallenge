package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class Tiles {
    private List<List<Tile>> tiles;
    
    public Tiles(List<List<Tile>> tiles) {
        this.tiles = tiles;
    }

    public List<List<Tile>> tiles() {
        return tiles;
    }

    public Tile getTile(IntPoint p) {
        return tiles.get(p.x()).get(p.y());
    }

    public void setTile(IntPoint p, Tile t) {
        tiles.get(p.x()).set(p.y(),t);
    }
    
    public void forEach(IntPoint center, int wRad, int hRad, Consumer<Tile> command) {
		IntStream.range(center.x()-wRad, center.x()+wRad)
				 .filter(x -> x >= 0 && x < tiles.size())
				 .forEach(x ->
				     IntStream.range(center.y()-hRad, center.y()+hRad)
				     		  .filter(y -> y >= 0 && y < tiles.get(x).size())
				     		  .forEach(y -> command.accept(tiles.get(x).get(y)))
				 );
	}
}
