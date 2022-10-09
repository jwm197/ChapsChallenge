package nz.ac.vuw.ecs.swen225.gp22.Persistency;


import nz.ac.vuw.ecs.swen225.gp22.App.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.gp22.Domain.*;
import nz.ac.vuw.ecs.swen225.gp22.Persistency.JarTool;
import org.dom4j.Document;
import org.dom4j.Node;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Converts the level XML file into a level object
 */
public class ParseXML {
    private int width = 0;
    private int height = 0;

    private void getLevelDim(Node n) {
        if (n.valueOf("@width").isEmpty() || n.valueOf("@height").isEmpty())
            throw new ParserException("X or Y coordinate not found");
        width = Integer.parseInt(n.valueOf("@width"));
        height = Integer.parseInt(n.valueOf("@height"));
    }

    /**
     * Converts a node to a set of x and y coords
     *
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
     *
     * @param colour the colour to be converted
     * @return A Java.awt colour object corresponding to the string
     */
    private Color getColour(String colour) {
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
     *
     * @param keys  the list of keys to parse
     * @param doors the list of doors to parse
     */
    private void checkKeysandDoors(List<Node> keys,Node player, List<Node> doors) {
        if (keys.isEmpty() || doors.isEmpty()) return;
        List<String> keyColour = new ArrayList<>(keys.stream()
                .map(node -> node.selectSingleNode("colour").getText()).toList());
        List<String> doorColour = doors.stream()
                .map(node -> node.selectSingleNode("colour").getText()).toList();

        player.selectNodes("key").forEach(node -> keyColour.add(node.getText()));
        Map<String, Long> keyCounts = keyColour.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        Map<String, Long> doorCounts = doorColour.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
//        keyCounts.forEach((key, value) -> {
//            if (!keyCounts.get(key).equals(doorCounts.get(key))) {
//                throw new ParserException("Number of keys don't match with num of doors"); //Check to make sure that for each door there is a key.
//            }
//        });
        System.out.println("Keys: " + keyCounts);
        System.out.println("Doors: " + doorCounts);
    }

    /**
     * Parse all the keys
     *
     * @param keys a list of keys to parse
     * @param freeTiles the list of tiles to add the new tile(s) to the game.
     * @return a list of objectbuilder instances containing info about keys
     */
    private List<Key> parseKeys(List<Node> keys, List<List<Tile>> freeTiles) {
        List<Key> keyList = keys.stream()
                .map(node -> new Key(getColour(node.selectSingleNode("colour").getText())))
                .collect(Collectors.toCollection(ArrayList::new));

        List<IntPoint> keyLocation = keys.stream()
                .map(this::getCoords)
                .collect(Collectors.toCollection(ArrayList::new));

        IntStream.range(0, keys.size())
                .forEach(i ->
                        freeTiles.get(keyLocation.get(i).x())
                                .set(keyLocation.get(i).y(), new FreeTile(keyLocation.get(i), keyList.get(i)))
                );
        return keyList;
    }

    /**
     * Parse all the doors
     *
     * @param doors a list of doors to parse
     * @param freeTiles the list of tiles to add the new tile(s) to the game.
     */
    private void parseDoors(List<Node> doors, List<List<Tile>> freeTiles) {
        List<LockedDoor> doorsList = doors.stream()
                .map(node -> new LockedDoor(getCoords(node),getColour(node.selectSingleNode("colour").getText())))
                .toList();
        IntStream.range(0, doors.size())
                .forEach(i ->
                        freeTiles.get(doorsList.get(i).location().x())
                                .set(doorsList.get(i).location().y(), doorsList.get(i))
                );
    }

    /**
     * Parse the player
     *
     * @param player the node to parse
     * @return a new objectbuilder instance containing info about player
     */
    private Player parsePlayer(Node player) {
        if (player == null) throw new ParserException("Player not found");
        return new Player(getCoords(player));
    }

    /**
     * Parse all the chips/treasures
     *
     * @param chips a list of chips to parse
     * @param freeTiles the list of tiles to add the new tile(s) to the game.
     * @return a list of objectbuilder instances containing info about chips
     */
    private List<Treasure> parseChips(List<Node> chips, List<List<Tile>> freeTiles) {
        if (chips.isEmpty()) throw new ParserException("List of chips not found");
        List<Treasure> chipsList = chips.stream()
                .map(node -> new Treasure())
                .collect(Collectors.toCollection(ArrayList::new));
        List<IntPoint> chipLocation = chips.stream()
                .map(this::getCoords)
                .collect(Collectors.toCollection(ArrayList::new));

        IntStream.range(0, chips.size())
                .forEach(i ->
                        freeTiles.get(chipLocation.get(i).x())
                                .set(chipLocation.get(i).y(), new FreeTile(chipLocation.get(i), chipsList.get(i)))
                );
        return chipsList;
    }

    /**
     * Parse all the walls
     *
     * @param walls a list of walls to parse
     * @param freeTiles the list of tiles to add the new tile(s) to the game.
     */
    private void parseWalls(List<Node> walls, List<List<Tile>> freeTiles) {
        if (walls.isEmpty()) throw new ParserException("List of walls not found");
        walls.forEach(n -> {
            if (n.selectSingleNode("x1") == null || n.selectSingleNode("y1") == null)
                throw new ParserException("First set of coordinate(s) not found");
            else if (n.selectSingleNode("x2") == null || n.selectSingleNode("y2") == null)
                throw new ParserException("Second set of coordinate(s) not found");
            int x1 = Integer.parseInt(n.selectSingleNode("x1").getText());
            int y1 = Integer.parseInt(n.selectSingleNode("y1").getText());
            int x2 = Integer.parseInt(n.selectSingleNode("x2").getText());
            int y2 = Integer.parseInt(n.selectSingleNode("y2").getText());
            freeTiles.get(x1).set(y1,new WallTile(new IntPoint(x1,y1)));
            if (x1 != x2) {
                for (int i = x1; i < x2; i++) {
                    freeTiles.get(i).set(y1,new WallTile(new IntPoint(i,y1)));
                }
            } else if (y1 != y2) {
                for (int i = y1; i < y2; i++) {
                    freeTiles.get(x1).set(i,new WallTile(new IntPoint(x1,i)));
                }
            }
            freeTiles.get(x2).set(y2,new WallTile(new IntPoint(x2,y2)));
        });
    }

    /**
     * Parse info field node and add it to the list of tiles
     *
     * @param info the node to parse
     * @param freeTiles the list of tiles to add the new tile(s) to the game.
     */
    private void parseInfo(Node info,List<List<Tile>> freeTiles) {
        if (info == null) throw new ParserException("Info not found");
        String helpText = info.selectSingleNode("helptext").getText();
        if (helpText == null) throw new ParserException("Missing help text");
        InfoField infoField = new InfoField(getCoords(info),helpText);
        freeTiles.get(infoField.location().x())
                .set(infoField.location().y(), infoField);
    }

    /**
     * Parse the lock node are add it to the list of tiles
     *
     * @param lock the node to parse
     * @param freeTiles the list of tiles to add the new tile(s) to the game.
     */
    private void parseLock(Node lock,List<List<Tile>> freeTiles) {
        if (lock == null) throw new ParserException("Lock not found");
        ExitLock exitLock = new ExitLock(getCoords(lock));
        freeTiles.get(exitLock.location().x())
                .set(exitLock.location().y(), exitLock);
    }

    /**
     * Parse the exit node and add it to the list of tiles
     *
     * @param exit the node to parse
     * @param freeTiles the list of tiles to add the new tile(s) to the game.
     */
    private void parseExit(Node exit,List<List<Tile>> freeTiles) {
        if (exit == null) throw new ParserException("Exit not found");
        Exit exitTile = new Exit(getCoords(exit));
        freeTiles.get(exitTile.location().x())
                .set(exitTile.location().y(), exitTile);
    }

    private List<Key> parseInventory(Node player) {
        List<Node> inventory = player.selectSingleNode("items").selectNodes("key");
        if(inventory.isEmpty()) return List.of();
        return inventory.stream().map(node -> new Key(getColour(node.selectSingleNode("colour").getText()))).collect(Collectors.toList());
    }

    private List<Bug> parseBugs(List<Node> bugs) {
        if(bugs.isEmpty()) return List.of();

        JarTool jarTool = new JarTool();
        try {
            jarTool.openJar("levels/level2.jar");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }

    /**
     * Parses the given file and return a map of game objects
     *
     * @param doc a dom4j document to parse
     * @return a level object
     * @throws ParserException if there is something wrong with parsing the file e.g. missing coordinates, items etc
     */
    protected Level parse(Document doc, ChapsChallenge cc) throws ParserException {
        Node root = doc.selectSingleNode("level");
        getLevelDim(root);
        List<List<Tile>> freeTiles = IntStream.range(0, width)
                .mapToObj(
                        x -> IntStream.range(0, height)
                                .mapToObj(y -> (Tile) new FreeTile(new IntPoint(x, y), null))
                                .collect(Collectors.toList())
                )
                .collect(Collectors.toList());


        checkKeysandDoors(root.selectNodes("key"),root.selectSingleNode("player"), root.selectNodes("door"));
        List<Key> keys = parseKeys(root.selectNodes("key"), freeTiles);
        List<Treasure> chips = parseChips(root.selectNodes("chip"), freeTiles);
        parseWalls(root.selectNodes("wall"),freeTiles);
        parseDoors(root.selectNodes("door"),freeTiles);
        parseInfo(root.selectSingleNode("info"),freeTiles);
        parseLock(root.selectSingleNode("lock"),freeTiles);
        parseExit(root.selectSingleNode("exit"),freeTiles);
        parseBugs(root.selectNodes("bugs"));
        Player player = parsePlayer(root.selectSingleNode("player"));
        player.keys().addAll(parseInventory(root.selectSingleNode("player")));
        return Level.makeLevel(
                player,
                new ArrayList<>(List.of(player)),//including bugs
                keys,
                chips,
                new Tiles(freeTiles, width,height),
                ()->cc.gameEnd(true),
                ()->cc.gameEnd(false)
        );
    }


}