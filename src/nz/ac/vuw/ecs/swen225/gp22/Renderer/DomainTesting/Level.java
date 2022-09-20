package nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting;

public class Level {
	private Map m;
	public Map getM() {
		return m;
	}
	public DummyPlayer getDummy() {
		return dummy;
	}

	public Animator getAnimator() {
		return animator;
	}
	public void setAnimator(Animator animator) {
		this.animator = animator;
	}
	private DummyPlayer dummy;
	private Animator animator;
	
	Level(Map m, DummyPlayer dummy, Animator animator) {
		this.m = m;
		this.dummy = dummy;
		this.animator = animator;
	}
	public void tick() {
		dummy.moveRight(animator);
		System.out.print(dummy.getPos());
	}
}