package nz.ac.vuw.ecs.swen225.gp22.Domain.Audio;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * An implementation of Playable utilizing preloaded byte array sources
 * 
 * <p>This implementation is only suitable for short clips due to its large memory footprint.
 * For a more memory-friendly implementation, see ThreadedLine.
 * This implementation also ensures auto-closure on completion to prevent resource leaks<p>
 * 
 * @see ThreadedLine
 * @author anfri
 */
public class ThreadedClip implements Playable {
	//inner audio clip
	private Clip inner;
	private boolean looping;
	
	//runnable for closure
	private Runnable onClose = () -> {};
	
	/**
	 * Constructor
	 * 
	 * @param source buffered stream for sourcing clip data
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 */
	public ThreadedClip(InputStream source) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		//create audio stream
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(source);
		inner = AudioSystem.getClip();
		inner.open(audioStream);
		
		//adds a listener to bind onClose
		inner.addLineListener((event) -> {
			//if audio is stopped on completion
			if (event.getType() == LineEvent.Type.STOP && inner.getFramePosition() == inner.getFrameLength()) {
				close();
				onClose.run();
			}
		});
	}

	@Override
	public Playable play() {
		inner.loop(looping ? -1 : 0);
		inner.start();
		return this;
	}

	@Override
	public Playable pause() {
		inner.stop();
		return this;
	}

	@Override
	public void close() {
		inner.close();
	}
	
	@Override
	public Playable setVolume(float volume) {
		FloatControl control = (FloatControl) inner.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(Playable.normalizeVolume(volume));
        return this;
	}
	
	@Override
	public Playable setLooping(boolean looping) {
		this.looping = looping;
		inner.loop(looping ? -1 : 0);
		return this;
	}
	
	@Override
	public Playable setLoopCount(int loopCount) {
		inner.loop(loopCount);
		return this;
	}

	@Override
	public void bindOnClose(Runnable onClose) {
		this.onClose = onClose;
	}
}
