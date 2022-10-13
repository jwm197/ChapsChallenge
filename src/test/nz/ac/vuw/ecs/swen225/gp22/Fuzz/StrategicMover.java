package test.nz.ac.vuw.ecs.swen225.gp22.Fuzz;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import nz.ac.vuw.ecs.swen225.gp22.App.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.gp22.App.Pair;

public class StrategicMover implements Iterator<Move> {
	private Deque<Move> moves = new ArrayDeque<>();
	private List<List<NavTile>> navTiles;
	private boolean pathfinding = true;

	public StrategicMover(ChapsChallenge c) {
	}

	private Deque<Move> bfs(ChapsChallenge c, Pair<Integer, Integer> location) {
		Deque<Move> moves = new ArrayDeque<>();
		bfs(c, location, moves);
		return moves;
	}

	private boolean bfs(ChapsChallenge c, Pair<Integer, Integer> location, Deque<Move> moves) {

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
