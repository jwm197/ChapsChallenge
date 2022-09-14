package nz.ac.vuw.ecs.swen225.gp22.Rendering;

abstract class Entity {
	int x, y;
}

class DummyPlayer extends Entity {
	private Img defaultImage = Img.Debug;
	private ImgSequence testAnimation = ImgSequence.DebugSequence;
	
	DummyPlayer(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Img getImage() {
		return defaultImage;
	}
}