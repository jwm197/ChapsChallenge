package nz.ac.vuw.ecs.swen225.gp22.Persistency;


import nz.ac.vuw.ecs.swen225.gp22.Domain.*;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Point;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Textures.LayeredTexture;
import org.dom4j.Document;
import org.dom4j.Node;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;


public class ParseXML {
    /**
     * Converts a node to a set of x and y coords
     * @param n the node to parse
     * @return A location instance containing x and y
     */
    private IntPoint getCoords(Node n) {
        if (n.selectSingleNode("location").selectSingleNode("x").getText().isEmpty() || n.selectSingleNode("location").selectSingleNode("y").getText().isEmpty())
            throw new ParserException("X or Y coordinate not found");
        int x = Integer.parseInt(n.selectSingleNode("location").selectSingleNode("x").getText());
        int y = Integer.parseInt(n.selectSingleNode("location").selectSingleNode("y").getText());
        return new IntPoint(x, y);
    }

    /**
     * Convert a string to a Java.awt colour object
     * @param colour the colour to be converted
     * @return A Java.awt colour object corresponding to the string
     */
    private Color getColour(String colour){
        return switch (colour) {
            case "Blue" -> Color.BLUE;
            case "Red" -> Color.RED;
            case "Yellow" -> Color.YELLOW;
            case "Green" -> Color.GREEN;
            default -> throw new ParserException("Invalid colour");
        };
    }

