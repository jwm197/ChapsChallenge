package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.Element;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.stream.Collectors;

public record SaveRecordedGame(RecordedLevel level) {
    public void saveXML(String path, String levelName) throws IOException {
        try {
            saveLevel(createDoc(path, levelName));
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
    private void saveLevel(Document doc){
        if (doc==null){
            throw new ParserException("document not found");
        }
        Element level= doc.getRootElement().element("level");
        level.setText(String.valueOf(level().levelName()));
        saveMoves(level);


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
        level.element("move").setText(String.valueOf(move.direction().toString()));


    }
}
}
