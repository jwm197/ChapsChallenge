package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import javax.xml.parsers.*;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

/**
 * Persistency module
 */

record Persistency(){

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
        System.out.println(keyCounts);
        System.out.println(doorCounts);
        //if(keyColour.size() != doorColour.size()) throw new ParserException("Number of keys don't match with num of doors"); //Check to make sure that for each door there is a key.

    }

    private void parsePlayer(Node player){
        if(player == null) throw new ParserException("Player not found");
        System.out.println("player");
        String playerData = "Coords: " + getCoords(player).toString();
    }

    private void parseChips(List<Node> chips){
        if(chips.isEmpty()) throw new ParserException("List of chips not found");
        List<List<Integer>> coords = chips.stream().map(this::getCoords).toList();
        String chipsData = "Num of chips: " + chips.size() + " Coords: " + coords;
        System.out.println(chipsData);
    }

    private void parseWalls(List<Node> walls){
        if(walls.isEmpty()) throw new ParserException("List of walls not found");
        System.out.println("walls");
        List<List<Integer>> coords = walls.stream().map(this::getCoords).toList();
        String wallsData = "Num of walls: " + walls.size() + " Coords: " + coords;
        System.out.println(wallsData);
    }

    private void parseBugs(List<Node> bugs){
        if(bugs.isEmpty()) return;
        System.out.println("bugs");
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

    private HashMap<String,Map<String,String>> parseXML(Document doc) throws ParserException {
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


    public void loadXML(String fileName) throws Exception {

        try{
            File xmlFile = new File("levels/" + fileName);
            if (!xmlFile.exists()){
                throw new IOException("XML file doesn't exist");
            }
            SAXReader reader = new SAXReader();
            Document document = reader.read(xmlFile);
            HashMap<String,Map<String,String>> level = parseXML(document);
            System.out.println("Parsing complete");
        }
        catch (ParserException e){
            throw new Exception("Oops, something went wrong: " + e);
        }
        catch (IOException e){
            throw new IOException("Oops, something went wrong: " + e);
        }
    }
     
     public void saveXML(String levelName,String levelData) throws ParserException, IOException, DocumentException{
        try{
            File xmlFile = new File("levels/" + levelName);
            if (!xmlFile.exists()){
                throw new IOException("XML file doesn't exist");
            }
            SAXReader reader = new SAXReader();
            Document document = reader.read(xmlFile);
            System.out.println("Save complete");
        }
        catch (ParserException e){
            throw new ParserException("Oops, something went wrong: " + e.getMessage());
        }
        catch (IOException e){
            throw new IOException("Oops, something went wrong: " + e.getMessage());
        }
     }
  }
