package test.nz.ac.vuw.ecs.swen225.gp22.Fuzz;

import nz.ac.vuw.ecs.swen225.gp22.App.Pair;

/**
 *
 *Tiles which represent game tiles to help in path search
 *
 *
 * @author quhann
 * @author anfri
 *
 */
class NavTile {
	private Pair<Integer, Integer> inner;
    private boolean visited;
    /**
     * Constructs a NavTile
     *
     * @param inner pair of coordinates
     */
    NavTile(Pair<Integer, Integer> inner) {
        this.inner = inner;
    }
    /**
     *
     *
     * @return pair of coordinates
     */

    Pair<Integer, Integer> inner() {
    	return inner;
    }

    /**
     * sets visited to false
     */
    void resetVisited() {
        visited = false;
    }
    /**
     * sets visited to true
     */
    void visit() {
        visited = true;
    }

    /**
     *
     * @return boolean visited
     */
    boolean isVisited() {
        return visited;
    }
}

