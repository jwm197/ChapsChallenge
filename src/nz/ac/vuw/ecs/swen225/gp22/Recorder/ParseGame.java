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
     * @param level the document containing the level
     * @return the level name the recording is played off
     */
    private String parseLevel(Document level){
        if (level==null){
            throw new ParserException("level not found");
        }

       return level.selectSingleNode("levelname").getText();

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
            parsedFutureMoves.add(new RecordedMove(MoveDirection.valueOf(move.selectSingleNode("move").getText()));
        }catch(IllegalArgumentException e){
            throw new ParserException("Move direction not found");
        }


    }






}
