package nz.ac.vuw.ecs.swen225.gp22.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.Persistency.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.stream.Collectors;

public record SaveRecordedGame(RecordedLevel level) {
    public void saveXML(String path, String fileName) throws ParserException, IOException, DocumentException {
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            System.out.println("doesn't");
            Document doc = saveLevel(new Persistency().createDoc(path, fileName));
            System.out.println("makesdoc");
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

    /**Saves the level to the file
     *
     * @param doc the document containing the level
     * @return the recorded level object containing the level name and the list of moves
     */
    private Document saveLevel(Document doc){
        System.out.println("starts to save");
        if (doc==null){
            throw new ParserException("document not found");
        }
        System.out.println("starts to save");
        Element level= doc.getRootElement().element("level");
        level.addAttribute("name", level().levelName());
        level.setText(String.valueOf(level().levelName()));
        saveMoves(level);
        System.out.println("saveslevel");
        return doc;


    }
    /**saves the moves to the xml file
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
        Element node= level.element("move");
        node.setText(String.valueOf(move.direction()));
        node.addAttribute("time",move.time()+"");


    }
}

