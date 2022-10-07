package nz.ac.vuw.ecs.swen225.gp22.Renderer.Audio;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An auto-closing Playable handler which terminates audio threads automagically on completion
 * 
 * @author anfri
 */
public class AudioMixer {
	//threaded hashset is used to avoid issues with concurrent removal and addition
	private Set<Playable> sounds = ConcurrentHashMap.<Playable>newKeySet();
	
	/**
	 * Adds the given Playable to the player with autoplay
	 * 
	 * @param p audio to be played
	 */
	public void add(Playable p) {
		add(p, true);
	}
	
	/**
	 * Adds the given Playable to the mixer
	 * 
	 * @param p audio to be played
	 * @param autoPlay whether to immediately start playing the audio
	 */
	public void add(Playable p, boolean autoPlay) {
		sounds.add(p);
		//bind the closure to remove the audio file concurrently from the hashset
		p.bindOnClose(() -> {
			sounds.remove(p);
		});
		if (autoPlay) p.play();
	}
	
	/**
	 * Sets all queued Playables to play
	 */
	public void playAll() {
		sounds.forEach(p -> p.play());
	}
	
	/**
	 * Sets all queued Playables to pause
	 */
	public void pauseAll() {
		sounds.forEach(p -> p.pause());
	}
	
	/**
	 * Removes all playables from the mixer
	 */
	public void closeAll() {
		sounds.forEach(p -> p.close());
	}
}
