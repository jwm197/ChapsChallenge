package nz.ac.vuw.ecs.swen225.gp22.Recorder;
import java.lang.Thread;
import java.util.ArrayDeque;


class MyRecorder{
    private ChapsChallenge game;
    static int tickSpeed=1;
    private ArrayDeque<MoveDirection> doneMoves=new ArrayDeque<>();
    private ArrayDeque<MoveDirection> futureMoves=new ArrayDeque<>();
    MyRecorder(ChapsChallenge game){
        this.game=game;
    }
    /**Set the tick speed if speed>0 otherwise throws an exception
     *
     * @param speed the new tick speed
     */
    public void setTickSpeed(int speed){
        if (speed<=0){
            throw new IllegalArgumentException("Speed has to be greater than 0");
        }
        tickSpeed=speed;
    }
    /**Get the next move to do from the future moves arraydeque
     * @return The next recorded move*/
    public MoveDirection getNextMove(){
        return futureMoves.pollFirst();
    }
    /**Get the next move to do from the future moves arraydeque without removing it from it
     * @return The next recorded move*/
    public MoveDirection peekNextMove(){
        return futureMoves.peekFirst();
    }
    /**Add last done move to the arraydeque of done moves
     * @param move the last done move*/
    public void setPreviousMove(MoveDirection move){
        doneMoves.add(move);
    }

    /**
     * Auto replay the saved game
     *
     * @throws InterruptedException if unable to complete
     */
    public void autoReplayGame() throws InterruptedException {
        while(peekNextMove()!=null){
            stepMove();
            Thread.sleep(1000/tickSpeed);
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










