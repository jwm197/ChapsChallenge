package nz.ac.vuw.ecs.swen225.gp22.Rendering.TextureHandling;

import java.util.List;

public enum LayeredTextures implements LayeredTexture {
	ScrungleLayered(Textures.Scrungle);
	
	private final List<Texture> layers;
	LayeredTextures(Texture... layers) {
		this.layers = List.of(layers);
	}

	public List<Texture> layers() {
		return layers;
	}
}