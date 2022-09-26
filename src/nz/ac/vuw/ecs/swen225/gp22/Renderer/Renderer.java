package nz.ac.vuw.ecs.swen225.gp22.Renderer;

import java.awt.Graphics;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting.Position;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.LayeredTexture;

interface Renderer {
	public void drawTexture(Graphics g, LayeredTexture texture, Position<Double> position);
}