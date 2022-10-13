package nz.ac.vuw.ecs.swen225.gp22.Domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Audio.AudioMixer;

/**
 * Tests for the domain package.
 * 
 * @author sidoroyuri
 * 
 */
public class DomainTests {
    public static Boolean test = false; // For detecting the execution of the next and gameOver runnables

    /**
     * Level for testing player interactions with the different tiles and items.
     * 
     * @param keyColor The color of the key in the level.
     * @return The test level.
     */
    public Level makeTestLevel1(Color keyColor) {
        Player player = new Player(new IntPoint(2,3));
        Map<Integer, Entity> entities = new HashMap<>();
        entities.put(0,player);
        Key key = new Key(keyColor);
        List<Key> keys = new ArrayList<>();
        keys.add(key);
        Treasure treasure1 = new Treasure();
        Treasure treasure2 = new Treasure();
        List<Treasure> treasure = new ArrayList<>();
        treasure.add(treasure1);
        treasure.add(treasure2);
        List<Tile> col1 = new ArrayList<>();
        col1.add(new WallTile(new IntPoint(0,0)));
        col1.add(new WallTile(new IntPoint(0,1)));
        col1.add(new WallTile(new IntPoint(0,2)));
        col1.add(new LockedDoor(new IntPoint(0,3),Color.BLUE));
        col1.add(new WallTile(new IntPoint(0,4)));
        List<Tile> col2 = new ArrayList<>();
        col2.add(new WallTile(new IntPoint(1,0)));
        col2.add(new WallTile(new IntPoint(1,1)));
        col2.add(new FreeTile(new IntPoint(1,2),null));
        col2.add(new FreeTile(new IntPoint(1,3),null));
        col2.add(new FreeTile(new IntPoint(1,4),key));
        List<Tile> col3 = new ArrayList<>();
        col3.add(new Exit(new IntPoint(2,0)));
        col3.add(new ExitLock(new IntPoint(2,1)));
        col3.add(new InfoField(new IntPoint(2,2),""));
        col3.add(new FreeTile(new IntPoint(2,3),null));
        col3.add(new FreeTile(new IntPoint(2,4),treasure1));
        List<Tile> col4 = new ArrayList<>();
        col4.add(new WallTile(new IntPoint(3,0)));
        col4.add(new WallTile(new IntPoint(3,1)));
        col4.add(new FreeTile(new IntPoint(3,2),null));
        col4.add(new FreeTile(new IntPoint(3,3),treasure2));
        col4.add(new FreeTile(new IntPoint(3,4),null));
        List<List<Tile>> cols = new ArrayList<>();
        cols.add(col1);
        cols.add(col2);
        cols.add(col3);
        cols.add(col4);
        Tiles tiles = new Tiles(cols,4,5);
        Runnable next = ()->{ test = true; };
        Runnable gameOver = ()->{};
        Level level = Level.makeLevel(player, entities, keys, treasure, tiles, next, gameOver);
        level.model().bindAnimator(Animator.NONE);
        level.model().bindMixer(new AudioMixer());
        level.model().setTime(60);
        return level;
    }

