package nz.ac.vuw.ecs.swen225.gp22.Renderer.Audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ThreadedLine implements Playable {
	public static final int BUFFER_SIZE = 2048;
	
	private SourceDataLine inner;
	private AudioInputStream stream;
	
	private int loopCount;
	
	private byte[] buffer;
	private int count;
	
	
	Thread current;
	private boolean isActive = false;

	private LineListener listener;

	private Runnable onClose = () -> {};
	
	public ThreadedLine(File source) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		listener = (event) -> {
			if (event.getType() == LineEvent.Type.STOP) {
				close();
				if (loopCount != 0) {
					try {
						constructStream(source);
					} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
						throw new Error(e);
					}
					play();
					if (loopCount != -1) loopCount--;
				} else {
					onClose.run();
				}
			}
		};
		constructStream(source);
	}
	
	private void constructStream(File source) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		stream = AudioSystem.getAudioInputStream(source);
		AudioFormat audioFormat = stream.getFormat();
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		inner = (SourceDataLine) AudioSystem.getLine(info);
		inner.open(audioFormat);
		buffer = new byte[BUFFER_SIZE];
		count = 0;
		inner.addLineListener(listener);
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	private synchronized void writeBuffer() {
	    inner.write(buffer, 0, count);
	    try {
			while ((count = stream.read(buffer, 0, BUFFER_SIZE)) != -1 && isActive) {
				inner.write(buffer, 0, count);
			}
		} catch (IOException e) {
		} finally {
			if (isActive) {
				inner.drain();
				inner.close();
			}
		}
	}

	@Override
	public void play() {
		inner.start();
		
		if (!isActive) {
			current = new Thread(Thread.currentThread().getThreadGroup(), () -> {
				writeBuffer();
			});
			current.start();
		}
		isActive = true;
	}

	@Override
	public void pause() {
		isActive = false;
		inner.stop();
	}
	
	@Override
	public void close() {
		isActive = false;
		inner.close();
	}

	@Override
	public void setVolume(float volume) {
		FloatControl control = (FloatControl) inner.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(Playable.normalizeVolume(volume));
	}

	@Override
	public void setLooping(boolean looping) {
		this.loopCount = looping ? -1 : 0;
	}

	@Override
	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}

	@Override
	public void bindOnClose(Runnable onClose) {
		this.onClose  = onClose;
	}
}