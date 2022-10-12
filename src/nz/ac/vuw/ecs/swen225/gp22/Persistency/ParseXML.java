package nz.ac.vuw.ecs.swen225.gp22.Persistency;


import nz.ac.vuw.ecs.swen225.gp22.App.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.gp22.Domain.*;
import org.dom4j.Document;
import org.dom4j.Node;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.List;
import java.util.jar.JarOutputStream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Converts and parse the entire level XML file into a level object to be loaded into the game
 */
public class ParseXML {
    private int width = 0;
    private int height = 0;
    private float time = 0;

    /**
     * Create a new JAR file containing all the logic for the bug
     * <p>
     * Code to compile the Java file into a class file was taken from <a href="https://stackoverflow.com/questions/2946338/how-do-i-programmatically-compile-and-instantiate-a-java-class">here</a>
     *
     * @throws RuntimeException will throw a Runtime exception if there's an I/O issue with the JAR file
     */
    public void writeJAR() {
        try {
            File sourceFile = new File("src/nz/ac/vuw/ecs/swen225/gp22/Domain/Bug.java");
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, null, null, sourceFile.getPath());
            JarTool tool = new JarTool();
            tool.startManifest();
            JarOutputStream target = tool.openJar("levels/level2.jar");
            tool.addFile(target, System.getProperty("user.dir") + "",
                    System.getProperty("user.dir") + "/src/nz/ac/vuw/ecs/swen225/gp22/Domain/Bug.class");
            target.close();
            System.out.println("JAR write complete");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the dimensions of the current level
     * @param n the root node containing information about the width and height of the level
     */
    private void getLevelInfo(Node n) {
        if (n.valueOf("@width").isEmpty() || n.valueOf("@height").isEmpty())
            throw new ParserException("X or Y coordinate not found");
        width = Integer.parseInt(n.valueOf("@width"));
        height = Integer.parseInt(n.valueOf("@height"));
        if(n.valueOf("@time").isEmpty()) time = 60;
        else time = Float.parseFloat(n.valueOf("@time"));
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
    private void checkKeysandDoors(List<Node> keys, Node player, List<Node> doors) {
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
     * @param keys      a list of keys to parse
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
     * @param doors     a list of doors to parse
     * @param freeTiles the list of tiles to add the new tile(s) to the game.
     */
    private void parseDoors(List<Node> doors, List<List<Tile>> freeTiles) {
        List<LockedDoor> doorsList = doors.stream()
                .map(node -> new LockedDoor(getCoords(node), getColour(node.selectSingleNode("colour").getText())))
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
     * @param chips     a list of chips to parse
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
     * @param walls     a list of walls to parse
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
            freeTiles.get(x1).set(y1, new WallTile(new IntPoint(x1, y1)));
            if (x1 != x2) {
                for (int i = x1; i < x2; i++) {
                    freeTiles.get(i).set(y1, new WallTile(new IntPoint(i, y1)));
                }
            } else if (y1 != y2) {
                for (int i = y1; i < y2; i++) {
                    freeTiles.get(x1).set(i, new WallTile(new IntPoint(x1, i)));
                }
            }
            freeTiles.get(x2).set(y2, new WallTile(new IntPoint(x2, y2)));
        });
    }

    /**
     * Parse info field node and add it to the list of tiles
     *
     * @param info      the node to parse
     * @param freeTiles the list of tiles to add the new tile(s) to the game.
     */
    private void parseInfo(Node info, List<List<Tile>> freeTiles) {
        if (info == null) throw new ParserException("Info not found");
        String helpText = info.selectSingleNode("helptext").getText();
        if (helpText == null) throw new ParserException("Missing help text");
        InfoField infoField = new InfoField(getCoords(info), helpText);
        freeTiles.get(infoField.location().x())
                .set(infoField.location().y(), infoField);
    }

    /**
     * Parse the lock node are add it to the list of tiles
     *
     * @param lock      the node to parse
     * @param freeTiles the list of tiles to add the new tile(s) to the game.
     */
    private void parseLock(Node lock, List<List<Tile>> freeTiles) {
        if (lock == null) throw new ParserException("Lock not found");
        ExitLock exitLock = new ExitLock(getCoords(lock));
        freeTiles.get(exitLock.location().x())
                .set(exitLock.location().y(), exitLock);
    }

    /**
     * Parse the exit node and add it to the list of tiles
     *
     * @param exit      the node to parse
     * @param freeTiles the list of tiles to add the new tile(s) to the game.
     */
    private void parseExit(Node exit, List<List<Tile>> freeTiles) {
        if (exit == null) throw new ParserException("Exit not found");
        Exit exitTile = new Exit(getCoords(exit));
        freeTiles.get(exitTile.location().x())
                .set(exitTile.location().y(), exitTile);
    }

    private List<Key> parseInventory(Node player) {
        List<Node> inventory = player.selectSingleNode("items").selectNodes("key");
        if (inventory.isEmpty()) return List.of();
        return inventory.stream().map(node -> new Key(getColour(node.selectSingleNode("colour").getText()))).collect(Collectors.toList());
    }

    /**
     * Create a list of bugs by laoding the logic and textures from the JAR file and making a new instance of a bug.
     * <p>
     * Code to load class from jar file was taken from <a href="https://stackoverflow.com/questions/2946338/how-do-i-programmatically-compile-and-instantiate-a-java-class">this question</a>
     *
     * @param bugs the list of nodes to parse
     * @return a list of bugs
     */
    private Map<Integer, Entity> parseBugs(List<Node> bugs) {
        Map<Integer, Entity> bugMap = new HashMap<>();
        if (bugs.isEmpty()) return bugMap;
        writeJAR();
        try {
            File jarFile = new File("levels\\level2.jar");
            URLClassLoader loader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()});
            Class<?> bugLogic = Class.forName("nz.ac.vuw.ecs.swen225.gp22.Domain.Bug", true, loader);
            bugs.forEach(node -> {
                try {
                    bugMap.put(Integer.valueOf(node.valueOf("@id")), (Entity) bugLogic.getDeclaredConstructor(IntPoint.class).newInstance(getCoords(node)));
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            });
            return bugMap;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Parses the given file and return a map of game objects
     *
     * @param doc a dom4j document to parse
     * @param cc  a chapschallenge game object to set the runnables
     * @return a level object
     * @throws ParserException if there is something wrong with parsing the file e.g. missing coordinates, items etc
     */
    protected Level parse(Document doc, ChapsChallenge cc) throws ParserException {
        Node root = doc.selectSingleNode("level");
        getLevelInfo(root);
        List<List<Tile>> freeTiles = IntStream.range(0, width)
                .mapToObj(
                        x -> IntStream.range(0, height)
                                .mapToObj(y -> (Tile) new FreeTile(new IntPoint(x, y), null))
                                .collect(Collectors.toList())
                )
                .collect(Collectors.toList());


        checkKeysandDoors(root.selectNodes("key"), root.selectSingleNode("player"), root.selectNodes("door"));
        List<Key> keys = parseKeys(root.selectNodes("key"), freeTiles);
        List<Treasure> chips = parseChips(root.selectNodes("chip"), freeTiles);
        parseWalls(root.selectNodes("wall"), freeTiles);
        parseDoors(root.selectNodes("door"), freeTiles);
        parseInfo(root.selectSingleNode("info"), freeTiles);
        parseLock(root.selectSingleNode("lock"), freeTiles);
        parseExit(root.selectSingleNode("exit"), freeTiles);
        Player player = parsePlayer(root.selectSingleNode("player"));
        player.keys().addAll(parseInventory(root.selectSingleNode("player")));
        Map<Integer, Entity> entities = new HashMap<>();
        entities.put(0, player);
        entities.putAll(parseBugs(root.selectNodes("bug")));
        Level level = Level.makeLevel(
                player,
                entities,
                keys,
                chips,
                new Tiles(freeTiles, width, height),
                () -> cc.gameEnd(true),
                () -> cc.gameEnd(false)
        );
        level.model().setTime(time);
        return level;
    }
}