package nz.ac.vuw.ecs.swen225.gp22.Persistency;

import nz.ac.vuw.ecs.swen225.gp22.App.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.gp22.Domain.Level;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * The main persistency module, handles the functionality to load and save game levels.
 */
public record Persistency() {

    /**
     * Creates a DOM4j document with a given path and filename to the level file
     * @param path the path to the level
     * @param fileName the filename to the levelfile ending with .xml
     * @return A DOM4j document
     * @throws IOException if there's something wrong with the I/O
     * @throws DocumentException if the document cannot be read
     */
    public Document createDoc(String path, String fileName) throws IOException, DocumentException {
        File xmlFile = new File(path + fileName);
        if (!xmlFile.exists()) throw new IOException("Level file doesn't exist: " + fileName);
        return new SAXReader().read(xmlFile);
    }

    /**
     * Loads a given xml file and returns a level containing all the game objects
     *
     * @param path     the path to the file
     * @param fileName the name of the file to parse
     * @return a level object to be used in the game
     * @throws ParserException   if parsing goes wrong
     * @throws IOException       if file cannot be read
     * @throws DocumentException if something is wrong with the document
     */
    public Level loadXML(String path, String fileName, ChapsChallenge chapsChallenge) throws ParserException, IOException, DocumentException {
        try {
            return new ParseXML().parse(createDoc(path, fileName), chapsChallenge);
        } catch (ParserException | NullPointerException e) {
            throw new ParserException(e.toString());
        } catch (IOException e) {
            throw new IOException(e.toString());
        }
    }


    /**
     * Saves the game by taking in all the game objects and calling the XML save method
     * @param levelPath the path of the file to parse
     * @param levelName the name of the file to parse
     * @param savedPath the path to the file to save
     * @param savedFile the name of the file to save
     * @param levelData the level data to parse
     * @throws ParserException   if parsing goes wrong
     * @throws IOException       if file cannot be read
     * @throws DocumentException if something is wrong with the document
     */
    public void saveLevel(String levelPath, String levelName, String savedPath,String savedFile,Level levelData) throws ParserException, IOException, DocumentException {
        Document doc = new WriteXML().write(createDoc(levelPath,levelName),levelData);
        saveXML(savedPath,savedFile,doc);
    }

    /**
     * Writes the given document to a given path and file name
     * @param savedPath the path to the file to save
     * @param savedFile the name of the file to save
     */
    public void saveXML(String savedPath,String savedFile,Document document) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileOutputStream(savedPath+savedFile), format);
        writer.write(document);
        System.out.println("Save complete");
    }
}