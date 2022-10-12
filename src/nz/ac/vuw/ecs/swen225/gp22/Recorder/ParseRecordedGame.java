package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nz.ac.vuw.ecs.swen225.gp22.Persistency.*;
import org.dom4j.*;

public record ParseRecordedGame() {
    public static RecordedLevel loadXML(String path, String fileName) throws IOException {

        try{
            return parseLevel(new Persistency().createDoc(path, fileName));
        }
        catch (ParserException | NullPointerException e){
            throw new ParserException(e.getMessage());
        }
        catch (IOException | DocumentException e){
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

        return new RecordedLevel(level.valueOf("@name"),parseMoves(level.selectNodes("move")));

    }
    /**Parse a move and add it to the
     *
     * @param moves the list of recorded moves being passed
     */
    private static ArrayDeque<RecordedMove> parseMoves(List<Node>moves) {
        return moves.stream().map(m->parseMove(m)).collect(Collectors.toCollection(ArrayDeque::new));
    }
    /**Parse a move and add it to the
     *
     * @param move the being passed
     */
    private static RecordedMove parseMove(Node move) {

        try{
            return new RecordedMove(MoveDirection.valueOf(move.getText()),Float.parseFloat(move.valueOf("@time")),parseBugs(move));
        }catch(IllegalArgumentException e){
            throw new ParserException("Move direction not found");
        }



    }
    /**Parse all the bugs moves on that move
     *
     * @param move the being passed
     * @return A hashmap containing the bug id and the direction they are moving in
     */
    private static HashMap<Integer,MoveDirection> parseBugs(Node move) {
        HashMap<Integer, MoveDirection> bugMoves = new HashMap<>();
        try {
            for (Node bug : move.selectNodes("bug")) {
                bugMoves.put(Integer.parseInt(bug.valueOf("@id")), MoveDirection.valueOf(move.getText()));
            }
        } catch (IllegalArgumentException e) {
            throw new ParserException("Move direction  or bug id not found");
        }
        return bugMoves;
    }
}
