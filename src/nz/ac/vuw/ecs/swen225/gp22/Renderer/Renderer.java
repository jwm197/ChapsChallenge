package nz.ac.vuw.ecs.swen225.gp22.Renderer;

import java.awt.Graphics;

import nz.ac.vuw.ecs.swen225.gp22.Domain.DoublePoint;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;

//FIXME: required? we only have a single renderer?
/**
 * Interface representing a renderer able to draw textures to a given graphics context
 *
 * @author anfri
 */
interface Renderer {
	/**
	 * Draw a given texture to the screen from a given worldspace
	 * 
	 * @param g graphics context
	 * @param texture texture to be drawn
	 * @param position worldspace position to draw texture to
	 */
	public void drawTexture(Graphics g, LayeredTexture texture, DoublePoint position);
}