    /**
     * Level for testing entity interactions as well as bug interactions with the different tiles
     * and random movement.
     * 
     * @return The test level.
     */
    public Level makeTestLevel2() {
        Player player = new Player(new IntPoint(0,2));
        Bug bug1 = new Bug(new IntPoint(2,0));
        Bug bug2 = new Bug(new IntPoint(3,2));
        Map<Integer, Entity> entities = new HashMap<>();
        entities.put(0,player);
        entities.put(1,bug1);
        entities.put(2,bug2);
        List<Key> keys = new ArrayList<>();
        List<Treasure> treasure = new ArrayList<>();
        List<Tile> col1 = new ArrayList<>();
        col1.add(new WallTile(new IntPoint(0,0)));
        col1.add(new FreeTile(new IntPoint(0,1),null));
        col1.add(new FreeTile(new IntPoint(0,2),null));
        col1.add(new WallTile(new IntPoint(0,3)));
        List<Tile> col2 = new ArrayList<>();
        col2.add(new FreeTile(new IntPoint(1,0),null));
        col2.add(new FreeTile(new IntPoint(1,1),null));
        col2.add(new FreeTile(new IntPoint(1,2),null));
        col2.add(new FreeTile(new IntPoint(1,3),null));
        List<Tile> col3 = new ArrayList<>();
        col3.add(new FreeTile(new IntPoint(2,0),null));
        col3.add(new FreeTile(new IntPoint(2,1),null));
        col3.add(new FreeTile(new IntPoint(2,2),null));
        col3.add(new FreeTile(new IntPoint(2,3),null));
        List<Tile> col4 = new ArrayList<>();
        col4.add(new WallTile(new IntPoint(3,0)));
        col4.add(new FreeTile(new IntPoint(3,1),null));
        col4.add(new FreeTile(new IntPoint(3,2),null));
        col4.add(new WallTile(new IntPoint(3,3)));
        List<List<Tile>> cols = new ArrayList<>();
        cols.add(col1);
        cols.add(col2);
        cols.add(col3);
        cols.add(col4);
        Tiles tiles = new Tiles(cols,4,4);
        Runnable next = ()->{};
        Runnable gameOver = ()->{ test = true; };
        Level level = Level.makeLevel(player, entities, keys, treasure, tiles, next, gameOver);
        level.model().bindAnimator(Animator.NONE);
        level.model().bindMixer(new AudioMixer());
        level.model().setTime(60);
        return level;
    }

    @Test
    /**
     * Valid level.
     */
    public void successfulLevelCreation1() {
        /**Player player = new Player(new IntPoint(0,0));
        Map<Integer, Entity> entities = new HashMap<>();
        entities.put(0,player);
        List<Key> keys = new ArrayList<>();
        List<Treasure> treasure = new ArrayList<>();
        Tile tile = new FreeTile(new IntPoint(0,0),null);
        List<Tile> col = new ArrayList<>();
        col.add(tile);
        List<List<Tile>> rows = new ArrayList<>();
        rows.add(col);
        Tiles tiles = new Tiles(rows,1,1);
        Runnable next = ()->{};
        Runnable gameOver = ()->{};
        try {
            Level level = Level.makeLevel(player,entities,keys,treasure,tiles,next,gameOver);
        } catch (Exception e) {
            fail("Level creation failed");
        }*/
        try {
            makeTestLevel1(Color.BLUE);
        } catch (Exception e) {
            fail("Level creation failed");
        }
    }

    @Test
    /**
     * makeLevel arguments must all not be null.
     */
    public void failedLevelCreation1() {
        assertThrows(IllegalArgumentException.class, ()->{ Level.makeLevel(null,null,null,null,null,null,null); });
    }

    @Test
    /**
     * Player id must be 0.
     */
    public void failedLevelCreation2() {
        Player player = new Player(new IntPoint(0,0));
        Map<Integer, Entity> entities = new HashMap<>();
        entities.put(1,player);
        List<Key> keys = new ArrayList<>();
        List<Treasure> treasure = new ArrayList<>();
        Tile tile = new FreeTile(new IntPoint(0,0),null);
        List<Tile> col = new ArrayList<>();
        col.add(tile);
        List<List<Tile>> cols = new ArrayList<>();
        cols.add(col);
        Tiles tiles = new Tiles(cols,1,1);
        Runnable next = ()->{};
        Runnable gameOver = ()->{};
        assertThrows(IllegalArgumentException.class, ()->{ Level.makeLevel(player,entities,keys,treasure,tiles,next,gameOver); });
    }

    @Test
    /**
     * Player can move to free tile.
     */
    public void validPlayerMovement1() {
        Level level = makeTestLevel1(Color.BLUE);
        Model model = level.model();
        Player player = model.player();
        player.move(Direction.LEFT,model);
        assertEquals(player.location(), new IntPoint(1,3));
    }

    @Test
    /**
     * Player can move to info tile.
     */
    public void validPlayerMovement2() {
        Level level = makeTestLevel1(Color.BLUE);
        Model model = level.model();
        Player player = model.player();
        player.move(Direction.UP,model);
        assertEquals(player.location(), new IntPoint(2,2));
    }

