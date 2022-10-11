package nz.ac.vuw.ecs.swen225.gp22.Domain;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.Random;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Animations;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.Textures;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.TextureSequence;

public class Bug implements Entity {
    private Map<Direction, TextureSequence> bugAnimations = Map.of(
        Direction.UP, Animations.BugMoveUp,
        Direction.RIGHT, Animations.BugMoveRight,
        Direction.DOWN, Animations.BugMoveDown,
        Direction.LEFT, Animations.BugMoveLeft
    );
    private Map<Direction, LayeredTexture> bugTextures = Map.of(
        Direction.NONE, Textures.BugFaceDown,
        Direction.UP, Textures.BugFaceUp,
        Direction.RIGHT, Textures.BugFaceRight,
        Direction.DOWN, Textures.BugFaceDown,
        Direction.LEFT, Textures.BugFaceLeft
    );
    private LayeredTexture texture;
    private IntPoint location;
    private Direction direction = Direction.NONE;
    private Boolean locked = false;

    public Bug(IntPoint location) {
        this.location = location;
        texture = bugTextures.get(direction);
    }

    public LayeredTexture texture() {
        return texture;
    }

    public IntPoint location() {
        return location;
    }

    public Direction calculateDirection() {
        NavigableMap<Integer, Direction> directions = new TreeMap<>();
        int cumulative = 0;
        directions.put(cumulative += (direction.probUp()*100), Direction.UP);
        directions.put(cumulative += (direction.probRight()*100), Direction.RIGHT);
        directions.put(cumulative += (direction.probDown()*100), Direction.DOWN);
        directions.put(cumulative += (direction.probLeft()*100), Direction.LEFT);

        Random randomNum = new Random();
        return directions.higherEntry(randomNum.nextInt(cumulative)).getValue();
    }

    public void move(Direction d, Model m) {
        if (locked) return;

        direction = d;
        texture = bugTextures.get(direction);

        IntPoint newPos = location.add(direction.direction());
        if (newPos.x()<0 || newPos.x()>=m.tiles().width()
        || newPos.y()<0 || newPos.y()>=m.tiles().height()) return;
        if (!m.entities().values().stream().filter(e->!(e instanceof Player || e==this) && location.equals(e.location()))
        .findFirst().isEmpty()) return;

        Tile t = m.tiles().getTile(newPos);
        if (t instanceof WallTile) return;

        locked = true;
        m.animator().Animate(this, bugAnimations.get(direction), newPos, 30, () -> {
            locked = false;
            if (location.equals(m.player().location())) m.onGameOver();
        });
        location = newPos;
    }

    public void tick(Model m) {
        move(calculateDirection(), m);
        
    }
}
