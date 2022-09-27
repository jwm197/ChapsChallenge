package nz.ac.vuw.ecs.swen225.gp22.Renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.function.Supplier;

import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp22.Domain.DoublePoint;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Entity;
import nz.ac.vuw.ecs.swen225.gp22.Domain.IntPoint;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Model;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;

/**
 * Implements a JPanel renderer which handles drawing the game state to the screen
 * 
 * @author anfri
 */
public class RenderPanel extends JPanel implements Renderer {
	private static final long serialVersionUID = 1L;
	
	//drawing consts
	public static final int TEXTURE_SIZE = 8;
	public static final int RENDER_SCALE = 8;
	public static final int TILE_PAD = 2;
	
	//default handler implementations
	private AnimationHandler animationHandler = new AnimationHandler();
	private Drawable entityHandler = (g, r) -> {};
	private Drawable tileHandler = (g, r) -> {};
	
	//default camera supplier
	Supplier<DoublePoint> camera = () -> new DoublePoint(0.0,0.0);
	DoublePoint cameraPos;
	
	/**
	 * Constructor
	 */
	public RenderPanel() {
		super();
	}
	
	/**
	 * Binds the renderer to a given domain object
	 * 
	 * @param level domain level object to be bound to the renderer
	 */
	public void bind(Model level) {
		//clears residual animations
		animationHandler.clear();
		
		//constructs the entity handler
		entityHandler = (g, r) ->
		         level.entities().stream() //get the entity stream from the level
				                    .filter(e -> !animationHandler.animating(e)) //if not animating
				                    .forEach(e -> r.drawTexture(g, e.texture(), e.location().toDoublePoint())); //draw the entity at its current position
		
		//constructs the tile handler
		tileHandler = (g, r) -> {
			//calculate the xRad and yRad (how many tiles to draw from the center of the screen)
			Dimension d = this.getSize();
			int xRad = (d.width/(TEXTURE_SIZE*RENDER_SCALE*2))+TILE_PAD;
			int yRad = (d.height/(TEXTURE_SIZE*RENDER_SCALE*2))+TILE_PAD;
			
			//get the center tile position
			IntPoint camRounded = cameraPos.toIntPoint();
			
			//draw the rough radius of tiles to the screen
			level.tiles().forEach(camRounded, xRad, yRad, t -> r.drawTexture(g, t.texture(), t.location().toDoublePoint()));
		};
		
		//bind the level to the animationHandler to allow the level to queue animations
		level.bindAnimator(animationHandler);
		
		//constructs the camera object
		camera = () -> {
			Entity player = level.player();
			
			//get the position of the player entity depending on whether it's animating or static
			return animationHandler.animating(player) ? animationHandler.get(player).position() : player.location().toDoublePoint();
		};
		
		//update cameraPos to fix flicker
		cameraPos = camera.get();
	}
	
	/**
	 * Update the state of the RenderPanel
	 */
	public void tick() {
		animationHandler.tick();  //update current animations
		cameraPos = camera.get(); //update camera position
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//draw the tiles, then entities, then animating entities
		tileHandler.draw(g, this);
		entityHandler.draw(g, this);
		animationHandler.draw(g, this);
	}

	@Override
	public void drawTexture(Graphics g, LayeredTexture texture, DoublePoint position) {
		Dimension d = this.getSize();
		
		//get the corner coordinates for the textures
		int w1 = (int)((position.x()-cameraPos.x())*TEXTURE_SIZE*RENDER_SCALE+(d.width-TEXTURE_SIZE*RENDER_SCALE)/2.0);
		int h1 = (int)((position.y()-cameraPos.y())*TEXTURE_SIZE*RENDER_SCALE+(d.height-TEXTURE_SIZE*RENDER_SCALE)/2.0);
		
		int w2 = w1 + TEXTURE_SIZE*RENDER_SCALE;
		int h2 = h1 + TEXTURE_SIZE*RENDER_SCALE;
		
		//if the texture coordinates are out of bounds, don't draw
		if (h2 <= 0 || w2 <= 0 || h1 >= d.height || w1 >= d.width) return;
		
		//for each texture in the layered texture, draw to screen
		texture.forEach(l -> g.drawImage(
			l.getTexture(),w2,h2,w1,h1,TEXTURE_SIZE,TEXTURE_SIZE,0,0,null
		));
	}
}
