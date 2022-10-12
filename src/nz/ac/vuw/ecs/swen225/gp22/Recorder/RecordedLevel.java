package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import java.util.ArrayDeque;


public record RecordedLevel(String levelName, ArrayDeque<RecordedMove>playerMoves,ArrayDeque<BugsMove>bugsMoves) {
    /**Get the next move to do from the moves arraydeque for the bugs
     * @return The next recorded move*/
    public BugsMove getNextBugMove(){
        return bugsMoves().pollFirst();
    }
    /**Get the next move to do from the moves arraydeque of bugs moves without removing it from it
     * @return The next recorded move*/
    public BugsMove peekNextBugMove(){
        return bugsMoves().peekFirst();
    }
    /**Add last done move of the bugs to the arraydeque of  moves
     * @param move the last done move*/
    public void setPreviousBugMove(BugsMove move){
        bugsMoves().add(move);
    }

}
