package nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.Texture;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.Textures;

abstract class Entity {
	int x, y;
}

class DummyPlayer extends Entity {
	public static Texture test = Textures.Scrungle;
	DummyPlayer(int x, int y) {
		this.x = x;
		this.y = y;
	}
}