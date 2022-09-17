package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import javax.xml.parsers.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

/**
 * Persistency module
 */

record Persistency(){

    private HashMap<String,Map<String,String>> parseXML(Document doc) throws ParserException {
        HashMap<String,Map<String,String>> data = new HashMap<>();
        List<Node> levelNodes = doc.selectNodes("/level");
        for(Node eachNode : levelNodes){
            Node player = eachNode.selectSingleNode("player");
            Node portal = eachNode.selectSingleNode("portal");
            List<Node> keys = eachNode.selectNodes("key");
            List<Node> doors = eachNode.selectNodes("door");
            if(keys.size() != doors.size()) throw new ParserException("Number of keys don't match with num of doors"); //Check to make sure that for each door there is a key.
        }


        return data;
    }


    public void loadXML(String fileName) throws ParserException, IOException, DocumentException{

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
            throw new ParserException("Oops, something went wrong: " + e.getMessage());
        }
        catch (IOException e){
            throw new IOException("Oops, something went wrong: " + e.getMessage());
        }
    }
     
     public boolean saveXML(){
        System.out.println("Save complete");
        return false;
     }
  }
