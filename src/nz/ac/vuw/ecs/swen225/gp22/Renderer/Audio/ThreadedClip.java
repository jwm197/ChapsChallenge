package nz.ac.vuw.ecs.swen225.gp22.Renderer.Audio;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ThreadedClip implements Playable {
	private Clip inner;
	private boolean looping;
	private Runnable onClose = () -> {};
	
	public ThreadedClip(InputStream source) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(source);
		inner = AudioSystem.getClip();
		inner.open(audioStream);
		inner.addLineListener((event) -> {
			if (event.getType() == LineEvent.Type.STOP && inner.getFramePosition() == inner.getFrameLength()) {
				close();
				onClose.run();
			}
		});
	}

	@Override
	public void play() {
		inner.loop(looping ? -1 : 0);
		inner.start();
		
		new ArrayList<String>(List.of(""));
		
	}

	@Override
	public void pause() {
		inner.stop();
	}

	@Override
	public void close() {
		inner.close();
	}
	
	@Override
	public void setVolume(float volume) {
		FloatControl control = (FloatControl) inner.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(Playable.normalizeVolume(volume));
	}
	
	@Override
	public void setLooping(boolean looping) {
		this.looping = looping;
		inner.loop(looping ? -1 : 0);
	}
	
	@Override
	public void setLoopCount(int loopCount) {
		inner.loop(loopCount);
	}

	@Override
	public void bindOnClose(Runnable onClose) {
		this.onClose = onClose;
	}
}