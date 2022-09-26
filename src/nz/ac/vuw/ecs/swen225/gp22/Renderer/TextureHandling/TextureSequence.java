package nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling;

import java.util.List;

public interface TextureSequence {
	List<LayeredTexture> frames();
	
	default LayeredTexture first() {
		return frames().get(0);
	}
	
	default LayeredTexture last() {
		return frames().get(frames().size()-1);
	}
	
	default LayeredTexture get(int index) {
		return frames().get(index);
	}
	
	default int frameCount() {
		return frames().size();
	}
}