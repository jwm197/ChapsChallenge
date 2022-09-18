package nz.ac.vuw.ecs.swen225.gp22.Renderer.TextureHandling;

import java.util.Iterator;
import java.util.List;

public interface TextureSequence extends Iterable<LayeredTexture> {
	List<LayeredTexture> frames();
	
	default LayeredTexture first() {
		return frames().get(0);
	}
	
	default LayeredTexture last() {
		return frames().get(frames().size()-1);
	}
	
	default Iterator<LayeredTexture> iterator() {
		return frames().iterator();
	}
}