package nz.ac.vuw.ecs.swen225.gp22.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.App.ChapsChallenge;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.lang.Thread;
import java.util.ArrayDeque;


public class Recorder{
    private ChapsChallenge game;
    private float tickSpeed=1;
    private RecordedLevel recording;
    public Recorder(ChapsChallenge game,String levelName){
        this.game=game;
        recording=new RecordedLevel(levelName,new ArrayDeque<>());
    }
    /**Getter which level the recording is of*/
    public String getRecordingLevelName(){
        return recording.levelName();
    }
    /**
     * loads a recorded game
     * @param fileName the filename of the recording**/
    public void loadRecording(String path, String fileName) throws DocumentException, IOException {
        recording=ParseRecordedGame.loadXML(path,fileName);
    }
    /**Saves a recorded game*/
    public void saveRecording(String path,String fileName)throws DocumentException, IOException{
        new SaveRecordedGame(recording).saveXML(path, fileName);
    }
    /**Set the tick speed if speed>0 otherwise throws an exception
     *
     * @param speed the new tick speed
     */
    public void setTickSpeed(float speed){
        if (speed<=0){
            throw new IllegalArgumentException("Speed has to be greater than 0");
        }
        tickSpeed=speed;
    }
    public float getTickSpeed(){
       return tickSpeed;
    }
    /**Get the next move to do from the moves arraydeque
     * @return The next recorded move*/
    public RecordedMove getNextMove(){
        return recording.moves().pollFirst();
    }
    /**Get the next move to do from the moves arraydeque without removing it from it
     * @return The next recorded move*/
    public RecordedMove peekNextMove(){
        return recording.moves().peekFirst();
    }
    /**Add last done move to the arraydeque of  moves
     * @param move the last done move*/
    public void setPreviousMove(RecordedMove move){
        recording.moves().add(move);
    }

    /**
     * Auto replay the saved game
     *
     * @throws InterruptedException if unable to complete
     */
    public void autoReplayGame() throws InterruptedException {
        while(peekNextMove()!=null){
            doMove();
            //Thread.sleep(1000/tickSpeed);
        }
    }
/**Getter for the chaps challenge game
 * */
    public ChapsChallenge getGame() {
        return game;
    }

    /**Advance the recorded game 1 move*/
    public void stepMove(){
        if(peekNextMove()!=null){
            doMove();
        }

    }
    /**do a single move**/
    public void doMove(){
        getNextMove().direction().move(game);
    }
}










