package nz.ac.vuw.ecs.swen225.gp22.Domain.Textures;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Enum representing the base Texture objects used to build complex images and animations
 * 
 * @author anfri
 */
public enum Textures implements Texture {
	/*tiles*/
    Floor,
    Wall,
    LockFrame,
    LockFill,
    TreasureLockOverlay,
    ExitOverlay,
    
    /*items*/
    Key,
    Note,
    Treasure,
    
    /*player*/
    PlayerFaceLeft,
    PlayerFaceRight,
    
    /*player animation*/
    PlayerMoveLeft1,
    PlayerMoveLeft2,
    PlayerMoveRight1,
    PlayerMoveRight2,
    
    /*null*/
    MissingTexture,
	Scrungle;
	
	public static final String IMAGE_PATH = "assets/textures/";
	private final BufferedImage image;
	
	/**
	 * Constructor to load images from file
	 */
	Textures() {
		//attempt to read the image from file and throw a blocking exception on fail
		try {
			image = ImageIO.read(new File(IMAGE_PATH+this.name()+".png"));
		} catch (IOException e) {
			throw new Error(e);
		}
	}
	
	@Override
	public BufferedImage getTexture() {
		return image;
	}
}