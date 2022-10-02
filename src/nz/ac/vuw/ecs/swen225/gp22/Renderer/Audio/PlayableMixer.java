package nz.ac.vuw.ecs.swen225.gp22.Renderer.Audio;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PlayableMixer {
	private Set<Playable> sounds = ConcurrentHashMap.<Playable>newKeySet();
	
	public void add(Playable p) {
		add(p, true);
	}
	
	public void add(Playable p, boolean autoPlay) {
		sounds.add(p);
		p.bindOnClose(() -> {
			sounds.remove(p);
		});
		if (autoPlay) p.play();
	}
	
	public void closeAll() {
		sounds.forEach(p -> p.close());
	}
}
