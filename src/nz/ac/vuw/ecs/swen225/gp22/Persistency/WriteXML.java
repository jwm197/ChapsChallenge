package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import org.dom4j.Document;
import org.dom4j.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WriteXML {

    private List<Integer> getCoords(Node n) {
        if (n.selectSingleNode("location").selectSingleNode("x") == null || n.selectSingleNode("location").selectSingleNode("y") == null)
            throw new ParserException("X or Y coordinate not found");
        int x = Integer.parseInt(n.selectSingleNode("location").selectSingleNode("x").getText());
        int y = Integer.parseInt(n.selectSingleNode("location").selectSingleNode("y").getText());
        return List.of(x, y);
    }


    private void parseKeysandDoors(List<Node> keys, List<Node> doors) {
        if (keys == null) throw new ParserException("List of keys not found");
        else if (doors == null) throw new ParserException("List of doors not found");
//        List<String> keyColour = keys.stream()
//                .map(node -> node.selectSingleNode("colour").getText()).toList();
//        List<String> doorColour = doors.stream()
//                .map(node -> node.selectSingleNode("colour").getText()).toList();
//        Map<String, Long> keyCounts = keyColour.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
//        Map<String, Long> doorCounts = doorColour.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
//
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

    private void parsePlayer(Node player) {
        if (player == null) throw new ParserException("Player not found");
//        String[] items = player.selectSingleNode("items").getText().split(",");
//        String playerData = "Num items: " + items.length + " Coords: " + getCoords(player);
//        System.out.println(playerData);
    }

    private void parseChips(List<Node> chips) {
        if (chips.isEmpty()) throw new ParserException("List of chips not found");
//        List<List<Integer>> coords = chips.stream().map(this::getCoords).toList();
//        String chipsData = "Num of chips: " + chips.size() + " Coords: " + coords;
//        System.out.println(chipsData);
    }

    private void parseLock(List<Node> chips, Node lock) {
        if (lock == null) throw new ParserException("Lock not found");
//        String lockData = "Lock - Num of chips: " + chips.size() + " Coords: " + getCoords(lock);
//        System.out.println(lockData);
    }

    private void parseExit(Node exit) {
        if (exit == null) throw new ParserException("Exit not found");
//        String dest = exit.selectSingleNode("destination").getText();
//        if (dest == null) throw new ParserException("Destination not specified");
//        String exitData = "Destination: " + dest + " Coords: " + getCoords(exit);
//        System.out.println(exitData);
    }

    protected boolean write(Document doc,String levelData) {
        return false;
    }
}
