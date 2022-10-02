package nz.ac.vuw.ecs.swen225.gp22.Renderer.Audio;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public enum SoundLines {
	;
	
	public static final String LINE_PATH = "assets/audio/";
	private final File file;
	
	SoundLines() {
		//attempt to read the image from file and throw a blocking exception on fail
		file = new File(LINE_PATH+this.name()+".wav");
	}
	
	public Playable generate() {
		try {
			return new ThreadedLine(file);
		} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
			throw new Error(e);
		}
	}

}
