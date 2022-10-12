package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Random;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.Playable;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.SoundClips;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Animations;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.TextureSequence;

/**
 * Represents a bug entity in the game.
 * 
 * @author sidoroyuri
 * 
 */
public class Bug implements Entity {
    // Map of bug animations based on direction
    private final Map<Direction, TextureSequence> bugAnimations = Map.of(
        Direction.UP, Animations.BugMoveUp,
        Direction.RIGHT, Animations.BugMoveRight,
        Direction.DOWN, Animations.BugMoveDown,
        Direction.LEFT, Animations.BugMoveLeft
    );
    // Map of bug textures based on direction
    private final Map<Direction, LayeredTexture> bugTextures = Map.of(
        Direction.NONE, Textures.BugFaceDown,
        Direction.UP, Textures.BugFaceUp,
        Direction.RIGHT, Textures.BugFaceRight,
        Direction.DOWN, Textures.BugFaceDown,
        Direction.LEFT, Textures.BugFaceLeft
    );
    private LayeredTexture texture; // Bug texture
    private IntPoint location; // Position of the bug in the game
    private Direction direction = Direction.NONE; // Direction the bug is facing
    private Boolean locked = false; // Determines if the bug can move or not
    private Random randomNum = new Random(); // Random number generator for random moves

    /**
     * Construct a bug entity with a given starting position.
     * 
     * @param location The starting position.
     */
    public Bug(IntPoint location) {
        this.location = location;
        texture = bugTextures.get(direction);
    }

    @Override
    public LayeredTexture texture() {
        return texture;
    }

    @Override
    public IntPoint location() {
        return location;
    }

    /**
     * Calculate the next direction the bug should move in based on the
     * direction it's currently facing and the random number generator.
     * 
     * @return The next direction the bug should move in.
     */
    public Direction calculateDirection() {
        NavigableMap<Integer, Direction> directions = new TreeMap<>();
        int cumulative = 0;
        directions.put(cumulative += (direction.probUp()*100), Direction.UP);
        directions.put(cumulative += (direction.probRight()*100), Direction.RIGHT);
        directions.put(cumulative += (direction.probDown()*100), Direction.DOWN);
        directions.put(cumulative += (direction.probLeft()*100), Direction.LEFT);

        return directions.higherEntry(randomNum.nextInt(cumulative)).getValue(); // Returns the value corresponding to the first key that's higher than the random number
    }

    @Override
    public void move(Direction d, Model m) {
        if (locked) return; // Don't move if the bug is not allowed to move
        if (d == Direction.NONE) return; // Don't move if a NONE direction is given

        direction = d;
        texture = bugTextures.get(direction);

        IntPoint newPos = location.add(direction.direction());

        // Don't move if the new position is out of bounds
        if (newPos.x()<0 || newPos.x()>=m.tiles().width()
        || newPos.y()<0 || newPos.y()>=m.tiles().height()) return;

        // Don't move if there's a bug at the new position
        if (!m.entities().values().stream().filter(e->!(e instanceof Player || e==this) && newPos.equals(e.location()))
        .findFirst().isEmpty()) return;

        // Prevent the player from moving if the bug is moving to the player's position
        if (newPos.equals(m.player().location())) m.player().setLocked(true);

        Tile t = m.tiles().getTile(newPos);
        if (t instanceof WallTile) return; // Don't move if the tile the bug's moving to is a wall tile

        locked = true; // Prevent the bug from making a new move whilst it's in the process of moving

        // Animate the bug movement
        m.animator().Animate(this, bugAnimations.get(direction), newPos, 20, () -> {
            locked = false;
            if (location.equals(m.player().location())) {
                Playable bugBiteSound = SoundClips.BugBite.generate(); // Sound of the bug eating the player
                bugBiteSound.play();
                m.onGameOver(); // Kill the player if the bug moves to the player's position
            }
        });
        location = newPos;
    }

    @Override
    public void tick(Model m) {
        move(calculateDirection(), m);
    }
}
