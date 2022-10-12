package nz.ac.vuw.ecs.swen225.gp22.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.Persistency.*;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileOutputStream;
import java.io.IOException;


public record SaveRecordedGame(RecordedLevel level) {
    /**Saves a recorded game
     * @param path the directory save in
     * @param fileName the name of the file to save*/
    public void saveXML(String path, String fileName) throws ParserException, IOException, DocumentException {
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            Document doc = saveLevel();
            FileOutputStream fos = new FileOutputStream(path+fileName);
            XMLWriter writer = new XMLWriter(fos, format);
            writer.write(doc);
            System.out.println("Save complete");


        } catch (ParserException | NullPointerException e) {
            throw new ParserException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**Saves the level to the document
     *
     *
     * @return document being saved
     */
    private Document saveLevel(){

        Document doc = DocumentHelper.createDocument();

        Element level=doc.addElement("level");
        level.addAttribute("name", level().levelName());
        level.setText(String.valueOf(level().levelName()));
        saveMoves(level);
        return doc;
    }
    /**saves the moves to the document
     *
     * @param level the list of recorded moves being passed
     */
    private void saveMoves(Element level) {
        level().moves().stream().forEach(m->saveMove(level,m));
    }
    /**Saves a move to the file
     *
     * @param move the being saved
     * @param level the level element of the document being saved
     */
    private void saveMove(Element level, RecordedMove move) {
        Element node= level.addElement("move");
        node.setText(move.direction().toString());
        node.addAttribute("time",move.time()+"");


    }
}

