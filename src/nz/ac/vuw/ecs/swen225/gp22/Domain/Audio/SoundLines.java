package nz.ac.vuw.ecs.swen225.gp22.Domain.Audio;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Enum representing a collection of generatable audio lines
 * 
 * When requested, audio clips are generated from the file paths
 * 
 * @author anfri
 */
public enum SoundLines {
	Music,
	Music2;
	
	public static final String LINE_PATH = "assets/audio/";
	
	//path to given audio
	private final File file;
	
	/**
	 * Constructor
	 */
	SoundLines() {
		//attempt to read the image from file and throw a blocking exception on fail
		file = new File(LINE_PATH+this.name()+".wav");
	}
	
	/**
	 * Generates a new ThreadedLine Playable
	 * 
	 * @return ThreadedLine wrapped by Playable interface
	 */
	public Playable generate() {
		try {
			return new ThreadedLine(file);
		} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
			throw new Error(e);
		}
	}

}
