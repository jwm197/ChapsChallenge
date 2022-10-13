package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BugsMove{
    private final float time;
    private final Map<Integer,MoveDirection>moves=new HashMap<>();
    public BugsMove(float time,Map<Integer,MoveDirection>moves){
        this.time=time;
        this.moves.putAll(moves);
    }
    public float getTime(){
        return time;
    }
    public Map<Integer,MoveDirection>getMoves(){
        return Collections.unmodifiableMap(moves);
    }


}