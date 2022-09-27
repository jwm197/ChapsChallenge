package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import java.lang.Thread;
import java.util.ArrayDeque;

class Recorder{
    static int tickSpeed=1;
    private ArrayDeque<RecordedMove> doneMoves=new ArrayDeque<>();
    private ArrayDeque<RecordedMove> futureMoves=new ArrayDeque<>();
    public void setTickSpeed(int speed){
        if (speed<=0){
            throw new IllegalArgumentException("Speed has to be bigger than 0");
        }
        tickSpeed=speed;
    }
    public RecordedMove getNextMove(){
        return futureMoves.pollFirst();
    }
    public RecordedMove peekNextMove(){
        return futureMoves.peekFirst();
    }
    public void setPreviousMove(RecordedMove move){
        doneMoves.add(move);
    }
    public void autoReplayGame() throws InterruptedException {
        while(peekNextMove()!=null){
            stepMove();
            Thread.sleep(1000/tickSpeed);
        }
    }
    public void stepMove(){
        if(peekNextMove()!=null){
            setPreviousMove(peekNextMove());
            getNextMove().execute();
        }

    }


}










