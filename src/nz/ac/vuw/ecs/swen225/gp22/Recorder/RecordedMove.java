package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Stack;

import org.dom4j.*;


record RecordedMove() implements RecordedEntity{
    static Stack<RecordedMove>doneMoves=new Stack<>();
    static ArrayDeque<RecordedMove>futureMoves=new ArrayDeque<>();
    @Override
    public void parseEntity(Document document) throws IOException {
        // TODO Auto-generated method stub
        
    }


}