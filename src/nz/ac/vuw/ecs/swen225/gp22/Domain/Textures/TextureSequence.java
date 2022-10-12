package nz.ac.vuw.ecs.swen225.gp22.Domain.Textures;

import java.util.List;

/**
 * Interface representing a sequence of frames, useful for representing animations
 * 
 * @author anfri
 */
public interface TextureSequence {
	/**
	 * Gets the internal List<LayeredTexture>
	 * 
	 * @return list of textures
	 */
	List<LayeredTexture> frames();
	
	/**
	 * Gets the first frame of the animation
	 * 
	 * @return first frame
	 */
	default LayeredTexture first() {
		return frames().get(0);
	}
	
	/**
	 * Gets the last frame of the animation
	 * 
	 * @return last frame
	 */
	default LayeredTexture last() {
		return frames().get(frames().size()-1);
	}
	
	/**
	 * Gets a frame from the given index
	 * 
	 * @param index frame index
	 * @return the given frame from the index
	 */
	default LayeredTexture get(int index) {
		return frames().get(index);
	}
	
	/**
	 * Gets the number of frames in the animation
	 * 
	 * @return frame count
	 */
	default int frameCount() {
		return frames().size();
	}
}