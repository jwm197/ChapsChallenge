package nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting;

import java.util.List;

public class Level {
	private Map m;
	public Map getM() {
		return m;
	}
	public Entity getPlayer() {
		return dummy;
	}

	public Animator getAnimator() {
		return animator;
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public void setAnimator(Animator animator) {
		this.animator = animator;
	}
	private DummyPlayer dummy;
	private Animator animator;
	private List<Entity> entities;
	
	Level(Map m, DummyPlayer dummy, Animator animator) {
		this.m = m;
		this.dummy = dummy;
		this.entities = List.of(dummy);
		this.animator = animator;
	}
	public void tick() {
		dummy.moveRight(animator);
	}
}