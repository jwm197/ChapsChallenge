package nz.ac.vuw.ecs.swen225.gp22.Renderer;

import java.awt.Graphics;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting.Level;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.DomainTesting.Position;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling.LayeredTexture;

public class RenderPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int TILE_SCALE = 8;
	
	public RenderPanel(Level debugLevel) {
		debugLevel.setAnimator(null);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
