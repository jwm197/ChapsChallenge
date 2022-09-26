package nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling;

import java.awt.Color;
import java.util.List;
import java.util.stream.IntStream;

public enum LayeredTextures implements LayeredTexture {
	//ordered bottom to top
	ScrungleLayered(Textures.Scrungle, Textures.Scrungle.tint(Color.red));
	
	protected final List<Texture> layers;
	LayeredTextures(Texture... layers) {
		this.layers = List.of(layers);
	}

	public List<Texture> layers() {
		return layers;
	}
	
	static final List<Texture> tintLayer(List<Texture> lt, Color c, int layer) {
		return IntStream.range(0, lt.size()).mapToObj(i -> i == layer ? lt.get(i): lt.get(i).tint(c)).toList();
	}
}