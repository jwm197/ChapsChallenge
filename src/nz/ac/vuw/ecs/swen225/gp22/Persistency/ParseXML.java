package nz.ac.vuw.ecs.swen225.gp22.Persistency;


import org.dom4j.Document;
import org.dom4j.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

record ParseXML() {
    private List<Integer> getCoords(Node n){
        if(n.selectSingleNode("location").selectSingleNode("x") == null) throw new ParserException("X coord not found");
        else if(n.selectSingleNode("location").selectSingleNode("y") == null) throw new ParserException("Y coord not found");
        int x = Integer.parseInt(n.selectSingleNode("location").selectSingleNode("x").getText());
        int y = Integer.parseInt(n.selectSingleNode("location").selectSingleNode("y").getText());
        return List.of(x,y);
    }

    private void parseKeysandDoors(List<Node> keys,List<Node> doors){
        if(keys == null) throw new ParserException("List of keys not found");
        else if(doors == null) throw new ParserException("List of doors not found");
        List<String> keyColour = keys.stream()
                .map(node -> node.selectSingleNode("colour").getText()).toList();
        List<String> doorColour = doors.stream()
                .map(node -> node.selectSingleNode("colour").getText()).toList();
        Map<String,Long> keyCounts = keyColour.stream().collect(Collectors.groupingBy(e->e,Collectors.counting()));
        Map<String,Long> doorCounts = doorColour.stream().collect(Collectors.groupingBy(e->e,Collectors.counting()));
//        System.out.println("Keys: " + keyCounts);
//        System.out.println("Doors: " + doorCounts);
        for(String k:keyCounts.keySet()){
            if(k.equals("Green")){

            }
            else if(!keyCounts.get(k).equals(doorCounts.get(k))) throw new ParserException("Number of keys don't match with num of doors"); //Check to make sure that for each door there is a key.
        }

    }

    private void parsePlayer(Node player){
        if(player == null) throw new ParserException("Player not found");
        String items = player.selectSingleNode("items").getText();
        String playerData = "Coords: " + getCoords(player).toString();
        System.out.println(playerData);
    }

    private void parseChips(List<Node> chips){
        if(chips.isEmpty()) throw new ParserException("List of chips not found");
        List<List<Integer>> coords = chips.stream().map(this::getCoords).toList();
        String chipsData = "Num of chips: " + chips.size() + " Coords: " + coords;
        System.out.println(chipsData);
    }

    private void parseWalls(List<Node> walls){
        if(walls.isEmpty()) throw new ParserException("List of walls not found");
        List<List<Integer>> coords = walls.stream().map(this::getCoords).toList();
        String wallsData = "Num of walls: " + walls.size() + " Coords: " + coords;
        System.out.println(wallsData);
    }

    private void parseBugs(List<Node> bugs){
        if(bugs.isEmpty()) return;
        List<List<Integer>> coords = bugs.stream().map(this::getCoords).toList();
        String bugsData = coords.toString();
        System.out.println("Bug data " + bugsData);
    }

    private void parseInfo(Node info){
        if(info == null) throw new ParserException("Info not found");
        String helpText = info.selectSingleNode("helptext").getText();
        if(helpText == null) throw new ParserException("Missing help text");
        String infoData = "Text: " + helpText + " Coords: " + getCoords(info).toString();
        System.out.println(infoData);

    }

    private void parseLock(List<Node> chips, Node lock){
        if(lock == null) throw new ParserException("Lock not found");
        String lockData = "Lock - Num of chips: " + chips.size() + " Coords: " + getCoords(lock).toString();
        System.out.println(lockData);
    }

    private void parseExit(Node exit){
        if(exit == null) throw new ParserException("Exit not found");
        String dest = exit.selectSingleNode("destination").getText();
        if(dest == null) throw new ParserException("Destination not specified");
        String exitData = "Destination: " + dest + " Coords: " + getCoords(exit).toString();
        System.out.println(exitData);
    }

    public HashMap<String,Map<String,String>> parse(Document doc) throws ParserException {
        HashMap<String,Map<String,String>> data = new HashMap<>();
        Node root = doc.selectSingleNode("level");
        parsePlayer(root.selectSingleNode("player"));
        parseKeysandDoors(root.selectNodes("key"),root.selectNodes("door"));
        parseChips(root.selectNodes("chip"));
        parseWalls(root.selectNodes("wall"));
        parseBugs(root.selectNodes("bug"));
        parseInfo(root.selectSingleNode("info"));
        parseLock(root.selectNodes("chip"),root.selectSingleNode("lock"));
        parseExit(root.selectSingleNode("exit"));
        return data;
    }
}
