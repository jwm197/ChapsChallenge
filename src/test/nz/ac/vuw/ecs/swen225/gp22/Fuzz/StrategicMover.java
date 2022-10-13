package test.nz.ac.vuw.ecs.swen225.gp22.Fuzz;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import nz.ac.vuw.ecs.swen225.gp22.App.ChapsChallenge;
import nz.ac.vuw.ecs.swen225.gp22.App.Pair;

/**
 * Make moves strategically
 *
 * @author quhann
 * @author anfri
 *
 */

public class StrategicMover implements Iterator<Move> {
	private ChapsChallenge c;
	private Deque<Move> currentMoves = new ArrayDeque<>();
	private List<List<NavTile>> navTiles = List.of();

	/**
	 * constructs StrategicMover
	 *
	 * @param c
	 */
	public StrategicMover(ChapsChallenge c) {
		this.c = c;
		System.out.println("MADE");
	}

	/**
	 * Breadth first searches game for path to a near item
	 *
	 * @param location pair of coordinates
	 * @param checker predicate that checks for items
	 * @return
	 */
	private Deque<Move> bfs(Pair<Integer, Integer> location, Predicate<NavTile> checker) {
		navTiles = c.tilesArray()
			    .stream()
                .map(ts ->
                    ts.stream()
                       .map(t -> new NavTile(t))
                       .toList()
                )
                .toList();

		Deque<Move> moves = new ArrayDeque<>();

		Deque<Pair<NavTile, Node>> nodes = new ArrayDeque<>();
		Node origin = new Node(Move.STILL, null);
		Node end = origin;

		Pair<Integer, Integer> position = location;

		nodes.offer(new Pair<>(navTiles.get(location.first()).get(location.second()), origin));

		while (!nodes.isEmpty()) {
			Pair<NavTile, Node> current = nodes.poll();
			NavTile tile = current.first();
			if (tile.isVisited()) continue;
			tile.visit();

			position = tile.inner();

			int x = position.first();
			int y = position.second();

			if (checker.test(tile)) {
				end = current.second();
				break;
			}
			if (c.canMoveTo(new Pair<>(x + 1, y))) {
				nodes.offer(new Pair<>(
						navTiles.get(x + 1).get(y),
						new Node(Move.RIGHT, current.second())
				));
	        }
			if (c.canMoveTo(new Pair<>(x, y + 1))) {
				nodes.offer(new Pair<>(
						navTiles.get(x).get(y + 1),
						new Node(Move.DOWN, current.second())
				));
	        }
			if (c.canMoveTo(new Pair<>(x - 1, y))) {
				nodes.offer(new Pair<>(
						navTiles.get(x-1).get(y),
						new Node(Move.LEFT, current.second())
				));
	        }
			if (c.canMoveTo(new Pair<>(x, y - 1))) {
				nodes.offer(new Pair<>(
						navTiles.get(x).get(y - 1),
						new Node(Move.UP, current.second())
				));
	        }
		}

		Node n = end;
		while (n != origin) {
			moves.addFirst(n.move());
			n = n.prev();
		}
		return moves;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public Move next() {
		if (currentMoves.isEmpty()) {
			currentMoves = bfs(c.getPlayerPosition(), t -> {
				if (c.treasureLeft() > 0) {
					return c.getItems().stream().anyMatch(i -> i.equals(t.inner()));
				} else {
					return c.getExit().equals(t.inner());
				}
			});
		}
		return currentMoves.poll();
	}
	/**
	 * Nodes to keep track of path to target
	 * @author anfri
	 *
	 */
	record Node(Move move, Node prev) {}
}
