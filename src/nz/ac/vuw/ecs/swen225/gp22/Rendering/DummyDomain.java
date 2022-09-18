package nz.ac.vuw.ecs.swen225.gp22.Rendering;

import nz.ac.vuw.ecs.swen225.gp22.Rendering.TextureHandling.TextureSequence;

interface Animator {
	void queueMovingImgSequence(Entity entity, TextureSequence images, Position initialPos, Position finalPos, int tickLength, int frameTicks, Runnable onCompletion);
}

class Level {
	Map m;
	DummyPlayer dummy;
	
	Level() {
		m = new Map(12, 12);
		dummy = new DummyPlayer(0,0);
	}
	
	
}
