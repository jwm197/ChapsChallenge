package nz.ac.vuw.ecs.swen225.gp22.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.App.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.gp22.Persistency.Persistency;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.lang.Thread;
import java.util.ArrayDeque;
import java.util.HashMap;


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
    /**Saves a recorded game
     * @param fileName the name of the file being saved
     * @param path the path of the file being saved*/
    public void saveRecording(String path,String fileName)throws DocumentException, IOException{

        new Persistency().saveXML(path,fileName,new SaveRecordedGame(recording).saveLevel());
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

    /**Getter for the chaps challenge game
     * */
    public ChapsChallenge getGame() {
        return game;
    }

    /**Advance the recorded game 1 move*/
    public void stepMove(){
        if(peekNextMove()!=null){
            game.performAction(peekNextMove().playerMoveDirection().toString());
            game.moveBugs(getNextMove().bugDirections());
        }

    }
}










