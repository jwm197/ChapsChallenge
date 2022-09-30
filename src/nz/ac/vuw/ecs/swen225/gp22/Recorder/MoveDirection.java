package nz.ac.vuw.ecs.swen225.gp22.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.App.*;
public enum MoveDirection{
    None,Left,Up,Down,Right;


    /**
     * Apply the move on the entity in this direction
     */
    public void move(ChapsChallenge game){
        if (this!=None){
            game.performAction(ths.toString());
        }

    }

}