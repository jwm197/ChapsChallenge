package nz.ac.vuw.ecs.swen225.gp22.Rendering.TextureHandling;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum Textures implements Texture {
	Scrungle;

	private final BufferedImage image;
	public static final String IMAGE_PATH = "assets/textures/";
	Textures() {
		try {
			image = ImageIO.read(new File(IMAGE_PATH+this.name()+".png"));
		} catch (IOException e) {
			throw new Error(e);
		}
	}
	
	public BufferedImage getTexture() {
		return image;
	}
}