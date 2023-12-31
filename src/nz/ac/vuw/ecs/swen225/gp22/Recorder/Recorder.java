package nz.ac.vuw.ecs.swen225.gp22.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.App.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.gp22.Persistency.Persistency;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.ArrayDeque;


/**The main recorder class which handles replaying a game, recording one in progress and delegates saving and loading to a xml file

 *  * @author Jacob McBride ID: 300537323  */
public class Recorder{
    private float tickSpeed=1;
    private RecordedLevel recording;
    /**Creates a recorder object sets the level the recording is recording and creates a new recording object
     * @param levelName the level name of the level the recording is recording */
    public Recorder(String levelName){
        recording=new RecordedLevel(levelName,new ArrayDeque<>(),new ArrayDeque<>());
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
    /**getter for the tick speed*/
    public float getTickSpeed(){
        return tickSpeed;
    }
    /**Get the next move to do from the moves arraydeque
     * @return The next recorded move*/
    public RecordedMove getNextPlayerMove(){
        return recording.playerMoves().pollFirst();
    }
    /**Get the next move to do from the moves arraydeque without removing it from it
     * @return The next recorded move*/
    public RecordedMove peekNextPlayerMove(){
        return recording.playerMoves().peekFirst();
    }
    /**Add last done move to the arraydeque of  moves
     * @param move the last done move*/
    public void setPreviousPlayerMove(RecordedMove move){
        recording.playerMoves().add(move);
    }

    /**Get the next move to do from the moves arraydeque for the bugs
     * @return The next recorded move*/
    public BugsMove getNextBugMove(){
        return recording.bugsMoves().pollFirst();
    }
    /**Get the next move to do from the moves arraydeque of bugs moves without removing it from it
     * @return The next recorded move*/
    public BugsMove peekNextBugMove(){
        return recording.bugsMoves().peekFirst();
    }

    /**Add last done move of the bugs to the arraydeque of  moves
     * @param move the last done move*/
    public void setPreviousBugMove(BugsMove move){
        recording.bugsMoves().add(move);
    }


    /**Advance the recorded game 1 for the player move
     * @param game the chaps challenge game the recording is being played on*/
    public void stepMovePlayer(ChapsChallenge game){
        if(peekNextPlayerMove()!=null){
            game.performAction(getNextPlayerMove().direction().toString());
        }

    }
    /**Advance the recorded game 1 for the bugs
     * @param game the chaps challenge game the recording is being played on*/
    public void stepMoveBugs(ChapsChallenge game){
        if(peekNextBugMove()!=null){
            game.moveBugs(getNextBugMove().getMoves());
        }
    }
}
/**A data holder for all the level including all player and bug moves and the level name*/
record RecordedLevel(String levelName, ArrayDeque<RecordedMove>playerMoves,ArrayDeque<BugsMove>bugsMoves) {

}







