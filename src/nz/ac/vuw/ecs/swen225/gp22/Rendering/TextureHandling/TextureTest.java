package nz.ac.vuw.ecs.swen225.gp22.Rendering.TextureHandling;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class TextureTest {
	public static void main(String... args) {
		Texture dummy = Textures.Scrungle;
		File f = new File("sbingle.png");
		try {
			ImageIO.write(dummy.getTexture(), "png", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}