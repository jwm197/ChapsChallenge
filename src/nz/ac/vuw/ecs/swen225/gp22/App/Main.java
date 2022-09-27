package nz.ac.vuw.ecs.swen225.gp22.App;

import javax.swing.SwingUtilities;

/**
 *  App module
 */


/**
 * The starting point of the game.
 */
public class Main {
    public static void main(String[] args) throws Exception {
    	SwingUtilities.invokeLater(ChapsChallenge::new);
    }
}
