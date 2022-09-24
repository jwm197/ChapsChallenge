package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ObjectBuilder {
    public String name = "";
    public String text = "";
    public int numChips;
    public List<Integer> location = new ArrayList<>();
    public List<List<Integer>> locations = new ArrayList<>();
    public List<String> items = new ArrayList<>();
    public List<String> colour = new ArrayList<>();
    public List<String> keys = new ArrayList<>();
    public List<String> doors = new ArrayList<>();
    public List<String> bugs = new ArrayList<>();

    /**
     *
     * @param n name
     * @return
     */
    public ObjectBuilder name(String n) {
        name = n;
        return this;
    }

    /**
     *
     * @param t
     * @return
     */
    public ObjectBuilder text(String t) {
        text = t;
        return this;
    }

    /**
     *
     * @param c
     * @return
     */
    public ObjectBuilder numChips(int c) {
        numChips = c;
        return this;
    }

    /**
     *
     * @param loc
     * @return
     */
    public ObjectBuilder location(List<Integer> loc) {
        location = loc;
        return this;
    }

    /**
     *
     * @param l
     * @return
     */
    public ObjectBuilder locations(List<List<Integer>> l) {
        locations = l;
        return this;
    }

    /**
     *
     * @param i
     * @return
     */
    public ObjectBuilder items(List<String> i) {
        items = i;
        return this;
    }

    /**
     *
     * @param c
     * @return
     */
    public ObjectBuilder colour(List<String> c) {
        colour = c;
        return this;
    }

    /**
     *
     * @param k
     * @return
     */
    public ObjectBuilder keys(List<String> k) {
        keys = k;
        return this;
    }

    /**
     *
     * @param d
     * @return
     */
    public ObjectBuilder doors(List<String> d) {
        doors = d;
        return this;
    }

    /**
     *
     * @param b
     * @return
     */
    public ObjectBuilder bugs(List<String> b) {
        bugs = b;
        return this;
    }

    /**
     *
     * @return
     */
    public ObjectBuilder getObj(){
        return this;
    }


    @Override
    public String toString() {
        return "Item: " + name +
                "Text: " + text +
                "Number of chips required: " + numChips +
                "Location(s): " + if(!location.isEmpty()) location else if(!locations.isEmpty()) locations +
                "Items: " + items
                ;
    }
}
