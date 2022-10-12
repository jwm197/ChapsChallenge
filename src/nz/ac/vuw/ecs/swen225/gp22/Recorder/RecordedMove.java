package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import java.util.HashMap;

public record RecordedMove(MoveDirection playerMoveDirection, float time, HashMap<Integer, MoveDirection> bugDirections) {

}
