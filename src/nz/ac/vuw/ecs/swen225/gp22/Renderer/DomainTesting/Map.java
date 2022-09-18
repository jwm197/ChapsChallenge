package nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting;

import java.util.ArrayList;
import java.util.List;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.Textures;

class Map {//FIXME: replace during merge week
	List<List<Tile>> tiles;
	Map(int xSize, int ySize) {
		tiles = new ArrayList<>();
		for (int i = 0; i < xSize; i++) {
			var tmp = new ArrayList<Tile>();
			tiles.add(tmp);
			for (int j = 0; j < ySize; j++) {
				tmp.add(new Tile(i, j, Textures.Scrungle));
			}
		}
	}
	
}
