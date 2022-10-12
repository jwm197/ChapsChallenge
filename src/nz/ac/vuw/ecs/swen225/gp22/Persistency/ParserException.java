package nz.ac.vuw.ecs.swen225.gp22.Persistency;

/**
 * An exception to indicate any issues with parsing the level file
 *
 * @author Jia Wei Leong (300560651)
 */
public class ParserException extends RuntimeException{
    /**
     * Constructor for the exception
     * @param msg the message to send
     */
    public ParserException(String msg){
        super("Parsing failed! (" + msg + ")");
    }
}