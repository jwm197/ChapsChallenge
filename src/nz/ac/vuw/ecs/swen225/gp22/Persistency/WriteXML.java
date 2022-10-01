package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import nz.ac.vuw.ecs.swen225.gp22.Domain.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class WriteXML {

    private void checkCoords(IntPoint n) {
        if (n.x() < 0 || n.y() < 0)
            throw new ParserException("X or Y coordinate not found");
    }

    /**
     * Convert a string to a Java.awt colour object
     *
     * @param colour the colour to be converted
     * @return A Java.awt colour object corresponding to the string
     */
    private String getColour(Color colour) {
        if (colour.equals(Color.BLUE)) return "Blue";
        else if (colour.equals(Color.RED)) return "Red";
        else if (colour.equals(Color.YELLOW)) return "Yellow";
        else if (colour.equals(Color.GREEN)) return "Green";
        throw new ParserException("Invalid colour");
    }

    /**
     * Check to make sure there is enough keys for each door and will throw a parserexeception if this condition isn't met.
     *
     * @param keys  the list of keys to parse
     * @param doors the list of doors to parse
     */
    private void checkKeysandDoors(List<FreeTile> keys, List<LockedDoor> doors) {
        if (keys == null) throw new ParserException("List of keys not found");
        else if (doors == null) throw new ParserException("List of doors not found");
//        List<String> keyColour = keys.stream().map(a->a.item().)
        List<String> doorColour = doors.stream()
                .map(a -> a.color().toString()).toList();
        System.out.println(doorColour);
//        Map<String, Long> keyCounts = keyColour.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
//        Map<String, Long> doorCounts = doorColour.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
//        if (!keyCounts.containsKey("Green")) throw new ParserException("Green key not found");
//        if (!doorCounts.containsKey("Green")) throw new ParserException("No green doors found");
//        keyCounts.entrySet().stream().filter(m -> !m.getKey().equals("Green")).forEach(m -> {
//            if (!keyCounts.get(m.getKey()).equals(doorCounts.get(m.getKey()))) {
//                throw new ParserException("Number of keys don't match with num of doors"); //Check to make sure that for each door there is a key.
//            }
//        });
//        System.out.println("Keys: " + keyCounts);
//        System.out.println("Doors: " + doorCounts);
    }

//    /**
//     * Parse all the keys
//     *
//     * @param keys a list of keys to parse
//     * @return a list of objectbuilder instances containing info about keys
//     */
//    private void parseKeys(Element root, List<FreeTile> keys) {
//        keys.forEach(freeTile -> {
//            if (freeTile.item() instanceof Key key) {
//                root.addElement("key").valueOf("name");
//                root.element("key").element("location").addElement("x").setText(String.valueOf(freeTile.location().x()));
//                root.element("key").element("location").addElement("y").setText(String.valueOf(freeTile.location().y()));
//            }
//        });
//    }
//
//    /**
//     * Parse all the doors
//     *
//     * @param doors a list of doors to parse
//     * @return a list of objectbuilder instances containing info about doors
//     */
//    private void parseDoors(Element root, List<LockedDoor> doors) {
//        doors.forEach(lockedDoor -> {
//            root.addElement("key").valueOf("name");
//            root.element("key").element("location").addElement("x").setText(String.valueOf(lockedDoor.location().x()));
//            root.element("key").element("location").addElement("y").setText(String.valueOf(lockedDoor.location().y()));
////            root.element("key").element("location").addElement("y").setText(lockedDoor.color()
//        });
//    }

    /**
     * Parse the player
     *
     * @param player the node to parse
     * @return a new objectbuilder instance containing info about player
     */
    private void parsePlayer(Element root, Player player) {
        if (player == null) throw new ParserException("Player not found");
        checkCoords(player.location());
        root.element("player").element("location").element("x").setText("999");//String.valueOf(player.location().x())
        root.element("player").element("location").element("y").setText("999");
    }

//    /**
//     * Parse all the chips
//     *
//     * @param chips a list of chips to parse
//     * @return a list of objectbuilder instances containing info about chips
//     */
//    private void parseChips(Element root, List<FreeTile> chips) {
//        if (chips.isEmpty()) throw new ParserException("List of chips not found");
//        chips.forEach(c -> {
//            if (c.item() instanceof Treasure) {
//                root.addElement("chip").addElement("location");
//                root.element("chip").element("location").addElement("x").setText(String.valueOf(c.location().x()));
//                root.element("chip").element("location").addElement("y").setText(String.valueOf(c.location().y()));
//            }
//        });
//    }

    /**
     * @param doc
     * @param levelData
     */
    protected Document write(Document doc, Level levelData) throws IOException {
//        Model m = levelData.model();
//        Tiles<? extends Tile> tiles = m.tiles();
        Element root = doc.getRootElement();
//        checkKeysandDoors(null,null);
        parsePlayer(root, levelData.model().player());
//        parseKeys(root, levelData.model().player());
//        parseDoors(root, levelData.model().player());
//        parseChips(root, levelData.model().player());
        return doc;
    }
}
