package nz.ac.vuw.ecs.swen225.gp22.Persistency;

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
            HashMap<String,ObjectBuilder> data = new Persistency().loadXML("levels/","level1.xml");
            System.out.println(data.toString());
            assert data.toString().equals("""
                    {door=Item: doors
                    Location(s): [Location{x=-2, y=3}, Location{x=2, y=3}, Location{x=-3, y=2}, Location{x=-3, y=-2}, Location{x=-1, y=-3}, Location{x=1, y=-3}, Location{x=3, y=2}, Location{x=3, y=-2}]
                    Colours: [Green, Green, Blue, Red, Yellow, Yellow, Red, Blue], exit=Item: exit
                    Text: level2.xml
                    Location(s): [Location{x=0, y=4}], bugs=, chip=Item: chips
                    Location(s): [Location{x=-2, y=0}, Location{x=2, y=0}, Location{x=0, y=-2}, Location{x=1, y=-5}, Location{x=-1, y=-5}, Location{x=5, y=1}, Location{x=5, y=-1}, Location{x=-5, y=1}, Location{x=-5, y=-1}, Location{x=-3, y=5}, Location{x=3, y=5}], locks=Item: lock
                    Number of chips required: 11
                    Location(s): [Location{x=0, y=3}], wall=Item: walls
                    Paths: [Path{x=-1, y=6, x1=-5, y1=6}, Path{x=1, y=6, x1=5, y1=6}, Path{x=-5, y=5, x1=-5, y1=4}, Path{x=5, y=5, x1=5, y1=4}, Path{x=-1, y=5, x1=-1, y1=3}, Path{x=1, y=5, x1=1, y1=3}, Path{x=-1, y=5, x1=-1, y1=3}, Path{x=1, y=5, x1=1, y1=3}], key=Item: keys
                    Location(s): [Location{x=-2, y=1}, Location{x=-2, y=-1}, Location{x=2, y=1}, Location{x=1, y=-6}, Location{x=2, y=-5}, Location{x=2, y=5}, Location{x=2, y=1}]
                    Colours: [Blue, Blue, Red, Red, Yellow, Yellow, Green], player=Item: player
                    Location(s): [Location{x=0, y=0}]
                    Items: [], info=Item: info
                    Text: Lorem ipsum dolor blah blah blah
                    Location(s): [Location{x=0, y=1}]}
                    """) : "Output doesn't match expected output";
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