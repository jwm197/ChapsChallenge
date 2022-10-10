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
        Direction.UP, Animations.PlayerMoveUp,
        Direction.RIGHT, Animations.PlayerMoveRight,
        Direction.DOWN, Animations.PlayerMoveDown,
        Direction.LEFT, Animations.PlayerMoveLeft
    );
    private Map<Direction, LayeredTexture> playerTextures = Map.of(
        Direction.NONE, Textures.PlayerFaceDown,
        Direction.UP, Textures.PlayerFaceUp,
        Direction.RIGHT, Textures.PlayerFaceRight,
        Direction.DOWN, Textures.PlayerFaceDown,
        Direction.LEFT, Textures.PlayerFaceLeft
    );
    private LayeredTexture texture;
    private IntPoint location;
    private Direction direction = Direction.NONE;
    private List<Key> keys = new ArrayList<>();
    private Boolean locked = false;
    private int id;

    public Player(IntPoint location, int id) {
        this.location = location;
        this.id = id;
        texture = playerTextures.get(direction);
    }

    public LayeredTexture texture() {
        return texture;
    }

    public IntPoint location() {
        return location;
    }

    public int id() {
        return id;
    }
    
    public List<Key> keys() {
        return keys;
    }

    public Boolean locked() {
        return locked;
    }

    public void move(Direction d, Model m) {
        if (locked) return;
        if (d == Direction.NONE) return;

        direction = d;
        texture = playerTextures.get(direction);
        
        IntPoint newPos = location.add(d.direction());
        if (newPos.x()<0 || newPos.x()>=m.tiles().width()
        || newPos.y()<0 || newPos.y()>=m.tiles().height()) return;

        Tile t = m.tiles().getTile(newPos);
        if (!t.canPlayerMoveTo(m)) return;

        locked = true;
        if (t instanceof WallTile) t.playerMovedTo(m);
        m.animator().Animate(this, playerAnimations.get(direction), newPos, 20, () -> {
            if (!(t instanceof WallTile)) t.playerMovedTo(m);
            location = newPos;
            locked = false;
        });
    }

    public void tick(Model m) {}
}
