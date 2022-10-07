package nz.ac.vuw.ecs.swen225.gp22.Renderer;

import java.awt.Graphics;

/**
 * Interface representing an object which is able to be drawn to the graphics panel
 *
 * @author anfri
 */
interface Drawable {
	/**
	 * Draw the given object to the graphics context using the provided renderer
	 * 
	 * @param g graphics context
	 * @param r renderer to draw with
	 */
	public void draw(Graphics g, Renderer r);
}
