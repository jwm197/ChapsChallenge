package nz.ac.vuw.ecs.swen225.gp22.Persistency;

public class ParserException extends RuntimeException{
    public ParserException(String msg){
        super("Parsing failed! (" + msg + ")");
    }
}