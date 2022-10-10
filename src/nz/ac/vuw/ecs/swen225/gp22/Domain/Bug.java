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
        Direction.UP, Textures.MissingTexture,
        Direction.RIGHT, Textures.MissingTexture,
        Direction.DOWN, Textures.MissingTexture,
        Direction.LEFT, Textures.MissingTexture
    );
    private Map<Direction, LayeredTexture> bugTextures = Map.of(
        Direction.NONE, Textures.MissingTexture,
        Direction.UP, Textures.MissingTexture,
        Direction.RIGHT, Textures.MissingTexture,
        Direction.DOWN, Textures.MissingTexture,
        Direction.LEFT, Textures.MissingTexture
    );
    private LayeredTexture texture;
    private IntPoint location;
    private Direction direction = Direction.NONE;
    private Boolean locked = false;
    private int id;

    public Bug(IntPoint location, int id) {
        this.location = location;
        this.id = id;
        texture = bugTextures.get(direction);
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
        if (!m.entities().values().stream().filter(e->!(e instanceof Player) && location.equals(e.location()))
        .findFirst().isEmpty()) return;

        Tile t = m.tiles().getTile(newPos);
        if (t instanceof WallTile) return;

        locked = true;
        m.animator().Animate(this, bugAnimations.get(direction), newPos, 30, () -> {
            location = newPos;
            locked = false;
        });
    }

    public void tick(Model m) {
        move(calculateDirection(), m);
        if (location.equals(m.player().location())) m.onGameOver();
    }
}
