package test.nz.ac.vuw.ecs.swen225.gp22.Fuzz;

import java.util.List;

import nz.ac.vuw.ecs.swen225.gp22.App.Pair;


class NavTile {
	private Pair<Integer, Integer> inner;
    private boolean visited;
    NavTile(Pair<Integer, Integer> inner) {
        this.inner = inner;
    }

    int x() {
    	return inner.first();
    }

    int y() {
    	return inner.second();
    }

    void resetVisited() {
        visited = false;
    }

    void visit() {
        visited = true;
    }

    boolean isVisited() {
        return visited;
    }
}