    @Test
    /**
     * Player can move to and pick up treasure.
     */
    public void validPlayerMovement3() {
        Level level = makeTestLevel1(Color.BLUE);
        Model model = level.model();
        Player player = model.player();
        player.move(Direction.RIGHT,model);
        assertEquals(player.location(), new IntPoint(3,3));
        assertEquals(model.treasure().size(), 1);
    }

    @Test
    /**
     * Player can move to and pick up a key.
     */
    public void validPlayerMovement4() {
        Level level = makeTestLevel1(Color.BLUE);
        Model model = level.model();
        Player player = model.player();
        player.move(Direction.LEFT,model);
        player.move(Direction.DOWN,model);
        assertEquals(player.location(), new IntPoint(1,4));
        assertEquals(model.keys().size(), 0);
        assertEquals(player.keys().size(), 1);
    }

    @Test
    /**
     * Player can unlock a locked door with the right colored key.
     */
    public void validPlayerMovement5() {
        Level level = makeTestLevel1(Color.BLUE);
        Model model = level.model();
        Player player = model.player();
        player.move(Direction.LEFT,model);
        player.move(Direction.DOWN,model);
        player.move(Direction.UP,model);
        player.move(Direction.LEFT,model);
        assertEquals(player.location(), new IntPoint(0,3));
        assertEquals(player.keys().size(), 0);
        Tiles tiles = model.tiles();
        assertTrue(tiles.getTile(new IntPoint(0,3)) instanceof FreeTile);
    }

    @Test
    /**
     * Player can unlock the exit lock after all the treasure has been picked up.
     */
    public void validPlayerMovement6() {
        Level level = makeTestLevel1(Color.BLUE);
        Model model = level.model();
        Player player = model.player();
        player.move(Direction.RIGHT,model);
        player.move(Direction.DOWN,model);
        player.move(Direction.LEFT,model);
        player.move(Direction.UP,model);
        player.move(Direction.UP,model);
        player.move(Direction.UP,model);
        assertEquals(player.location(), new IntPoint(2,1));
        assertEquals(model.treasure().size(), 0);
        Tiles tiles = model.tiles();
        assertTrue(tiles.getTile(new IntPoint(2,1)) instanceof FreeTile);
    }

    @Test
    /**
     * onNextLevel method is called when the player moves to an exit tile.
     */
    public void validPlayerMovement7() {
        test = false;
        Level level = makeTestLevel1(Color.BLUE);
        Model model = level.model();
        Player player = model.player();
        player.move(Direction.RIGHT,model);
        player.move(Direction.DOWN,model);
        player.move(Direction.LEFT,model);
        player.move(Direction.UP,model);
        player.move(Direction.UP,model);
        player.move(Direction.UP,model);
        player.move(Direction.UP,model);
        assertEquals(player.location(), new IntPoint(2,0));
        assert test;
    }

    @Test
    /**
     * Player can't move out of bounds.
     */
    public void invalidPlayerMovement1() {
        Level level = makeTestLevel1(Color.BLUE);
        Model model = level.model();
        Player player = model.player();
        player.move(Direction.DOWN,model);
        player.move(Direction.DOWN,model);
        assertEquals(player.location(), new IntPoint(2,4));
    }

    @Test
    /**
     * Player can't unlock a locked door if he doesn't have a key.
     */
    public void invalidPlayerMovement2() {
        Level level = makeTestLevel1(Color.BLUE);
        Model model = level.model();
        Player player = model.player();
        player.move(Direction.LEFT,model);
        player.move(Direction.LEFT,model);
        assertEquals(player.location(), new IntPoint(1,3));
        Tiles tiles = model.tiles();
        assertTrue(tiles.getTile(new IntPoint(0,3)) instanceof LockedDoor);
    }

