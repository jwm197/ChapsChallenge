package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import javax.xml.parsers.*;
import java.io.*;
import java.util.List;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

/**
 * Persistency module
 */

record Persistency(){

    private List<Node> parseXML(Document doc) throws ParserException {
        List<Node> levelNodes = doc.selectNodes("/level");
        levelNodes.stream().forEach(node -> node.selectNodes("player"));
        return levelNodes;
    }


    public void loadXML() {

        try{
            File xmlFile = new File("levels/level1.xml");
            if (!xmlFile.exists()){
                throw new Exception("XML file doesn't exist");
            }
            SAXReader reader = new SAXReader();
            Document document = reader.read(xmlFile);
            List<Node> level = parseXML(document);
            System.out.println("Parsing complete");
        }
        catch (Exception e){
            System.out.println("Oops, something went wrong: " + e.getMessage());
        }
    }
     
     public boolean saveXML(){
        System.out.println("Save complete");
        return false;
     }
  }
