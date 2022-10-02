package nz.ac.vuw.ecs.swen225.gp22.Renderer.Audio;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public enum SoundClips {
	;
	
	public static final String CLIP_PATH = "assets/audio/";
	private final byte[] data;
	
	SoundClips() {
		//attempt to read the image from file and throw a blocking exception on fail
		try {
			data = Files.readAllBytes(Paths.get(CLIP_PATH+this.name()+".wav"));
		} catch (IOException e) {
			throw new Error(e);
		}
	}
	
	public Playable generate() {
		try {
			return new ThreadedClip(new ByteArrayInputStream(data));
		} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
			throw new Error(e);
		}
	}	
}

