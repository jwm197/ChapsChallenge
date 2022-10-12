package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * Represents all of the tiles in a level.
 * 
 * @author sidoroyuri
 * 
 */
public class Tiles {
    private List<List<Tile>> tiles; // 2d array of tiles
    private int width; // Width of all the tiles
    private int height; // Height of all the tiles
    
    /**
     * Construct a Tiles object with a given 2d array of tiles as well as its width and height.
     * 
     * @param tiles The 2d array of tiles.
     * @param width Width of all the tiles.
     * @param height Height of all the tiles.
     */
    public Tiles(List<List<Tile>> tiles, int width, int height) {
        this.tiles = tiles;
        this.width = width;
        this.height = height;
    }

    /**
     * Get the 2d array of tiles.
     * 
     * @return The 2d array of tiles.
     */
    public List<List<Tile>> tiles() {
        return tiles;
    }

    /**
     * Get the tile at the given position.
     * 
     * @param p The position of the tile to get.
     * @return The tile at the given position.
     */
    public Tile getTile(IntPoint p) {
        return tiles.get(p.x()).get(p.y());
    }

    /**
     * Set the tile at the given position to be the given tile.
     * 
     * @param p The position of the tile to replace.
     * @param t The tile to replace with.
     */
    public void setTile(IntPoint p, Tile t) {
        tiles.get(p.x()).set(p.y(),t);
    }

    /**
     * Get the width of all the tiles.
     * 
     * @return The width of all the tiles.
     */
    public int width() {
        return width;
    }

    /**
     * Get the height of all the tiles.
     * 
     * @return The height of all the tiles.
     */
    public int height() {
        return height;
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
