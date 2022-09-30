package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import java.util.ArrayDeque;

public record RecordedLevel(String levelName, ArrayDeque<MoveDirection>moves) {
}
