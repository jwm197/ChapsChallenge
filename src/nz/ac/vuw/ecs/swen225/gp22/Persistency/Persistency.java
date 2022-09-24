package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import java.io.*;
import java.util.HashMap;
import java.util.List;

import org.dom4j.*;
import org.dom4j.io.SAXReader;


/**
 * Persistency module
 */

record Persistency(){
    static String path = "levels/";
    static String filename;

    /**
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public HashMap<String,ObjectBuilder> loadXML(String fileName) throws Exception {

        try{
            File xmlFile = new File(path + fileName);
            if (!xmlFile.exists()){
                throw new IOException("XML file doesn't exist");
            }
            SAXReader reader = new SAXReader();
            Document document = reader.read(xmlFile);
            return new ParseXML().parse(document);
        }
        catch (ParserException e){
            throw new Exception("Oops, something went wrong: " + e);
        }
        catch (IOException e){
            throw new IOException("Oops, something went wrong: " + e);
        }
    }

    /**
     *
     * @param levelName
     * @param levelData
     * @throws ParserException
     * @throws IOException
     * @throws DocumentException
     */
     public void saveXML(String levelName,String levelData) throws ParserException, IOException, DocumentException{
        try{
            File xmlFile = new File(path + levelName);
            if (!xmlFile.exists()){
                throw new IOException("XML file doesn't exist");
            }
            SAXReader reader = new SAXReader();
            Document document = reader.read(xmlFile);
            new WriteXML().write(document,levelData);
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
