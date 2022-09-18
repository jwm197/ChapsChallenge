package nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting;

record Level(Map m, DummyPlayer dummy, Animator animator) {
	
}

class DummyDomain {
	public static void main(String... args) {
		Level l = new Level(new Map(12,12), new DummyPlayer(new Position(0,0)), Animator.NONE);
	}
}