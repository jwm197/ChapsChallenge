package nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling;

import java.awt.Color;
import java.awt.image.BufferedImage;

interface Tintable<R> {
	R tint(Color tint);
	static BufferedImage tintBufferedImage(BufferedImage source, Color tint) {
		BufferedImage tinted = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		for (int x = 0; x < source.getWidth(); x++) {
			for (int y = 0; y < source.getHeight(); y++) {
				Color c = new Color(source.getRGB(x, y), true);
				
				int redSquared = (c.getRed()*c.getRed()+tint.getRed()*tint.getRed())/2;
				int greenSquared = (c.getGreen()*c.getGreen()+tint.getGreen()*tint.getGreen())/2;
				int blueSquared = (c.getBlue()*c.getBlue()+tint.getBlue()*tint.getBlue())/2;
				int alpha = c.getAlpha();
				int combinedComponent = (alpha << 24) | ((int)Math.sqrt(redSquared) << 16) | ((int)Math.sqrt(greenSquared) << 8) | (int)Math.sqrt(blueSquared);
				tinted.setRGB(x, y, combinedComponent);
			}
		}
		return tinted;
	}
}
