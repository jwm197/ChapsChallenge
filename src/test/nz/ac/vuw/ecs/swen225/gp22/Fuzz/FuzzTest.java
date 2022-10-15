package test.nz.ac.vuw.ecs.swen225.gp22.Fuzz;

import nz.ac.vuw.ecs.swen225.gp22.App.*;

import org.junit.Test;

//import static org.junit.jupiter.api.Assertions.fail;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;

/**
 * Fuzz module
 *
 */

/**
 *
 * @author quhann
 * @author anfri
 *
 */
public class FuzzTest {
	/**
	 * Plays level and way of
	 *
	 * @param level level name to be loaded
	 */
	static void performMovementTest(String level, boolean strategic) {
		ChapsChallenge c = new ChapsChallenge();
		c.gameScreen(level);
		if(strategic) {
			testMoves(new StrategicMover(c), c);
		} else {
			testMoves(new RandomMover(1000), c);
		}
	}

	/**
	 * Plays level 1 strategically
	 */
	@Test
	public void test1() {
		performMovementTest("level1.xml", true);
	}

	/**
	 * Plays level 2 strategically
	 */
	@Test
	public void test2() {
		performMovementTest("level2.xml", true);
	}

		/**
	 * Plays level 1 randomly
	 */
	@Test
	public void test1() {
		performMovementTest("level1.xml", false);
	}

	/**
	 * Plays level 2 randomly
	 */
	@Test
	public void test2() {
		performMovementTest("level2.xml", false);
	}

	/**
	 * Test the moves
	 *
	 * @param mover iterator containing moves to make
	 * @param c chapschallenge object
	 */
	public static void testMoves(Iterator<Move> mover, ChapsChallenge c){
		boolean running[] = {true};
		Fuzzer fuzzer = new Fuzzer() {

			@Override
			public void nextMove() {
				if (!mover.hasNext()) end();
				if (!c.animating()) c.performAction(mover.next().name());
			}

			@Override
			public void end() {
				running[0] = false;
			}
		};

		c.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}

			public void windowClosing(WindowEvent e) {
				fuzzer.end();
			}
		});
		c.bindFuzzer(fuzzer);
		fuzzer.nextMove();
		while (running[0]) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e1) {
			}
		}
	}
}

/**
 *
 * Possible moves for player
 *
 */

enum Move{
	UP,DOWN,LEFT,RIGHT,STILL;
}
