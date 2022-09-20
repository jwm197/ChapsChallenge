package nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.Texture;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.Textures;

public interface Entity {
	Position<Integer> getPos();
	Boolean locked();
	LayeredTexture texture();
}

class DummyPlayer implements Entity {
	public static Texture test = Textures.Scrungle;
	private Position<Integer> pos;
	private boolean locked = false;
	DummyPlayer(Position<Integer> pos) {
		this.pos = pos;
	}
	public Position<Integer> getPos() {
		return pos;
	}
	public void moveRight(Animator e) {
		if (locked) return;
		locked = true;
		Position<Integer> newPos = new Position<Integer>(pos.x()+1, pos.y());
		
		e.Animate(this, test, newPos, 60, () -> {
			pos = newPos;
			locked = false;
		});
	}
	public Boolean locked() {
		return locked;
	}
	public LayeredTexture texture() {
		return Textures.Scrungle;
	}
}