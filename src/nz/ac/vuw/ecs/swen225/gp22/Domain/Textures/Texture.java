package nz.ac.vuw.ecs.swen225.gp22.Domain.Textures;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Interface representing a simple BufferedImage texture
 * 
 * Note that this interface implements LayeredTexture and by proxy TextureSequence,
 * as this interface is able to act as a single layer LayeredTexture or a single framed TextureSequence.
 * 
 * @author anfri
 */
public interface Texture extends LayeredTexture {
	/**
	 * Gets the internal BufferedImage
	 * 
	 * @return internal BufferedImage
	 */
	BufferedImage getTexture();
	
	@Override
	default Texture top() {return this; }
	
	@Override
	default Texture bottom() {return this; }
	
	@Override
	default int layerCount() { return 1; }
	
	@Override
	default Texture tint(Color c) { return () -> Tintable.tintBufferedImage(getTexture(), c); }
	
	@Override
	default List<Texture> layers() { return List.of(this); }
}
