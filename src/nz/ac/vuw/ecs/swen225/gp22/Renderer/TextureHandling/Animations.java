package nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling;

import java.util.List;

public enum Animations implements TextureSequence {
	RainbowScrungle(Textures.Scrungle, LayeredTextures.ScrungleLayered.tint(java.awt.Color.red));
	
	private final List<LayeredTexture> frames;
	Animations(LayeredTexture... frames) {
		this.frames = List.of(frames);
	}
	
	@Override
	public List<LayeredTexture> frames() {
		return frames;
	}

}
