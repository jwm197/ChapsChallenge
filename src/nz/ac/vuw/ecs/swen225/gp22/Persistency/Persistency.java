package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import java.io.*;
import java.util.HashMap;
import java.util.List;

import nz.ac.vuw.ecs.swen225.gp22.Domain.Level;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


/**
 * Persistency module
 */

public record Persistency() {
    /**
     * @param path
     * @param fileName
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public Document createDoc(String path, String fileName) throws IOException, DocumentException {
        File xmlFile = new File(path + fileName + ".xml");
        if (xmlFile.createNewFile()) {
            System.out.println("XML file created successfully");
        }
        SAXReader reader = new SAXReader();
        return reader.read(xmlFile);
    }

    /**
     * Loads a given xml file and returns a hashmap containing all the game objects
     *
     * @param path     the path to the file
     * @param fileName the name of the file to parse
     * @return A HashMap containing all the game objects built using ObjectBuilder with the key being the object name
     * @throws ParserException   if parsing goes wrong
     * @throws IOException       if file cannot be read
     * @throws DocumentException if something is wrong with the document
     */
    public Level loadXML(String path, String fileName) throws ParserException, IOException, DocumentException {
        try {
            return new ParseXML().parse(createDoc(path, fileName));
        } catch (ParserException | NullPointerException e) {
            throw new ParserException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Saves the game by taking in all the game objects and writing them to the xml file
     *
     * @param levelName the name of the file to parse
     * @param levelData the map of objects to parse
     * @throws ParserException   if parsing goes wrong
     * @throws IOException       if file cannot be read
     * @throws DocumentException if something is wrong with the document
     */
    public void saveXML(String path, String levelName, Level levelData) throws ParserException, IOException, DocumentException {
        try {
            Document doc = new WriteXML().write(createDoc(path, levelName), levelData);
            System.out.println("Save complete");
        } catch (ParserException | NullPointerException e) {
            throw new ParserException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }


}