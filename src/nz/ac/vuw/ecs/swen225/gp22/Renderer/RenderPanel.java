package nz.ac.vuw.ecs.swen225.gp22.Renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.function.Supplier;

import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting.Entity;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting.Level;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting.Position;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.LayeredTexture;

public class RenderPanel extends JPanel implements Renderer {
	private static final long serialVersionUID = 1L;
	
	public static final int TILE_SCALE = 32;
	public static final int TILE_PAD = 2;
	
	
	
	private final AnimationHandler animationHandler = new AnimationHandler();
	private final Drawable entityHandler;
	private final Drawable tileHandler;
	Supplier<Position<Double>> camera;
	Position<Double> cameraPos;
	
	public RenderPanel(Level debugLevel) {
		
		//FIXME move to method?
		entityHandler = (g, r) -> debugLevel.getEntities()
											.stream()
											.filter(e -> !animationHandler.animating(e))
											.forEach(e -> r.drawTexture(g, e.texture(), e.getPos().doubleValue()));
		
		tileHandler = (g, r) -> {
			Dimension d = this.getSize();
			int xRad = (d.width/(TILE_SCALE*2))+TILE_PAD;
			int yRad = (d.height/(TILE_SCALE*2))+TILE_PAD;
			
			Position<Integer> camRounded = cameraPos.intValue();
			debugLevel.getM().forEach(camRounded, xRad, yRad, t -> r.drawTexture(g, t.texture(), t.pos().doubleValue()));
		};
		
		debugLevel.setAnimator(animationHandler);
		camera = () -> {
			Entity player = debugLevel.getPlayer();
			return animationHandler.animating(player) ? animationHandler.get(player).position() : player.getPos().doubleValue();
		};
		cameraPos = camera.get();
	}
	
	public void tick() {
		animationHandler.tick();
		cameraPos = camera.get();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		tileHandler.draw(g, this);
		entityHandler.draw(g, this);
		animationHandler.draw(g, this);
	}

	@Override
	public void drawTexture(Graphics g, LayeredTexture texture, Position<Double> position) {
		Dimension d = this.getSize();
		
		int w1 = (int)Math.round((position.x()-cameraPos.x())*TILE_SCALE+(d.width-TILE_SCALE)/2.0);
		int h1 = (int)Math.round((position.y()-cameraPos.y())*TILE_SCALE+(d.height-TILE_SCALE)/2.0);
		
		int w2 = w1 + TILE_SCALE;
		int h2 = h1 + TILE_SCALE;
		
		if (h2 <= 0 || w2 <= 0 || h1 >= d.height || w1 >= d.width) return;
		texture.forEach(l -> g.drawImage(
			l.getTexture(),w1,h1,w2,h2,8,8,0,0,null
		));
	}
}
