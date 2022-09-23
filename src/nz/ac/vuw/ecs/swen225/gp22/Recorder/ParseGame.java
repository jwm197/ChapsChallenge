package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

public class ParseRecordedGame extends ParseXML {
    private ArrayDeque<RecordedMove> parsedDoneMoves=new ArrayDeque<>();
    private ArrayDeque<RecordedMove> parsedFutureMoves=new ArrayDeque<>();
    public void parseLevel(Node level){
        if (level==null){
            throw new ParserException("level not found");
        }
        String levelName=level.selectSingleNode("levelname").getText();
        if (levelName.equals(the currently loaded level )){
           // reset it
        }
        else{
            //call level parser here
        }
    }
    public void parseMove(Node move) {
       if (move == null) {
           throw new ParserException("Player move not found");
        }
        parsedFutureMoves.add(new RecordedMove(super.getCoords(move).get(0),super.getCoords(move).get(1)));
    }
    protected HashMap<String, Map<String, String>> parse(Document doc) throws ParserException {
        HashMap<String, Map<String, String>> data = new HashMap<>();
        Node root = doc.selectSingleNode("recordedGame");
        parseLevel("level");
        parseMove(root.selectSingleNode("move"));
        return data;
    }





}
