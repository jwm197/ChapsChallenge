package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import java.util.ArrayList;
import java.util.List;
/**
 * Used to build objects parsed
 */
public class ObjectBuilder {
    public String name = "";
    public String text = "";
    public int numChips;
    public List<Location> location;
    public List<Path> paths = new ArrayList<>();
    public List<String> items = new ArrayList<>();
    public List<String> colour = new ArrayList<>();


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
    public ObjectBuilder location(List<Location> loc) {
        location = loc;
        return this;
    }

    /**
     *
     * @param p
     * @return
     */
    public ObjectBuilder paths(List<Path> p) {
        paths = p;
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
     * @return
     */
    public ObjectBuilder getObj(){
        return this;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(name != null && !name.isEmpty()) sb.append("Name: ").append(name);
        if(text != null && !text.isEmpty()) sb.append("\nText: ").append(text);
        if(numChips > 0) sb.append("\nNumber of chips required: ").append(numChips);
        if(location != null && !location.isEmpty()) sb.append("\nLocation(s): ").append(location);
        if(paths != null && !paths.isEmpty()) sb.append("\nPaths: ").append(paths);
        if(items != null && !items.isEmpty()) sb.append("\nItems: ").append(items);
        if(colour != null && !colour.isEmpty()) sb.append("\nColours: ").append(colour);
        return sb.toString();
    }
}
