package test.nz.ac.vuw.ecs.swen225.gp22.Fuzz;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Random;


/**
 * Makes random moves
 *
 * @author quhann
 * @author anfri
 *
 */
public class RandomMover implements Iterator<Move> {
	private Deque<Move> moves = new ArrayDeque<>();

	private static final Random random = new Random();


	/**
	 * Constructs RandomMover
	 *
	 * @param count
	 */
	public RandomMover(int count) {
		moves.addAll(
			random.ints(count, 0, Move.values().length-1)
			      .mapToObj(i -> Move.values()[i])
			      .toList());
	}

	@Override
	public boolean hasNext() {
		return !moves.isEmpty();
	}

	@Override
	public Move next() {
		return moves.poll();
	}
}
