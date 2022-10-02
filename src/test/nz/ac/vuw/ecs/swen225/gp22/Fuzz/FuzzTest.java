package test.nz.ac.vuw.ecs.swen225.gp22.Fuzz;
import nz.ac.vuw.ecs.swen225.gp22.App.*;

import static org.junit.Assert.fail;
import org.junit.Test;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;


/**
 * Fuzz module 
 */
public class FuzzTest{
    //plays level 1
    @Test
    public void test1(){
        try {
            inputs1.forEach(i->i.check());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //plays level 2
    @Test
    public void test2(){
        try {
            inputs2.forEach(i->i.check());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //inputs to be tested
    
    static List<TestInput> inputs1 = List.of(new TestInput(Move.Load1, randomMoves(100)));
    static List<TestInput> inputs2 = List.of(new TestInput(Move.Load2, randomMoves(100)));
    record TestInput(Move level, List<Move> moves) implements Serializable{//a collection of inputs
        void check(){
            Game g = new FuzzTest().new Game();
            ChapsChallenge c = new ChapsChallenge();
            g.doMove(level, c);
            for (var m:moves){
                g.doMove(m, c);
            }
            g.doMove(Move.Exit, c);
        };
    };
    //game object for test
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
                g.performAction("CTRL-1");
                case 6:
                g.performAction("CTRL-2");
                case 7:
                g.performAction("CTRL-R");
                case 8:
                g.performAction("CTRl-S");
                case 9:
                g.performAction("CTRL-X");

            }
        }
    }
    interface Input{}
    enum Move implements Input {Left, Right, Up, Down, Pause, Load1, Load2, Load, Exit, Menu}// player moves possible
    static List<Move> randomMoves(int size){//generates random moves
        return IntStream.range(0, size)
        .map(i->new Random().nextInt(3))
        .mapToObj(ei->Move.values()[ei])
        .toList();
    }

    
}
