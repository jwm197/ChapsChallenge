package nz.ac.vuw.ecs.swen225.gp22.Rendering.TextureHandling;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

public interface LayeredTexture extends Iterable<Texture>, Tintable<LayeredTexture> {
	List<Texture> layers();
	default Iterator<Texture> iterator() { return layers().iterator(); }
	default Texture top() { return layers().get(0); }
	default Texture bottom() { return layers().get(layers().size()-1); }
	
	default LayeredTexture tint(Color tint) { return tint(tint, DEFAULT_TINT_METHOD); }
	default LayeredTexture tint(Color tint, BiFunction<List<Texture>, Color, List<Texture>> f) { return tint(this, tint, f); }
	
	private static LayeredTexture tint(LayeredTexture source, Color tint, BiFunction<List<Texture>, Color, List<Texture>> f) {
		return new LayeredTexture() {
			private List<Texture> layers = f.apply(source.layers(), tint);
			
			public LayeredTexture tint(Color tint, BiFunction<List<Texture>, Color, List<Texture>> f) {
				return LayeredTexture.tint(this, tint, f);
			}
			
			public LayeredTexture tint(Color tint) {
				return tint(tint, DEFAULT_TINT_METHOD);
			}
			
			public List<Texture> layers() {
				return layers;
			}
		};
	}
	
	static final BiFunction<List<Texture>, Color, List<Texture>> DEFAULT_TINT_METHOD = (l, c) -> l.stream().map(e -> e.tint(c)).toList();
}
