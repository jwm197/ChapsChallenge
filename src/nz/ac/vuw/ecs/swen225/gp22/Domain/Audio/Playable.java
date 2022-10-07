package nz.ac.vuw.ecs.swen225.gp22.Renderer.Audio;

/**
 * Represents a threaded audio object which can be played
 * 
 * @author anfri
 */
public interface Playable {
	/**
	 * Play the audio
	 */
	void play();
	
	/**
	 * Pause the audio
	 * 
	 * <p>If terminating audio permanently, {@link #close() close} should be used.<p>
	 */
	void pause();
	
	/**
	 * Kills the internal audio object
	 * 
	 * <p>Depending on implementation, this method will free the internal iostream resources.
	 * If audio is paused, this method must be called at some point after the audio is no longer required/leaves scope, or a resource leak may occur.
	 */
	void close();
	
	/**
	 * Set the looping status of the audio
	 * 
	 * @param looping whether to loop
	 */
	void setLooping(boolean looping);
	
	/**
	 * Sets the number of loops that should be performed before this audio terminates
	 * @param loopCount
	 */
	void setLoopCount(int loopCount);
	
	/**
	 * Set the volume of the audio (0-100 normalized). Valies over 100 are allowed, however may result in distortion.
	 * @param volume
	 */
	void setVolume(float volume);
	
	/**
	 * Runnable to detect closure utilised by AudioMixer
	 * 
	 * @param onClose closure Runnable
	 * @see AudioMixer
	 */
	void bindOnClose(Runnable onClose);
	
	/**
	 * Normalizes volume from a 0-100 range to a decibel scale
	 * @param non-normalized volume
	 * @return normalized volume
	 */
	static float normalizeVolume(float volume) {
		return 20 * (float) Math.log10(volume / 100);
	}
}
