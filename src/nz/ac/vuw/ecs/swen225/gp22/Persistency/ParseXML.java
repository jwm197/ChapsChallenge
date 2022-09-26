package nz.ac.vuw.ecs.swen225.gp22.Persistency;


import org.dom4j.Document;
import org.dom4j.Node;

import java.util.*;
import java.util.stream.Collectors;


public class ParseXML {
    /**
     * Converts a node to a set of x and y coords
     * @param n the node to parse
     * @return A location instance containing x and y
     */
    private Location getCoords(Node n) {
        if (n.selectSingleNode("location").selectSingleNode("x").getText().isEmpty() || n.selectSingleNode("location").selectSingleNode("y").getText().isEmpty())
            throw new ParserException("X or Y coordinate not found");
        int x = Integer.parseInt(n.selectSingleNode("location").selectSingleNode("x").getText());
        int y = Integer.parseInt(n.selectSingleNode("location").selectSingleNode("y").getText());
        return new Location(x, y);
    }

    /**
     * Check to make sure there is enough keys for each door and will throw a parserexeception if this condition isn't met.
     * @param keys the list of keys to parse
     * @param doors the list of doors to parse
     */
    private void checkKeysandDoors(List<Node> keys, List<Node> doors){
        if (keys == null) throw new ParserException("List of keys not found");
        else if (doors == null) throw new ParserException("List of doors not found");
        List<String> keyColour = keys.stream()
                .map(node -> node.selectSingleNode("colour").getText()).toList();
        List<String> doorColour = doors.stream()
                .map(node -> node.selectSingleNode("colour").getText()).toList();
        Map<String, Long> keyCounts = keyColour.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        Map<String, Long> doorCounts = doorColour.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        if (!keyCounts.containsKey("Green")) throw new ParserException("Green key not found");
        if (!doorCounts.containsKey("Green")) throw new ParserException("No green doors found");
        keyCounts.entrySet().stream().filter(m -> !m.getKey().equals("Green")).forEach(m -> {
            if (!keyCounts.get(m.getKey()).equals(doorCounts.get(m.getKey()))) {
                throw new ParserException("Number of keys don't match with num of doors"); //Check to make sure that for each door there is a key.
            }
        });
        System.out.println("Keys: " + keyCounts);
        System.out.println("Doors: " + doorCounts);
    }

    /**
     * Parse all the keys
     * @param keys a list of keys to parse
     * @return a list of objectbuilder instances containing info about keys
     */
    private ObjectBuilder parseKeys(List<Node> keys) {
        List<String> keyColour = keys.stream()
                .map(node -> node.selectSingleNode("colour").getText()).toList();
        List<Location> coords = keys.stream().map(this::getCoords).toList();
        return new ObjectBuilder().name("keys").colour(keyColour).location(coords);
    }

    /**
     * Parse all the doors
     * @param doors a list of doors to parse
     * @return a list of objectbuilder instances containing info about doors
     */
    private ObjectBuilder parseDoors(List<Node> doors) {
        List<String> doorColour = doors.stream()
                .map(node -> node.selectSingleNode("colour").getText()).toList();
        List<Location> coords = doors.stream().map(this::getCoords).toList();
        return new ObjectBuilder().name("doors").colour(doorColour).location(coords);
    }

    /**
     * Parse the player
     * @param player the node to parse
     * @return a new objectbuilder instance containing info about player
     */
    private ObjectBuilder parsePlayer(Node player) {
        if (player == null) throw new ParserException("Player not found");
        List<String> items = new ArrayList<>();
        if(player.selectSingleNode("items").getText().isEmpty()) {
            String[] temp = player.selectSingleNode("items").getText().split(",");
            items.addAll(Arrays.asList(temp));
        }
        return new ObjectBuilder().name("player").items(items).location(List.of(getCoords(player)));
    }

    /**
     * Parse all the chips
     * @param chips a list of chips to parse
     * @return a list of objectbuilder instances containing info about chips
     */
    private ObjectBuilder parseChips(List<Node> chips) {
        if (chips.isEmpty()) throw new ParserException("List of chips not found");
        List<Location> coords = chips.stream().map(this::getCoords).toList();
        return new ObjectBuilder().name("chips").location(coords);
    }

