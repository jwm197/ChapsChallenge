package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import org.junit.jupiter.api.*;

import java.util.HashMap;

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
            HashMap<String,ObjectBuilder> data = new Persistency().loadXML("level1.xml");
            System.out.println(data.toString());
            data.get("player").location;
            assert data.toString().equals("{door=Item: , exit=Item: , bugs=Item: , chip=Item: , locks=Item: , wall=Item: , key=Item: , player=Item: , info=Item: }") : "Data doesn't match";
        } catch (Exception e) {
            assert false : e.toString();
        }
    }

    /**
     * Test that it loads the second level without any issues
     */
    @Test
    void TestLoadXML2() {
        try {
            new Persistency().loadXML("level2.xml");
        } catch (Exception e) {
            assert false : e.getMessage();
        }
    }

    /**
     * Test that it cannot load in a non-existent file
     */
    @Test
    void TestInvalidLoadXML1() {
        try {
            new Persistency().loadXML("level999.xml");
            assert false : "IO exception not thrown";
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Test that it cannot load in a file with one or more coordinates missing (this is required for all objects)
     */
    @Test
    void TestInvalidLoadXML2() {
        try {
            assert false : "Parsing exception not thrown";
        } catch (Exception e) {
            return;
        }
    }
}