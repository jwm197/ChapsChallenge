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

/**
 * An implementation of Playable utilizing rolling buffer file loading
 * 
 * <p>This implementation is suitable for longer audio sources due to lower memory footprint.
 * This implementation is more expensive than ThreadedClip due to the constant memory reads, which results in
 * worse performance for shorter clips. For this case, ThreadedClip is recommended
 * This implementation also ensures auto-closure on completion to prevent resource leaks<p>
 * 
 * @see ThreadedLine
 * @author anfri
 */
public class ThreadedLine implements Playable {
	//size of data buffer
	public static final int BUFFER_SIZE = 2048;
	
	private SourceDataLine inner;
	private AudioInputStream stream;
	
	private int loopCount;
	
	private byte[] buffer;
	private int count;
	
	//thread for concurrent audio execution
	Thread current;
	private boolean isActive = false;
	private LineListener listener;
	
	//onclose callback
	private Runnable onClose = () -> {};
	
	/**
	 * Constructor
	 * 
	 * @param source File to load audio from
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 */
	public ThreadedLine(File source) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		
		//generate the listener
		listener = (event) -> {
			
			//if the audio is stopped
			if (event.getType() == LineEvent.Type.STOP) {
				close();
				
				//if the audio is looping, create a new audio stream thread, else call the callback
				if (loopCount != 0) {
					try {
						//construct a new audio thread
						constructStream(source);
					} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
						//if we reach here, something has gone horribly wrong
					}
					play();
					if (loopCount != -1) loopCount--;
				} else {
					onClose.run();
				}
			}
		};
		
		//construct the audio thread
		constructStream(source);
	}
	
	/**
	 * Construct a new audio stream to populate the buffer
	 * 
	 * @param source File to be loaded
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	private void constructStream(File source) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		//load stream from file
		stream = AudioSystem.getAudioInputStream(source);
		
		//create the inner line
		AudioFormat audioFormat = stream.getFormat();
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		inner = (SourceDataLine) AudioSystem.getLine(info);
		
		//open the line and set up params
		inner.open(audioFormat);
		buffer = new byte[BUFFER_SIZE];
		count = 0;
		inner.addLineListener(listener);
	}
	
	/**
	 * Get the activity state of the internal thread
	 * 
	 * @return whether the thread is active
	 */
	public boolean isActive() {
		return isActive;
	}
	
	
	/**
	 * Synchronized method to write new data from buffer
	 */
	private synchronized void writeBuffer() {
		//FIXME: is this required?
		//write residual data to buffer (if paused during a write cycle)
	    inner.write(buffer, 0, count);
	    
	    try {
	    	//whilst there is still more data to write, write to buffer
			while ((count = stream.read(buffer, 0, BUFFER_SIZE)) != -1 && isActive) {
				inner.write(buffer, 0, count);
			}
		} catch (IOException e) {
		} finally {
			//cleanup on completion/error
			if (isActive) {
				inner.drain();
				inner.close();
			}
		}
	}

	@Override
	public void play() {
		inner.start();
		
		//if the audio is inactive, generate a new thread to handle writing data to the audio buffer
		if (!isActive) {
			//generate new thread
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