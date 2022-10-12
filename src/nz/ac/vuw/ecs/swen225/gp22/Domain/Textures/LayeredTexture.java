package nz.ac.vuw.ecs.swen225.gp22.Domain.Textures;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * Interface representing a Texture with multiple layers
 * 
 * Note that LayeredTextures are by inheritance also TextureSequences consisting of a single frame
 * 
 * @author anfri
 */
public interface LayeredTexture extends Iterable<Texture>, Tintable<LayeredTexture>, TextureSequence {
	List<Texture> layers();
	/**
	 * Get the top texture of the layered texture
	 * 
	 * @return top texture
	 */
	default Texture top() { return layers().get(layers().size()-1); }
	
	/**
	 * Get the bottom texture of the layered texture
	 * 
	 * @return bottom texture
	 */
	default Texture bottom() { return layers().get(0); }
	
	/**
	 * Get the number of layers in the texture
	 * 
	 * @return layer count
	 */
	default int layerCount() { return layers().size(); }
	
	@Override
	default Iterator<Texture> iterator() { return layers().iterator(); }
	
	@Override
	default List<LayeredTexture> frames() { return List.of(this); }
	
	@Override
	default LayeredTexture first() { return this; }
	
	@Override
	default LayeredTexture last() { return this; }
	
	@Override
	default int frameCount() { return 1; }
	
	@Override
	default LayeredTexture tint(Color tint) { return tint(this, tint, DEFAULT_TINT_METHOD); }
	
	/**
	 * Tints the texture with a given procedure 
	 * 
	 * @param tint colour to tint with
	 * @param f function to process the layers
	 * @return a new LayeredTexture representing the tinted object
	 */
	default LayeredTexture tint(Color tint, BiFunction<List<Texture>, Color, List<Texture>> f) { return tint(this, tint, f); }
	
	/**
	 * Static method to tint a given layeredTexture with a given strategy
	 * 
	 * @param source source texture to tint
	 * @param tint tint to be applied
	 * @param f function to process the layers
	 * @return a new LayeredTexture representing the tinted object
	 */
	private static LayeredTexture tint(LayeredTexture source, Color tint, BiFunction<List<Texture>, Color, List<Texture>> f) {
		return new LayeredTexture() {
			private List<Texture> layers = f.apply(source.layers(), tint);
			public List<Texture> layers() { return layers; }
		};
	}
	
	//default strategy for tinting layers (applies tint to all layers)
	static final BiFunction<List<Texture>, Color, List<Texture>> DEFAULT_TINT_METHOD = (l, c) -> l.stream().map(e -> e.tint(c)).toList();

	/**
     * Stacks one LayeredTexture on top of another
     * 
     * @param other the other texture to be stacked on top
     * @return a new layered texture backed by the combined textures
     */
    default LayeredTexture stack(LayeredTexture other) {
        return () -> Stream.concat(this.layers().stream(), other.layers().stream()).toList();
    }
}
