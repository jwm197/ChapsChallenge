package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**Saves all the bugs moves happening at the specific moment
 * @author Jacob McBride ID: 300537323 */
public class BugsMove{
    private final float time;
    private final Map<Integer,MoveDirection>moves=new HashMap<>();
    /**creates a Bugsmove which stores all the bugs on the boards move at that moment
     * @param time the time the moves occur at
     * @param moves a collection of the bug ids with the direction their moving in*/
    public BugsMove(float time,Map<Integer,MoveDirection>moves){
        this.time=time;
        this.moves.putAll(moves);
    }
    /**getter for time that the move occurs at*/
    public float getTime(){
        return time;
    }
    /**getter for moves which is a collection of the bug ids with the direction their moving in  */
    public Map<Integer,MoveDirection>getMoves(){
        return Collections.unmodifiableMap(moves);
    }


}