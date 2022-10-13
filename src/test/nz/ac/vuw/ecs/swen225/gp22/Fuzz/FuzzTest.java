package test.nz.ac.vuw.ecs.swen225.gp22.Fuzz;

import nz.ac.vuw.ecs.swen225.gp22.App.*;

import org.junit.Test;

//import static org.junit.jupiter.api.Assertions.fail;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

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
	 * DESCRIPTIOM
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
	@Test
	public void test1() {
		performMovementTest("level1.xml", true);
	}

	@Test
	public void test2() {
		performMovementTest("level2.xml", true);
	}

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

	public static void attemptLevel(ChapsChallenge c) {

	}

}

enum Move{
	UP,DOWN,LEFT,RIGHT,STILL;
}
