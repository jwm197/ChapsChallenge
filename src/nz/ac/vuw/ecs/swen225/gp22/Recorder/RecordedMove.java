package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Stack;

import org.dom4j.*;


record RecordedMove(int x,int y) {
    public void execute(){
        //overriding for player movement :
        if(x>=1){

        }
        else if (x<=-1){

        }
        if (y>=1){

        }
        else if (y<=-1){

        }
    }

}