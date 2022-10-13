package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.Playable;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.SoundClips;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Animations;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.TextureSequence;

/**
 * Represents a player entity in the game.
 *
 * @author Yuri Sidorov (300567814)
 *
 */
public class Player implements Entity {
    // Map of player animations based on direction
    private final Map<Direction, TextureSequence> playerAnimations = Map.of(
        Direction.UP, Animations.PlayerMoveUp,
        Direction.RIGHT, Animations.PlayerMoveRight,
        Direction.DOWN, Animations.PlayerMoveDown,
        Direction.LEFT, Animations.PlayerMoveLeft
    );
    // Map of player textures based on direction
    private final Map<Direction, LayeredTexture> playerTextures = Map.of(
        Direction.NONE, Textures.PlayerFaceDown,
        Direction.UP, Textures.PlayerFaceUp,
        Direction.RIGHT, Textures.PlayerFaceRight,
        Direction.DOWN, Textures.PlayerFaceDown,
        Direction.LEFT, Textures.PlayerFaceLeft
    );
    private LayeredTexture texture; // Player texture
    private IntPoint location; // Position of the player in the game
    private Direction direction = Direction.NONE; // Direction the player is facing
    private List<Key> keys = new ArrayList<>(); // Keys the player is holding
    private Boolean locked = false; // Determines if the player can move or not
    private Boolean isDead = false; // Determines if the player is dead or not

    /**
     * Construct a player entity with a given starting position.
     *
     * @param location The starting position.
     */
    public Player(IntPoint location) {
        this.location = location;
        texture = playerTextures.get(direction);
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
     * Get the player's keys.
     *
     * @return List of the player's keys.
     */
    public List<Key> keys() {
        return keys;
    }

    /**
     * Determine if the player can move or not.
     *
     * @return True if the player can't move and false if the player can move.
     */
    public Boolean locked() {
        return locked;
    }

    /**
     * Lock or unlock player movement.
     *
     * @param locked True to lock player movement or false to unlock.
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * Determine if the player is dead or not.
     *
     * @return True if the player is dead and false if he isn't.
     */
    public Boolean isDead() {
        return isDead;
    }

    /**
     * Set the player to be dead or not.
     *
     * @param isDead True to have the player be dead and false to not.
     */
    public void setIsDead(Boolean isDead) {
        this.isDead = isDead;
    }

    @Override
    public void move(Direction d, Model m) {
        if (locked) return; // Don't move if the player is not allowed to move
        if (d == Direction.NONE) return; // Return immediately if no direction is given

        direction = d;
        texture = playerTextures.get(direction);

        IntPoint newPos = location.add(d.direction());
        assert (location.distance(newPos).size() == 1):"Player can only move one tile away";

        // Don't move if the new position is out of bounds
        if (newPos.x()<0 || newPos.x()>=m.tiles().width()
        || newPos.y()<0 || newPos.y()>=m.tiles().height()) return;

        // Don't move if there's a bug at the new position
        if (!m.entities().values().stream().filter(e->!(e instanceof Player) && newPos.equals(e.location()))
        .findFirst().isEmpty()) return;

        Tile t = m.tiles().getTile(newPos);
        if (!t.canPlayerMoveTo(m)) return; // Don't move if the player is not allowed to move to the tile at the new position

        locked = true; // Prevent the player from making a new move whilst it's in the process of moving
        if (t instanceof WallTile) t.playerMovedTo(m); // Unlockable wall tiles get unlocked before the player moves to them
        m.mixer().add(SoundClips.PlayerMove.generate()); // Plays the moving player sound and adds it to the model's mixer

        // Animate the player movement
        m.animator().Animate(this, playerAnimations.get(direction), newPos, 20, () -> {
            if (!(t instanceof WallTile)) t.playerMovedTo(m); // Items get picked up after the player has finished moving
            location = newPos;
            assert (m.tiles().getTile(location) instanceof FreeTile):"Player can't stand on a tile that doesn't extend free tile";
            locked = false;
        });
    }

    @Override
    public void tick(Model m) {}
}
