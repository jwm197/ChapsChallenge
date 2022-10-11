package test.nz.ac.vuw.ecs.swen225.gp22.Fuzz;
import nz.ac.vuw.ecs.swen225.gp22.App.*;

import static org.junit.Assert.fail;
import org.junit.Test;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.IntStream;


/**
 * Fuzz module 
 */
public class FuzzTest{
    //plays level 1
    @Test
    public void test1(){
        try {
            inputs1.forEach(i->((TestAuto) i).check());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //plays level 2
    @Test
    public void test2(){
        try {
            inputs2.forEach(i->((TestAuto) i).check());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //inputs to be tested
    static List<Serializable> inputs1 = List.of(new TestAuto("leve1.xml"));
    static List<Serializable> inputs2 = List.of(new TestInput("level1.xml", randomMoves(1000))); 
    
    /**
     * input to be tested
     */
    record TestInput(String level, List<Move> moves) implements Serializable{//a collection of inputs
        void check(){
            Game g = new FuzzTest().new Game();
            ChapsChallenge c = new ChapsChallenge();
            Stack<Move> stackOfMoves = new Stack<>();
            stackOfMoves.addAll(moves);
            c.gameScreen(level);
            while(!stackOfMoves.isEmpty()) {
            	if (!c.animating()) {
            		g.doMove(stackOfMoves.pop(), c);
            	}
            }
        }
    };
    
    record TestAuto(String level) implements Serializable{//a collection of inputs
        void check(){
            Game g = new FuzzTest().new Game();
            ChapsChallenge c = new ChapsChallenge();
            c.gameScreen(level);
            while(c.)
        }
    };
    
    
    /**
     * Matches with performaction in ChapsChallenge.java
     */
    public class Game{
        void doMove(Move move, ChapsChallenge g){//moves player accordingly   
            switch(move.ordinal()){
                case 0:
                g.performAction("LEFT");
                case 1:
                g.performAction("RIGHT");
                case 2:
                g.performAction("UP");
                case 3:
                g.performAction("DOWN");
                case 4:
                g.performAction("ESC");
                case 5:
                g.performAction("SPACE");
                case 6:
                g.performAction("CTRL-1");
                case 7:
                g.performAction("CTRL-2");
                case 8:
                g.performAction("CTRL-R");
                case 9:
                g.performAction("CTRl-S");
                case 10:
                g.performAction("CTRL-X");

            }
        }
    }
    enum Move {Left, Right, Up, Down, Pause, Continue, Load1, Load2, Load, Exit, Menu}// player moves possible
    /**
     * Generates a list of random moves.
     * @param size size of move list
     * @return list of moves
     */
    static List<Move> randomMoves(int size){//generates random moves
    	Random r = new Random();
        return IntStream.range(0, size)
        .map(i->r.nextInt(Move.values().length))
        .mapToObj(ei->Move.values()[ei%2])
        .toList();
    }

    
}
