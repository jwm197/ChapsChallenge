package nz.ac.vuw.ecs.swen225.gp22.Domain.Audio;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Enum representing a collection of generatable audio clips
 * 
 * When requested, audio clips are generated from the intenal byte arrays
 * 
 * @author anfri
 */
public enum SoundClips {
	;
	
	public static final String CLIP_PATH = "assets/audio/";
	
	//byte array to keep audio persistent
	private final byte[] data;
	
	/**
	 * Constructor
	 */
	SoundClips() {
		//attempt to read the image from file and throw a blocking exception on fail
		try {
			//loads data from file into a byte array
			data = Files.readAllBytes(Paths.get(CLIP_PATH+this.name()+".wav"));
		} catch (IOException e) {
			throw new Error(e);
		}
	}
	
	/**
	 * Generates a new ThreadedClip Playable
	 * 
	 * @return ThreadedClip wrapped by Playable interface
	 */
	public Playable generate() {
		try {
			//creates clip using stream wrapper of byte array
			return new ThreadedClip(new ByteArrayInputStream(data));
		} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
			throw new Error(e);
		}
	}	
}

