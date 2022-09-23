package nz.ac.vuw.ecs.swen225.gp22.Recorder;


import java.util.ArrayDeque;

record Recorder(){
    static ArrayDeque<RecordedMove> doneMoves=new ArrayDeque<>();
    static ArrayDeque<RecordedMove> futureMoves=new ArrayDeque<>();

}