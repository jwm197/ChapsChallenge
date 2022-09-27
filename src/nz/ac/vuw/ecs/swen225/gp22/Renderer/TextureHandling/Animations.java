package nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling;

import java.awt.Color;
import java.util.List;

public enum Animations implements TextureSequence {
	RainbowScrungle(Textures.Scrungle.tint(Color.red),Textures.Scrungle.tint(Color.green),Textures.Scrungle.tint(Color.blue));
	
	private final List<LayeredTexture> frames;
	Animations(LayeredTexture... frames) {
		this.frames = List.of(frames);
	}
	
	@Override
	public List<LayeredTexture> frames() {
		return frames;
	}

}