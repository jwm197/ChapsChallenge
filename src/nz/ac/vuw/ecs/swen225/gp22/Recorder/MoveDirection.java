package nz.ac.vuw.ecs.swen225.gp22.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.App.ChapsChallenge;
public enum MoveDirection{
    NONE,LEFT,UP,DOWN,RIGHT;


    /**
     * Apply the move on the entity in this direction
     */
    public void move(ChapsChallenge game){
        if (this!=NONE){
            game.performAction(this.toString());
        }

    }

}