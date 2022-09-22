package test.nz.ac.vuw.ecs.swen225.gp22.Fuzz;
import nz.ac.vuw.ecs.swen225.gp22.App.*;
import static org.junit.Assert.fail;
import org.junit.Test;

import java.awt.Event;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.List;

import javax.swing.SwingUtilities;
/**
 * Fuzz module 
 */
public class FuzzTest{
    //plays level 1
    @Test
    public void test1(){
        try {
            SwingUtilities.invokeLater(ChapsChallenge::new);
            //ChapsChallenge g = new ChapsChallenge();
            /*g.LoadGame();
            g.GameHelp();
            g.GameScreen();
            g.MenuScreen();
            g.ViewControls();
            Event evt;
            Object what;
            g.action(evt, what);
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //plays level 2
    @Test
    public void test2(){
        try {
            Main.main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static List<TestInput> inputs;
    record TestInput() implements Serializable{
        void check(){
            //checks assertions
        };
    };
}
