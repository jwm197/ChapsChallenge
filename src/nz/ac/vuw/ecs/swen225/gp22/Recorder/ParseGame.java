package nz.ac.vuw.ecs.swen225.gp22.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.Persistency.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

public class ParseRecordedGame {
    public static RecordedLevel loadXML(String path, String fileName) throws IOException {

        try{
            return parseLevel(new SAXReader().read(Persistency.createDoc(path,fileName)));
        }
        catch (ParserException | NullPointerException e){
            throw new ParserException(e.getMessage());
        }
        catch (IOException e){
            throw new IOException(e.getMessage());
        }
    }

    /**Parse the level and load the right level
     *
     * @param doc the document containing the level
     * @return the recorded level object containing the level name and the list of moves
     */
    private static RecordedLevel parseLevel(Document doc){
        if (doc==null){
            throw new ParserException("document not found");
        }
        Node level = doc.selectSingleNode("level");
        return new RecordedLevel(level.getText(),parseMoves(level.selectNodes("move")));

    }
    /**Parse a move and add it to the
     *
     * @param moves the list of recorded moves being passed
     */
    private static ArrayDeque<MoveDirection> parseMoves(List<Node>moves) {
        return moves.stream().map(m->parseMove(m)).collect(Collectors.toCollection(ArrayDeque::new));
    }
    /**Parse a move and add it to the
     *
     * @param move the being passed
     */
    private static MoveDirection parseMove(Node move) {

        try{
            return MoveDirection.valueOf(move.selectSingleNode("move").getText());
        }catch(IllegalArgumentException e){
            throw new ParserException("Move direction not found");
        }


    }
}