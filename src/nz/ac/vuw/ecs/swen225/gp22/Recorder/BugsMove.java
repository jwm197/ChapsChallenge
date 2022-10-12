package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import java.util.HashMap;

public record BugsMove(float time, HashMap<Integer,MoveDirection>moves) {
}
