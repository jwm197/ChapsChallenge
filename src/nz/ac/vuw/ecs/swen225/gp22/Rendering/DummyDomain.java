package nz.ac.vuw.ecs.swen225.gp22.Rendering;

class Level {
	Map m;
	DummyPlayer dummy;
	
	Level() {
		m = new Map(12, 12);
		dummy = new DummyPlayer(0,0);
	}
}
