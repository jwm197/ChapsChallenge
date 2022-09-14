package nz.ac.vuw.ecs.swen225.gp22.Rendering;

import java.util.ArrayList;
import java.util.List;

class Map {//FIXME: replace during merge week
	List<List<Tile>> tiles;
	Map(int xSize, int ySize) {
		tiles = new ArrayList<>();
		for (int i = 0; i < xSize; i++) {
			var tmp = new ArrayList<Tile>();
			tiles.add(tmp);
			for (int j = 0; j < ySize; j++) {
				tmp.add(new Tile(i, j, Img.Debug4));
			}
		}
	}
	
}
