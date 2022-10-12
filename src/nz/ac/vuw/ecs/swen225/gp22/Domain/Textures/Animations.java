package nz.ac.vuw.ecs.swen225.gp22.Domain.Textures;

import java.util.List;
/**
 * Enum containing the TextureSequence constants to be used
 * 
 * @author anfri
 */
public enum Animations implements TextureSequence {
	/*player*/
    PlayerMoveLeft(Textures.PlayerMoveLeft1, Textures.PlayerMoveLeft2),
    PlayerMoveRight(Textures.PlayerMoveRight1, Textures.PlayerMoveRight2),
    PlayerMoveUp(Textures.PlayerMoveUp1, Textures.PlayerMoveUp2),
    PlayerMoveDown(Textures.PlayerMoveDown1, Textures.PlayerMoveDown2),
	
	/*bug*/
    BugMoveLeft(Textures.BugMoveLeft1, Textures.BugMoveLeft2),
    BugMoveRight(Textures.BugMoveRight1, Textures.BugMoveRight2),
    BugMoveUp(Textures.BugMoveUp1, Textures.BugMoveUp2),
    BugMoveDown(Textures.BugMoveDown1, Textures.BugMoveDown2);
	
	private final List<LayeredTexture> frames;
	
	/**
	 * Constructor
	 * 
	 * @param frames frames of the animation
	 */
	Animations(LayeredTexture... frames) {
		this.frames = List.of(frames);
	}
	
	@Override
	public List<LayeredTexture> frames() {
		return frames;
	}

}
