package nz.ac.vuw.ecs.swen225.gp22.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.Persistency.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

public class ParseRecordedGame {
    private ArrayDeque<RecordedMove> parsedFutureMoves=new ArrayDeque<>();

    /**Parse the level and load the right level
     *
     * @param level the node containing the level name
     */
    private void parseLevel(Node level){
        if (level==null){
            throw new ParserException("level not found");
        }
        String levelName=level.selectSingleNode("levelname").getText();
        //call level here to reset
    }

    /**Parse a move and add it to the
     *
     * @param move the move being passed
     */
    private void parseMove(Node move) {
       if (move == null) {
           throw new ParserException("Move not found");
        }
        try{
            accountType = AccountType.valueOf(accountTypeString);
        }catch(IllegalArgumentException e){
            // report error
        }

        parsedFutureMoves.add(new RecordedMove(super.getCoords(move).get(0),super.getCoords(move).get(1)));
    }
//     @Override
//    protected HashMap<String, Map<String, String>> parse(Document doc) throws ParserException {
////        HashMap<String, Map<String, String>> data = new HashMap<>();
////        Node root = doc.selectSingleNode("recordedGame");
////        parseLevel("level");
////        parseMove(root.selectSingleNode("move"));
////
////
////        return data;
    }





}
