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
    public HashMap<String,ObjectBuilder> loadXML(String path,String fileName) throws ParserException,IOException,DocumentException {

        try{
            File xmlFile = new File(path + fileName);
            if (!xmlFile.exists()){
                throw new IOException("XML file doesn't exist");
            }
            SAXReader reader = new SAXReader();
            Document document = reader.read(xmlFile);
            return new ParseXML().parse(document);
        }
        catch (ParserException | NullPointerException e){
            throw new ParserException(e.getMessage());
        }
        catch (IOException e){
            throw new IOException(e.getMessage());
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
     public void saveXML(String levelName,HashMap<String,ObjectBuilder> levelData) throws ParserException, IOException, DocumentException{
        try{
            File xmlFile = new File(levelName + levelName);
            if (!xmlFile.exists()){
                throw new IOException("XML file doesn't exist");
            }
            SAXReader reader = new SAXReader();
            Document document = reader.read(xmlFile);
            new WriteXML().write(path,document,levelData);
            System.out.println("Save complete");
        }
        catch (ParserException | NullPointerException e){
            throw new ParserException(e.getMessage());
        }
        catch (IOException e){
            throw new IOException(e.getMessage());
        }
     }
  }
