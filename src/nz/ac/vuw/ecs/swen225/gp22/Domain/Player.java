package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Animations;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.TextureSequence;

public class Player implements Entity {
    private Map<Direction, TextureSequence> playerAnimations = Map.of(
        Direction.UP, Textures.MissingTexture,
        Direction.RIGHT, Animations.PlayerMoveRight,
        Direction.DOWN, Textures.MissingTexture,
        Direction.LEFT, Animations.PlayerMoveLeft
    );
    private Map<Direction, LayeredTexture> playerTextures = Map.of(
        Direction.NONE, Textures.MissingTexture,
        Direction.UP, Textures.MissingTexture,
        Direction.RIGHT, Textures.PlayerFaceRight,
        Direction.DOWN, Textures.MissingTexture,
        Direction.LEFT, Textures.PlayerFaceLeft
    );
    private LayeredTexture texture;
    private IntPoint location;
    private Direction direction = Direction.NONE;
    private List<Key> keys = new ArrayList<>();
    private Boolean locked = false;

    public Player(IntPoint location) {
        this.location = location;
        texture = playerTextures.get(direction);
    }

    public LayeredTexture texture() {
        return texture;
    }

    public IntPoint location() {
        return location;
    }
    
    public List<Key> keys() {
        return keys;
    }

    public void movePlayer(Direction d, Model m, Runnable r) {
        if (d == Direction.NONE) return;

        direction = d;
        texture = playerTextures.get(direction);

        if (locked) return;
        
        IntPoint newPos = location.add(d.direction());
        if (newPos.x()<0 || newPos.x()>=m.tiles().width()
        || newPos.y()<0 || newPos.y()>=m.tiles().height()) return;

        Tile t = m.tiles().getTile(newPos);
        if (!t.canPlayerMoveTo(m)) return;

        locked = true;
        t.playerMovedTo(m);
        m.animator().Animate(this, playerAnimations.get(direction), newPos, 20, () -> {
            location = newPos;
            locked = false;
            r.run();
        });
    }

    public void ping(Model m) {}
}
