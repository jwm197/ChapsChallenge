package nz.ac.vuw.ecs.swen225.gp22.Renderer.Audio;
public interface Playable {
	void play();
	void pause();
	void close();
	void setLooping(boolean looping);
	void setLoopCount(int loopCount);
	void setVolume(float volume);
	
	void bindOnClose(Runnable onClose);
	
	static float normalizeVolume(float volume) {
		return 20 * (float) Math.log10(volume / 100);
	}
}
