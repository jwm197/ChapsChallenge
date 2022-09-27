package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import nz.ac.vuw.ecs.swen225.gp22.Domain.IntPoint;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Level;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Player;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A bunch of tests to test the persistency module
 */
record PersistencyTests() {

    /**
     * Test that it loads the first level without any issues
     */
    @Test
    void TestLoadXML1() {
        try {
            Level l = new Persistency().loadXML("levels/","level1.xml");
            assert l.model().treasureCount() == 11;
            assert l.model().player().location().equals(new IntPoint(0,0));
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
            new Persistency().loadXML("levels/","level2.xml");
        } catch (ParserException | IOException | DocumentException e) {
            assert false : e.toString();
        }
    }

    /**
     * Test that it cannot load in a non-existent file
     */
    @Test
    void TestInvalidLoadXML1() {
        try {
            new Persistency().loadXML("levels/","level999.xml");
            assert false : "IO exception not thrown";
        } catch (IOException | DocumentException e) {
            return;
        }
    }

    /**
     * Test that it cannot load in a file with one or more coordinates missing (this is required for all objects)
     */
    @Test
    void TestInvalidLoadXML2() {
        try {
            new Persistency().loadXML("test_levels/","level_broken1.xml");
            assert false : "Parsing exception not thrown";
        } catch (ParserException | IOException | DocumentException e) {
            return;
        }
    }

    /**
     * Test that it cannot load in a file with one colour missing (this is required for all keys and doors)
     */
    @Test
    void TestInvalidLoadXML3() {
        try {
            new Persistency().loadXML("test_levels/","level_broken2.xml");
            assert false : "Parsing exception not thrown";
        } catch (ParserException | IOException | DocumentException e) {
            return;
        }
    }

    /**
     * Test that it cannot load in a file with the destination for the exit missing
     */
    @Test
    void TestInvalidLoadXML4() {
        try {
            new Persistency().loadXML("test_levels/","level_broken3.xml");
            assert false : "Parsing exception not thrown";
        } catch (ParserException | IOException | DocumentException e) {
            return;
        }
    }

    /**
     * Test that it cannot load in a file with the helptext text missing
     */
    @Test
    void TestInvalidLoadXML5() {
        try {
            new Persistency().loadXML("test_levels/","level_broken4.xml");
            assert false : "Parsing exception not thrown";
        } catch (ParserException | IOException | DocumentException e) {
            return;
        }
    }
}