package nz.ac.vuw.ecs.swen225.gp22.Recorder;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.lang.Thread;
import java.util.ArrayDeque;


public class Recorder{
    private ChapsChallenge game;
    public static double tickSpeed=1;
    private RecordedLevel recording;
    Recorder(ChapsChallenge game,String levelName){
        this.game=game;
        recording=new RecordedLevel(levelName,new ArrayDeque<>());
    }

    public void loadRecording(String path, String fileName) throws DocumentException, IOException {
        recording=ParseRecordedGame.loadXML(path,fileName);
    }
    /**Set the tick speed if speed>0 otherwise throws an exception
     *
     * @param speed the new tick speed
     */
    public void setTickSpeed(double speed){
        if (speed<=0){
            throw new IllegalArgumentException("Speed has to be greater than 0");
        }
        tickSpeed=speed;
    }
    /**Get the next move to do from the moves arraydeque
     * @return The next recorded move*/
    public MoveDirection getNextMove(){
        return recording.moves().pollFirst();
    }
    /**Get the next move to do from the moves arraydeque without removing it from it
     * @return The next recorded move*/
    public MoveDirection peekNextMove(){
        return recording.moves().peekFirst();
    }
    /**Add last done move to the arraydeque of  moves
     * @param move the last done move*/
    public void setPreviousMove(MoveDirection move){
        recording.moves().add(move);
    }

    /**
     * Auto replay the saved game
     *
     * @throws InterruptedException if unable to complete
     */
    public void autoReplayGame() throws InterruptedException {
        while(peekNextMove()!=null){
            stepMove();
            //Thread.sleep(1000/((Long)tickSpeed));
        }
    }
/**Getter for game
 * @param the chaps challenge game
 * */
    public ChapsChallenge getGame() {
        return game;
    }

    /**Advance the recorded game 1 move*/
    public void stepMove(){
        if(peekNextMove()!=null){
            setPreviousMove(peekNextMove());
            getNextMove().move(game);
        }

    }
}










