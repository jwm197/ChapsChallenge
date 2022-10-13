package nz.ac.vuw.ecs.swen225.gp22.App;

/**
 * Implements an observer interface to allow for the polling of move events within fuzz testing
 * 
 * @author anfri
 * @author quhann
 *
 */

public interface Fuzzer {
	/**
	 * Does next move
	 */
	void nextMove();
	
	/**
	 * Ends the fuzz test
	 */
	void end();
	
	static Fuzzer NONE = new Fuzzer(){
		public void nextMove() {}
		public void end() {}
	};
}