    @Test
    /**
     * Player can't unlock a locked door if he doesn't have the right colored key.
     */
    public void invalidPlayerMovement3() {
        Level level = makeTestLevel1(Color.RED);
        Model model = level.model();
        Player player = model.player();
        player.move(Direction.LEFT,model);
        player.move(Direction.DOWN,model);
        player.move(Direction.UP,model);
        player.move(Direction.LEFT,model);
        assertEquals(player.location(), new IntPoint(1,3));
        assertEquals(player.keys().size(), 1);
        Tiles tiles = model.tiles();
        assertTrue(tiles.getTile(new IntPoint(0,3)) instanceof LockedDoor);
    }

    @Test
    /**
     * Player can't move to a wall tile.
     */
    public void invalidPlayerMovement4() {
        Level level = makeTestLevel1(Color.BLUE);
        Model model = level.model();
        Player player = model.player();
        player.move(Direction.LEFT,model);
        player.move(Direction.UP,model);
        player.move(Direction.UP,model);
        assertEquals(player.location(), new IntPoint(1,2));
    }

    @Test
    /**
     * Player can't unlock an exit lock if all of the treasure hasn't been picked up.
     */
    public void invalidPlayerMovement5() {
        Level level = makeTestLevel1(Color.BLUE);
        Model model = level.model();
        Player player = model.player();
        player.move(Direction.UP,model);
        player.move(Direction.UP,model);
        assertEquals(player.location(), new IntPoint(2,2));
        Tiles tiles = model.tiles();
        assertTrue(tiles.getTile(new IntPoint(2,1)) instanceof ExitLock);
    }

    @Test
    /**
     * Player can't move to a bug.
     */
    public void invalidPlayerMovement6() {
        Level level = makeTestLevel2();
        Model model = level.model();
        Player player = model.player();
        player.move(Direction.UP,model);
        player.move(Direction.RIGHT,model);
        player.move(Direction.UP,model);
        player.move(Direction.RIGHT,model);
        assertEquals(player.location(), new IntPoint(1,0));
    }

    @Test
    /**
     * Bug can move to tiles that extend free tile.
     */
    public void validBugMovement1() {
        Level level = makeTestLevel2();
        Model model = level.model();
        Bug bug = (Bug) model.entities().get(1);
        bug.move(Direction.DOWN,model);
        assertEquals(bug.location(), new IntPoint(2,1));
    }

    @Test
    /**
     * Bug can eat the player.
     */
    public void validBugMovement2() {
        test = false;
        Level level = makeTestLevel2();
        Model model = level.model();
        Bug bug = (Bug) model.entities().get(1);
        bug.move(Direction.LEFT,model);
        bug.move(Direction.DOWN,model);
        bug.move(Direction.DOWN,model);
        bug.move(Direction.LEFT,model);
        assert test;
        assertEquals(bug.location(), new IntPoint(0,2));
    }

    @Test
    /**
     * Bug can't move out of bounds.
     */
    public void invalidBugMovement1() {
        Level level = makeTestLevel2();
        Model model = level.model();
        Bug bug = (Bug) model.entities().get(1);
        bug.move(Direction.UP,model);
        assertEquals(bug.location(), new IntPoint(2,1));
    }

    @Test
    /**
     * Bug can't move to tiles that extend wall tile.
     */
    public void invalidBugMovement2() {
        Level level = makeTestLevel2();
        Model model = level.model();
        Bug bug = (Bug) model.entities().get(1);
        bug.move(Direction.RIGHT,model);
        assertEquals(bug.location(), new IntPoint(2,0));
    }

    @Test
    /**
     * Bug can't move to another bug.
     */
    public void invalidBugMovement3() {
        Level level = makeTestLevel2();
        Model model = level.model();
        Bug bug = (Bug) model.entities().get(1);
        bug.move(Direction.DOWN,model);
        bug.move(Direction.RIGHT,model);
        bug.move(Direction.DOWN,model);
        assertEquals(bug.location(), new IntPoint(3,1));
    }

    @Test
    /**
     * Random bug movements.
     */
    public void randomBugMovements() {
        Level level = makeTestLevel2();
        Model model = level.model();
        for (int i=0; i<20; i++) {
            try {
                model.tick();
            } catch (Exception e) {
                fail("Random bug movements failed");
            }
        }
    }
}
