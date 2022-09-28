package nz.ac.vuw.ecs.swen225.gp22.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.App.*;
public enum MoveDirection{
    None(0,0),Left(-1,0),LeftUp(-1,-1),LeftDown(-1,1),Up(0,-1),Down(0,1),Right(1,0),RightUp(1,-1),RightDown(1,1)
    final int x;
    final int y;
    MoveDirection(int x,int y){
        this.x=x;
        this.y=y;
    }

    /**
     * Apply the move on the entity in this direction
     */
    public void move(ChapsChallenge game){
        //game.performAction();
    }

}