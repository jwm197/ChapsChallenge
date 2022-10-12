package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import nz.ac.vuw.ecs.swen225.gp22.Domain.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Write the necessary objects from the level object back to a level XML file
 */
public class WriteXML {

    /**
     * A quick check to make sure the coordinates are valid, throws a parser exception if not.
     *
     * @param n the point to parse
     * @throws ParserException if x or y coords are not valid
     */
    private void checkCoords(IntPoint n) {
        if (n.x() < 0 || n.y() < 0)
            throw new ParserException("X or Y coordinate not found");
    }

    /**
     * Convert a string to a Java.awt colour object
     *
     * @param colour the colour to be converted
     * @return A Java.awt colour object corresponding to the string
     * @throws ParserException if invalid colour
     */
    private String getColour(Color colour) {
        if (colour.equals(Color.BLUE)) return "Blue";
        else if (colour.equals(Color.RED)) return "Red";
        else if (colour.equals(Color.YELLOW)) return "Yellow";
        else if (colour.equals(Color.GREEN)) return "Green";
        throw new ParserException("Invalid colour");
    }

    private void writeTime(Element root,float time){
        root.addAttribute("time",String.valueOf(time));

    }

    /**
     * Check to make sure there is enough keys for each door and will throw a parserexeception if this condition isn't met.
     *
     * @param m     the model containing the list of keys and list of items in inventory
     * @param doors the list of doors to parse
     * @throws ParserException if either, the list of keys or doors are missing or the number of keys don't match with num of doors
     */
    private void checkKeysandDoors(Model m, List<LockedDoor> doors) {
        if (m.keys() == null) throw new ParserException("List of keys not found");
        else if (m.player().keys() == null) throw new ParserException("List of keys in inventory not found");
        else if (doors == null) throw new ParserException("List of doors not found");
        List<Key> keys = new ArrayList<>();
        keys.addAll(m.keys());
        keys.addAll(m.player().keys());
        List<String> keyColour = keys.stream().map(a -> getColour(a.color())).toList();
        List<String> doorColour = doors.stream()
                .map(a -> getColour(a.color())).toList();
        Map<String, Long> keyCounts = keyColour.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        Map<String, Long> doorCounts = doorColour.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
//        keyCounts.forEach((key, value) -> {
//            if (!keyCounts.get(key).equals(doorCounts.get(key))) {
//                throw new ParserException("Number of keys don't match with num of doors");
//            }
//        });
        System.out.println("Keys: " + keyCounts);
        System.out.println("Doors: " + doorCounts);
    }

    /**
     * Write all the keys to the root element
     *
     * @param root the root element to add all the keys to
     * @param keys a list of keys to parse
     */
    private void parseKeys(Element root, Map<Key, IntPoint> keys) {
        root.elements("key").forEach(Node::detach);
        int i = 1;
        for (Map.Entry<Key, IntPoint> entry : keys.entrySet()) {
            root.addElement("key").addAttribute("name", "key" + (i)).addElement("location");
            for (Element e : root.elements("key")) {
                if (e.attributeValue("name").equals("key" + (i))) {
                    e.element("location").addElement("x").setText(String.valueOf(entry.getValue().x()));
                    e.element("location").addElement("y").setText(String.valueOf(entry.getValue().y()));
                    e.addElement("colour").setText(getColour(entry.getKey().color()));
                }
            }
            i++;
        }
    }

    /**
     * Write all the locked doors to the file
     *
     * @param doors a list of doors to parse
     * @param root  the root element to add all the doors to
     */
    private void parseDoors(Element root, List<LockedDoor> doors) {
        root.elements("door").forEach(Node::detach);
        IntStream.range(0, doors.size()).forEach(i -> {
            root.addElement("door").addAttribute("name", "door" + (i + 1)).addElement("location");
            root.elements("door")
                    .forEach(e -> {
                        if (e.attributeValue("name").equals("door" + (i + 1))) {
                            e.element("location").addElement("x").setText(String.valueOf(doors.get(i).location().x()));
                            e.element("location").addElement("y").setText(String.valueOf(doors.get(i).location().y()));
                            e.addElement("colour").setText(getColour(doors.get(i).color()));
                        }
                    });
        });
    }

    /**
     * Write the player to the root element
     *
     * @param root   the root element to add the player to
     * @param player the node to parse
     */
    private void parsePlayer(Element root, Player player) {
        if (player == null) throw new ParserException("Player not found");
        checkCoords(player.location());
        root.element("player").element("location").element("x").setText(String.valueOf(player.location().x()));
        root.element("player").element("location").element("y").setText(String.valueOf(player.location().y()));
    }

    /**
     * Write all the chips/treasures to the file
     *
     * @param chips a list of chips to parse
     * @param root  the root element to add all the treasure to
     */
    private void parseChips(Element root, List<IntPoint> chips) {
        if (chips.isEmpty()) throw new ParserException("List of chips not found");
        root.elements("chip").forEach(Node::detach);
        IntStream.range(0, chips.size()).forEach(i -> {
            root.addElement("chip").addAttribute("name", "chip" + (i + 1)).addElement("location");
            root.elements("chip")
                    .forEach(e -> {
                        if (e.attributeValue("name").equals("chip" + (i + 1))) {
                            e.element("location").addElement("x").setText(String.valueOf(chips.get(i).x()));
                            e.element("location").addElement("y").setText(String.valueOf(chips.get(i).y()));
                        }
                    });
        });
    }


    /**
     * Write all items in the player's inventory to the XML file
     * @param root the root element to add all the items to
     * @param inventory a list of keys to parse
     */
    private void parseInventory(Element root, List<Key> inventory) {
        root.element("player").element("items").elements("key").forEach(Node::detach);
        IntStream.range(0, inventory.size()).forEach(i -> {
            root.element("player").element("items").addElement("key").addAttribute("name", "key" + (i + 1))
                    .addElement("colour").setText(getColour(inventory.get(i).color()));
        });
    }


    /**
     * Write all the necessary objects in level data and return a document when successful
     *
     * @param doc       a dom4j document to parse
     * @param levelData the level data to get the objects from
     * @return an updated dom4j document
     * @throws IOException if an I/O exception occured
     */
    protected Document write(Document doc, Level levelData) throws IOException {
        Element root = doc.getRootElement();
        List<List<Tile>> tiles = levelData.model().tiles().tiles();
        List<LockedDoor> doors = new ArrayList<>();
        levelData.model().tiles().tiles().forEach(tile -> tile.forEach(t -> {
            if (t instanceof LockedDoor t2) doors.add(t2);
        }));
        Map<Key, IntPoint> keyPositions = new HashMap<>();
        List<IntPoint> treasurePositions = new ArrayList<>();
        IntStream.range(0, tiles.size())
                .forEach(i -> tiles.get(i).forEach(tile -> {
                    if (tile instanceof FreeTile t) {//spotbugs error here
                        if (t.item() instanceof Key) {
                            keyPositions.put((Key) t.item(), tile.location());
                        } else if (t.item() instanceof Treasure) {
                            treasurePositions.add(tile.location());
                        }
                    }
                }));
        checkKeysandDoors(levelData.model(), doors);
        writeTime(root,levelData.model().time());
        parseInventory(root, levelData.model().player().keys());
        parsePlayer(root, levelData.model().player());
        parseChips(root, treasurePositions);
        parseKeys(root, keyPositions);
        parseDoors(root, doors);
        return doc;
    }
}
