package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import nz.ac.vuw.ecs.swen225.gp22.App.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.gp22.Domain.*;
import nz.ac.vuw.ecs.swen225.gp22.Renderer.RenderPanel;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A bunch of tests to test the persistency module
 */
record PersistencyTests() {

    /**
     * Tick method to simulate and update player movement. Assuming it takes the player 2 seconds to move (60 sec per tick, so 120 ticks in total)
     * @param rp the renderpanel to update
     */
    private void tick(RenderPanel rp) {
        for (int i = 0; i < 120; i++) rp.tick();

    }

    private int checkTile(List<List<Tile>> tiles, Color c){
        int count = 0;
        for(List<Tile> tiles1 : tiles){
            for(Tile t : tiles1){
                if(t instanceof FreeTile t2 && t2.item() instanceof Key k && k.color().equals(c)) count++;
            }
        }
        return count;
    }
    /**
     * Test that it loads the first level without any issues
     */
    @Test
    void TestLoadXML1() {
        try {
            Level l = new Persistency().loadXML("levels/", "level1.xml", new ChapsChallenge());
            assert l.model().treasure().size() == 11;
            assert l.model().player().location().equals(new IntPoint(7, 6));
        } catch (ParserException | IOException | DocumentException e) {
            assert false : e.toString();
        }
    }

    /**
     * Test that it loads the second level without any issues
     */
    @Test
    void TestLoadXML2() {
        try {
            new Persistency().loadXML("levels/", "level2.xml", new ChapsChallenge());
        } catch (ParserException | IOException | DocumentException e) {
            assert false : e.toString();
        }
    }



    /**
     * Test that it can read a given level file and then write the level data to a new file
     */
    @Test
    void TestReadWriteXML1() {
        try {
            Level l = new Persistency().loadXML("levels/", "level1.xml", new ChapsChallenge());
            RenderPanel rp = new RenderPanel();
            rp.bind(l.model());
            l.model().player().move(Direction.LEFT, l.model());
            tick(rp);
            l.model().player().move(Direction.LEFT, l.model());
            tick(rp);
            l.model().player().move(Direction.UP, l.model());
            tick(rp);
            l.model().player().move(Direction.DOWN, l.model());
            tick(rp);
            l.model().player().move(Direction.DOWN, l.model());
            assert l.model().player().keys().size() == 2;
            assert checkTile(l.model().tiles().tiles(), Color.BLUE) == 0;
            new Persistency().saveXML("levels/", "level1.xml", "test_levels/", "l1.xml", l);
            Level l2 = new Persistency().loadXML("test_levels/","l1.xml",new ChapsChallenge());
            assert l2.model().player().keys().size() == 2;
            assert checkTile(l2.model().tiles().tiles(), Color.BLUE) == 0;

        } catch (ParserException | IOException | DocumentException e) {
            assert false : e.toString();
        }
    }

    /**
     * Test that it can read a given level file and then write the level data to a new file
     */
    @Test
    void TestReadWriteXML2() {
        try {
            Level l = new Persistency().loadXML("levels/", "level1.xml", new ChapsChallenge());
            RenderPanel rp = new RenderPanel();
            rp.bind(l.model());
            l.model().player().move(Direction.LEFT, l.model());
            tick(rp);
            l.model().player().move(Direction.LEFT, l.model());
            tick(rp);
            l.model().player().move(Direction.UP, l.model());
            tick(rp);
            l.model().player().move(Direction.UP, l.model());
            tick(rp);
            l.model().player().move(Direction.LEFT, l.model());
            tick(rp);
            l.model().player().move(Direction.LEFT, l.model());
            tick(rp);
            l.model().player().move(Direction.LEFT, l.model());
            assert l.model().player().keys().size() == 1;
            assert checkTile(l.model().tiles().tiles(), Color.BLUE) == 1;
            assert checkTile(l.model().tiles().tiles(), Color.YELLOW) == 1;
            new Persistency().saveXML("levels/", "level1.xml", "test_levels/", "l1.xml", l);
            Level l2 = new Persistency().loadXML("test_levels/","l1.xml",new ChapsChallenge());
            assert l2.model().player().keys().size() == 1;
            assert checkTile(l.model().tiles().tiles(), Color.BLUE) == 1;
            assert checkTile(l.model().tiles().tiles(), Color.YELLOW) == 1;
        } catch (ParserException | IOException | DocumentException e) {
            assert false : e.toString();
        }
    }

    /**
     * Test that it can read a given level file and then write the level data to a new file
     */
    @Test
    void TestReadWriteXML3() {
        try {
            Level l = new Persistency().loadXML("levels/", "level2.xml", new ChapsChallenge());
            new Persistency().saveXML("levels/", "level2.xml", "test_levels/", "l2.xml", l);
        } catch (ParserException | IOException | DocumentException e) {
            assert false : e.toString();
        }
    }

    /**
     * Test that it cannot load in a file with one or more coordinates missing (this is required for all objects)
     */
    @Test
    void TestInvalidLoadXML1() {
        try {
            new Persistency().loadXML("test_levels/", "level_broken1.xml", new ChapsChallenge());
            assert false : "Parsing exception not thrown";
        } catch (ParserException | IOException | DocumentException e) {
            return;
        }
    }

    /**
     * Test that it cannot load in a file with one colour missing (this is required for all keys and doors)
     */
    @Test
    void TestInvalidLoadXML2() {
        try {
            new Persistency().loadXML("test_levels/", "level_broken2.xml", new ChapsChallenge());
            assert false : "Parsing exception not thrown";
        } catch (ParserException | IOException | DocumentException e) {
            return;
        }
    }

    /**
     * Test that it cannot load in a file with the destination for the exit missing
     */
    @Test
    void TestInvalidLoadXML3() {
        try {
            new Persistency().loadXML("test_levels/", "level_broken3.xml", new ChapsChallenge());
            assert false : "Parsing exception not thrown";
        } catch (ParserException | IOException | DocumentException e) {
            return;
        }
    }

    /**
     * Test that it cannot load in a file with the helptext text missing
     */
    @Test
    void TestInvalidLoadXML4() {
        try {
            new Persistency().loadXML("test_levels/", "level_broken4.xml", new ChapsChallenge());
            assert false : "Parsing exception not thrown";
        } catch (ParserException | IOException | DocumentException e) {
            return;
        }
    }

    @Test
    void TestWriteJAR() {
        new Persistency().writeJAR();
    }
}