package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import javax.xml.parsers.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.dom4j.*;
import org.dom4j.io.SAXReader;


/**
 * Persistency module
 */

record Persistency(){

    public void loadXML(String fileName) throws Exception {

        try{
            File xmlFile = new File("levels/" + fileName);
            if (!xmlFile.exists()){
                throw new IOException("XML file doesn't exist");
            }
            SAXReader reader = new SAXReader();
            Document document = reader.read(xmlFile);
            HashMap<String,Map<String,String>> level = new ParseXML().parse(document);//need to return
            System.out.println("Load complete");
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
            boolean write = new WriteXML().write(document,levelData);
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
