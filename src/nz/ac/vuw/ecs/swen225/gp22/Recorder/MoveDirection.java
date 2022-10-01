package nz.ac.vuw.ecs.swen225.gp22.Recorder;

public enum MoveDirection{
    NONE,LEFT,UP,DOWN,RIGHT;


    /**
     * Apply the move on the entity in this direction
     */
    public void move(ChapsChallenge game){
<<<<<<< HEAD
        if (this!=None){
            game.performAction(ths.toString());
=======
        if (this!=NONE){
            game.performAction(this.toString());
>>>>>>> origin/Recorder
        }

    }

}