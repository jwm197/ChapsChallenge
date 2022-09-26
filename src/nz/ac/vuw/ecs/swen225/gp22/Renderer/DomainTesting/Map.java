package nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.Textures;

public class Map {//FIXME: replace during merge week
	private List<List<Tile>> tiles;
	Map(int xSize, int ySize) {
		tiles = new ArrayList<>();
		for (int i = 0; i < xSize; i++) {
			var tmp = new ArrayList<Tile>();
			tiles.add(tmp);
			for (int j = 0; j < ySize; j++) {
				tmp.add(new Tile(new Position<Integer>(i,j), Textures.Scrungle));
			}
		}
	}
	
	public void forEach(Position<Integer> center, int wRad, int hRad, Consumer<Tile> command) {
		IntStream.range(center.x()-wRad, center.x()+wRad)
				 .filter(x -> x >= 0 && x < tiles.size())
				 .forEach(x ->
				     IntStream.range(center.y()-hRad, center.y()+hRad)
				     		  .filter(y -> y >= 0 && y < tiles.get(x).size())
				     		  .forEach(y -> command.accept(tiles.get(x).get(y)))
				 );
	}
}
