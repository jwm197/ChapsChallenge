package nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Interface representing an object which can be tinted with a given color
 * 
 * @author anfri
 *
 * @param <R> resulting tinted type
 */
interface Tintable<R> {
	/**
	 * Tint the object with a given color
	 * 
	 * @param tint color
	 * @return tinted object
	 */
	R tint(Color tint);
	
	/**
	 * Static method to tint a given buffered image with a given tint colour
	 * 
	 * @param source source image to tint
	 * @param tint colour to tint with
	 * @return the new tinted image
	 */
	static BufferedImage tintBufferedImage(BufferedImage source, Color tint) {
		//generate a new empty image for output
		BufferedImage tinted = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		
		//for each pixel in the source image
		for (int x = 0; x < source.getWidth(); x++) {
			for (int y = 0; y < source.getHeight(); y++) {
				//get the source colour of the pixel
				Color c = new Color(source.getRGB(x, y), true);
				
				//combine the colours together using sqrt((a^2 + b^2)/2)
				int redSquared = (c.getRed()*c.getRed()+tint.getRed()*tint.getRed())/2;
				int greenSquared = (c.getGreen()*c.getGreen()+tint.getGreen()*tint.getGreen())/2;
				int blueSquared = (c.getBlue()*c.getBlue()+tint.getBlue()*tint.getBlue())/2;
				
				//preserve alpha
				int alpha = c.getAlpha();
				
				//combine the resulting colours together using logical bitshift and or
				int combinedComponent = (alpha << 24)
						               |((int)Math.sqrt(redSquared) << 16)
						               | ((int)Math.sqrt(greenSquared) << 8)
						               | (int)Math.sqrt(blueSquared);
				
				//set the colour in the output to the new combined colour
				tinted.setRGB(x, y, combinedComponent);
			}
		}
		return tinted;
	}
}