    /**
     * Parse all the walls
     * @param walls a list of walls to parse
     * @return a list of objectbuilder instances containing info about walls
     */
    private ObjectBuilder parseWalls(List<Node> walls) {
        if (walls.isEmpty()) throw new ParserException("List of walls not found");
        List<Path> coords = walls.stream().map(n -> {
            if (n.selectSingleNode("x1") == null || n.selectSingleNode("y1") == null)
                throw new ParserException("First set of coordinate(s) not found");
            else if (n.selectSingleNode("x2") == null || n.selectSingleNode("y2") == null)
                throw new ParserException("Second set of coordinate(s) not found");
            int x1 = Integer.parseInt(n.selectSingleNode("x1").getText());
            int y1 = Integer.parseInt(n.selectSingleNode("y1").getText());
            int x2 = Integer.parseInt(n.selectSingleNode("x2").getText());
            int y2 = Integer.parseInt(n.selectSingleNode("y2").getText());
            return new Path(x1,y1,x2,y2);
        }).toList();
        return new ObjectBuilder().name("walls").paths(coords);
    }

    /**
     * Parse all bugs
     * @param bugs the list of bugs to parse
     * @return a list of objectbuilder instances containing info about bugs
     */
    private ObjectBuilder parseBugs(List<Node> bugs) {
//        if (bugs.isEmpty()) return;
        //List<ListInteger> coords =


        //String bugsData = coords.toString();
        //System.out.println("Bug data " + bugsData);
        return new ObjectBuilder();
    }

    /**
     * Parse info field
     * @param info the node to parse
     * @return  a new objectbuilder instance containing info about info field
     */
    private ObjectBuilder parseInfo(Node info) {
        if (info == null) throw new ParserException("Info not found");
        String helpText = info.selectSingleNode("helptext").getText();
        if (helpText == null) throw new ParserException("Missing help text");
        return new ObjectBuilder().name("info").location(List.of(getCoords(info))).text(helpText);
    }

    /**
     * Parse the lock
     * @param chips a list of chips to check the number of chips
     * @param lock the node to parse
     * @return  a new objectbuilder instance containing info about lock
     */
    private ObjectBuilder parseLock(List<Node> chips, Node lock) {
        if (lock == null) throw new ParserException("Lock not found");
        else if(chips == null) throw new ParserException("List of chips not found");
        return new ObjectBuilder().name("lock").location(List.of(getCoords(lock))).numChips(chips.size());
    }

    /**
     * Parse the exit
     * @param exit the node to parse
     * @return a new objectbuilder instance containing info about exit
     */
    private ObjectBuilder parseExit(Node exit) {
        if (exit == null) throw new ParserException("Exit not found");
        String dest = exit.selectSingleNode("destination").getText();
        if (dest == null) throw new ParserException("Destination not specified");
        return new ObjectBuilder().name("exit").location(List.of(getCoords(exit))).text(dest);//next level file (destination) is stored in text
    }

    /**
     * Parses the given file and return a map of game objects
     * @param doc a dom4j document to parse
     * @return a list of objects for the game to use
     * @throws ParserException if there is something wrong with parsing the file e.g. missing coordinates, items etc
     */
    protected HashMap<String,ObjectBuilder> parse(Document doc) throws ParserException {
        HashMap<String,ObjectBuilder>  data = new HashMap<>();
        Node root = doc.selectSingleNode("level");
        checkKeysandDoors(root.selectNodes("key"), root.selectNodes("door"));
        //Adding all object data to hashmap
        data.put("player",parsePlayer(root.selectSingleNode("player")));
        data.put("key",parseKeys(root.selectNodes("key")));
        data.put("door",parseDoors(root.selectNodes("door")));
        data.put("chip",parseChips(root.selectNodes("chip")));
        data.put("info",parseInfo(root.selectSingleNode("info")));
        data.put("locks",parseLock(root.selectNodes("chip"), root.selectSingleNode("lock")));
        data.put("exit",parseExit(root.selectSingleNode("exit")));
        data.put("wall",parseWalls(root.selectNodes("wall")));
        data.put("bugs",parseBugs(root.selectNodes("bug")));
        System.out.println("Parsing complete");
        return data;
    }
}