    /**
     * Check to make sure there is enough keys for each door and will throw a parserexeception if this condition isn't met.
     * @param keys the list of keys to parse
     * @param doors the list of doors to parse
     */
    private void checkKeysandDoors(List<Node> keys, List<Node> doors){
        if (keys.isEmpty() || doors.isEmpty()) return;
        List<String> keyColour = keys.stream()
                .map(node -> node.selectSingleNode("colour").getText()).toList();
        List<String> doorColour = doors.stream()
                .map(node -> node.selectSingleNode("colour").getText()).toList();
        Map<String, Long> keyCounts = keyColour.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        Map<String, Long> doorCounts = doorColour.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        keyCounts.forEach((key, value) -> {
            if (!keyCounts.get(key).equals(doorCounts.get(key))) {
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
    private List<Key> parseKeys(List<Node> keys) {
//        return keys.stream()
//                .map(node -> (Tile) new FreeTile(getCoords(node),
//                        new Key(getColour(node.selectSingleNode("colour").getText()))))
//                .toList();
        return keys.stream()
                .map(node -> new Key(getColour(node.selectSingleNode("colour").getText())))
                .toList();
    }

    /**
     * Parse all the doors
     * @param doors a list of doors to parse
     * @return a list of objectbuilder instances containing info about doors
     */
    private List<Tile> parseDoors(List<Node> doors) {
        return doors.stream()
                .map(node -> (Tile) new LockedDoor(getCoords(node),
                        getColour(node.selectSingleNode("colour").getText())))
                .toList();
    }

    /**
     * Parse the player
     * @param player the node to parse
     * @return a new objectbuilder instance containing info about player
     */
    private Player parsePlayer(Node player) {
        if (player == null) throw new ParserException("Player not found");
//        List<String> items = new ArrayList<>();
//        if(player.selectSingleNode("items").getText().isEmpty()) {
//            String[] temp = player.selectSingleNode("items").getText().split(",");
//            items.addAll(Arrays.asList(temp));
//        }
        return new Player(getCoords(player));
    }

    /**
     * Parse all the chips
     * @param chips a list of chips to parse
     * @return a list of objectbuilder instances containing info about chips
     */
    private List<Treasure> parseChips(List<Node> chips) {
        if (chips.isEmpty()) throw new ParserException("List of chips not found");
//        return chips.stream()
//                .map(node -> (Tile) new FreeTile(getCoords(node),new Treasure()))
//                .toList();
        return chips.stream()
                .map(node -> new Treasure())
                .toList();
    }

    /**
     * Parse all the walls
     * @param walls a list of walls to parse
     * @return a list of objectbuilder instances containing info about walls
     */
    private List<Tile> parseWalls(List<Node> walls) {
        if (walls.isEmpty()) throw new ParserException("List of walls not found");
        List<IntPoint> points = new ArrayList<>();
        walls.forEach(n -> {
            if (n.selectSingleNode("x1") == null || n.selectSingleNode("y1") == null)
                throw new ParserException("First set of coordinate(s) not found");
            else if (n.selectSingleNode("x2") == null || n.selectSingleNode("y2") == null)
                throw new ParserException("Second set of coordinate(s) not found");
            int x1 = Integer.parseInt(n.selectSingleNode("x1").getText());
            int y1 = Integer.parseInt(n.selectSingleNode("y1").getText());
            int x2 = Integer.parseInt(n.selectSingleNode("x2").getText());
            int y2 = Integer.parseInt(n.selectSingleNode("y2").getText());
            if(x1 != x2){
                for(int i=x1;i<x2;i++) points.add(new IntPoint(i,y1));
            }
            else if(y1 != y2){
                for(int i=y1;i<y2;i++) points.add(new IntPoint(x1,i));
            }
            points.add(new IntPoint(x1,y1));
            points.add(new IntPoint(x2,y2));
        });
        return points.stream().map(WallTile::new).collect(Collectors.toList());
    }

    /**
     * Parse all bugs
     * @param bugs the list of bugs to parse
     * @return a list of objectbuilder instances containing info about bugs
     */
    private void parseBugs(List<Node> bugs) {
        /*
        Still WIP
         */
//        if (bugs.isEmpty()) return;
        //List<ListInteger> coords =


        //String bugsData = coords.toString();
        //System.out.println("Bug data " + bugsData);
    }

    /**
     * Parse info field
     * @param info the node to parse
     * @return  a new objectbuilder instance containing info about info field
     */
    private InfoField parseInfo(Node info) {
        if (info == null) throw new ParserException("Info not found");
        String helpText = info.selectSingleNode("helptext").getText();
        if (helpText == null) throw new ParserException("Missing help text");
        return new InfoField(getCoords(info),helpText);
    }

    /**
     * Parse the lock
     * @param lock the node to parse
     * @return  a new objectbuilder instance containing info about lock
     */
    private ExitLock parseLock(Node lock) {
        if (lock == null) throw new ParserException("Lock not found");
        return new ExitLock(getCoords(lock));
    }

    /**
     * Parse the exit
     * @param exit the node to parse
     * @return a new objectbuilder instance containing info about exit
     */
    private Exit parseExit(Node exit) {
        if (exit == null) throw new ParserException("Exit not found");
        String dest = exit.selectSingleNode("destination").getText();
        if (dest == null) throw new ParserException("Destination not specified");
        return new Exit(getCoords(exit));
    }

    /**
     * Parses the given file and return a map of game objects
     * @param doc a dom4j document to parse
     * @return a list of objects for the game to use
     * @throws ParserException if there is something wrong with parsing the file e.g. missing coordinates, items etc
     */
    protected Level parse(Document doc) throws ParserException {
        Node root = doc.selectSingleNode("level");
        checkKeysandDoors(root.selectNodes("key"), root.selectNodes("door"));
        List<List<Tile>> tiles = List.of(
                parseWalls(root.selectNodes("wall")),
                parseDoors(root.selectNodes("door")),
                List.of(parseInfo(root.selectSingleNode("info")),
                        parseLock(root.selectSingleNode("lock")),
                        parseExit(root.selectSingleNode("exit")))
        );

        return Level.makeLevel(parsePlayer(
                root.selectSingleNode("player")),
                List.of(),//for bugs
                parseKeys(root.selectNodes("key")),
                parseChips(root.selectNodes("chip")),
                new Tiles(tiles,tiles.get(0).size(),tiles.size()),
                null,//level 1
                null//level 1
        );

    }
}