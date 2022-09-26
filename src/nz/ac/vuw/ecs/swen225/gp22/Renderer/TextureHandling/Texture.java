package nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

public interface Texture extends LayeredTexture {
	BufferedImage getTexture();
	
	default Texture top() {return this; }
	default Texture bottom() {return this; }
	default int layerCount() { return 1; }
	
	@Override
	default Texture tint(Color c) { return () -> Tintable.tintBufferedImage(getTexture(), c); }
	
	default List<Texture> layers() { return List.of(this); }
}
