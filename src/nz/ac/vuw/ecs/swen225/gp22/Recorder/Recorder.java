package nz.ac.vuw.ecs.swen225.gp22.Recorder;

import java.util.ArrayDeque;

/**
 * Recorder module
 */

class Recorder{
    ArrayDeque<RecordedMove>doneMoves=new ArrayDeque<>();
    ArrayDeque<RecordedMove>futureMoves=new ArrayDeque<>();
}