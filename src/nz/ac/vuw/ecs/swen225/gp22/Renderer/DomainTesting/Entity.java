package nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.Texture;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.Textures;

interface Entity {
	Position getPos();
}

class DummyPlayer implements Entity {
	public static Texture test = Textures.Scrungle;
	private Position pos;
	DummyPlayer(Position pos) {
		this.pos = pos;
	}
	public Position getPos() {
		return pos;
	}
	public void moveLeft(Animator e) {
		Position newPos = new Position(pos.x(), pos.y());
		e.AnimateMoving(null, test, newPos, 60, () -> {
			pos = newPos;
		});
	}
}