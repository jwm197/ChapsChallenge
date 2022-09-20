package nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

public interface LayeredTexture extends Iterable<Texture>, Tintable<LayeredTexture>, TextureSequence {
	List<Texture> layers();
	default Texture top() { return layers().get(layers().size()-1); }
	default Texture bottom() { return layers().get(0); }
	default int layerCount() { return layers().size(); }
	
	default Iterator<Texture> iterator() { return layers().iterator(); }
	
	default List<LayeredTexture> frames() { return List.of(this); }
	default LayeredTexture first() { return this; }
	default LayeredTexture last() { return this; }
	default int frameCount() { return 1; }
	
	default LayeredTexture tint(Color tint) { return tint(tint, DEFAULT_TINT_METHOD); }
	default LayeredTexture tint(Color tint, BiFunction<List<Texture>, Color, List<Texture>> f) { return tint(this, tint, f); }
	
	private static LayeredTexture tint(LayeredTexture source, Color tint, BiFunction<List<Texture>, Color, List<Texture>> f) {
		return new LayeredTexture() {
			private List<Texture> layers = f.apply(source.layers(), tint);
			public List<Texture> layers() { return layers; }
		};
	}
	
	static final BiFunction<List<Texture>, Color, List<Texture>> DEFAULT_TINT_METHOD = (l, c) -> l.stream().map(e -> e.tint(c)).toList();
}
