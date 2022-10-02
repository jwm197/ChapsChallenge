package nz.ac.vuw.ecs.swen225.gp22.Domain.Textures;

import java.awt.Color;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

/**
 * Enum representing the LayeredTexture constants
 * 
 * @author anfri
 */
public enum LayeredTextures implements LayeredTexture {
	//frames are ordered from bottom to top
	/*tiles*/
    Exit(Textures.Floor, Textures.ExitOverlay),
    TreasureLock(Textures.Wall, Textures.TreasureLockOverlay),
    Lock(1, Textures.Wall, Textures.LockFill, Textures.LockFrame);
	
	protected final List<Texture> layers;
	protected final BiFunction<List<Texture>, Color, List<Texture>> tintStrategy;
	
	/**
	 * Constructor
	 * 
	 * @param layers layers of the texture
	 */
	LayeredTextures(Texture... layers) {
		this.layers = List.of(layers);
		this.tintStrategy = LayeredTexture.DEFAULT_TINT_METHOD;
	}
	
	/**
	 * Constructor with specified tint layer
	 * 
	 * @param tintIndex index to apply tint to
	 * @param layers layers of the texture
	 */
	LayeredTextures(int tintIndex, Texture... layers) {
		this.layers = List.of(layers);
		this.tintStrategy = (lt, c) -> tintLayer(lt, c, tintIndex);
	}
	
	@Override
	public List<Texture> layers() {
		return layers;
	}
	
	@Override
	public LayeredTexture tint(Color tint) {
		return tint(tint, tintStrategy);
	}
	
	/**
	 * Tint a specific layer of a List<Texture>
	 * 
	 * @param lt input list of textures
	 * @param c colour to tint the texture
	 * @param layer specific layer to be tinted
	 * @return new List<Texture> with the specific layer tinted
	 */
	static final List<Texture> tintLayer(List<Texture> lt, Color c, int layer) {
		return IntStream.range(0, lt.size()) //IntStream over length
				        .mapToObj(i -> i == layer ? lt.get(i): lt.get(i).tint(c)) //map to tinted if index matches
				        .toList(); //condense to list
	}
}