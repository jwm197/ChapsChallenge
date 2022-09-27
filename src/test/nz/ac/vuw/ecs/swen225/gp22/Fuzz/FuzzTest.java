package test.nz.ac.vuw.ecs.swen225.gp22.Fuzz;
import nz.ac.vuw.ecs.swen225.gp22.App.*;

import static org.junit.Assert.fail;
import org.junit.Test;
import java.awt.event.KeyEvent;
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
            
            inputs.forEach(i->i.check());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //plays level 2
    @Test
    public void test2(){
        try {
            inputs.forEach(i->i.check());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //inputs to be tested
    static List<TestInput> inputs = List.of(new TestInput(randomMove(10)));
    record TestInput(List<Move> moves) implements Serializable{//a collection of inputs
        void check(){
            Game g = new FuzzTest().new Game();
            for (var m:moves){
                g.doMove(m);

            }
        };
    };
    //game object for test
    public class Game{
        void doMove(Move move){//moves player accordingly
            switch(move.ordinal()){
                case 0:
                ChapsChallenge.performAction("LEFT");
                case 1:
                ChapsChallenge.performAction("RIGHT");
                case 2:
                ChapsChallenge.performAction("UP");
                case 3:
                ChapsChallenge.performAction("DOWN");
            }
            
        }
    }
    enum Move{Left, Right, Up, Down}// player moves possible
    static List<Move> randomMove(int size){//generates random moves
        return IntStream.range(0, size)
        .map(i->new Random().nextInt(Move.values().length))
        .mapToObj(ei->Move.values()[ei])
        .toList();
    }

    
}
