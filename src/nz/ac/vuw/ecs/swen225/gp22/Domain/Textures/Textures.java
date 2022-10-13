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
    PlayerFaceUp,
    PlayerFaceDown,
    
    /*player animation*/
    PlayerMoveLeft1,
    PlayerMoveLeft2,
    PlayerMoveRight1,
    PlayerMoveRight2,
    PlayerMoveUp1,
    PlayerMoveUp2,
    PlayerMoveDown1,
    PlayerMoveDown2,
    
    /*bug*/
    BugFaceLeft,
    BugFaceRight,
    BugFaceUp,
    BugFaceDown,
    
    /*bug animation*/
    BugMoveLeft1,
    BugMoveLeft2,
    BugMoveRight1,
    BugMoveRight2,
    BugMoveUp1,
    BugMoveUp2,
    BugMoveDown1,
    BugMoveDown2,
    
    /*null*/
    MissingTexture;
	
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
			System.out.println(this.name());
			throw new Error(e);
		}
	}
	
	@Override
	public BufferedImage getTexture() {
		return image;
	}
}
