package test.nz.ac.vuw.ecs.swen225.gp22.Fuzz;

import nz.ac.vuw.ecs.swen225.gp22.App.*;

import org.junit.Test;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * Fuzz module
 */
public class FuzzTest {

    private static Random r = new Random();

    // plays level 1
    @Test
    public void test1() {
        try {
            inputs1.forEach(i -> ((TestAuto) i).check());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // plays level 2
    @Test
    public void test2() {
        try {
            inputs2.forEach(i -> ((TestAuto) i).check());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // inputs to be tested
    private static List<Serializable> inputs1 = List.of(new TestAuto("level1.xml"));
    private static List<Serializable> inputs2 = List.of(new TestAuto("level2.xml"));

    /**
     * input to be tested
     */
    private record TestInput(String level, List<Move> moves) implements Serializable {// a collection of inputs
        void check() {
            Game g = new FuzzTest().new Game();
            ChapsChallenge c = new ChapsChallenge();
            Stack<Move> stackOfMoves = new Stack<>();
            stackOfMoves.addAll(moves);
            c.gameScreen(level);
            while (!stackOfMoves.isEmpty()) {
                if (!c.animating()) {
                    g.doMove(stackOfMoves.pop(), c);
                }
            }
        }
    };

    private record TestAuto(String level) implements Serializable {// a collection of inputs
        void check() {
            Game g = new FuzzTest().new Game();
            ChapsChallenge c = new ChapsChallenge();
            c.gameScreen(level);
            while (!c.animating()) {
                //if (!c.animating()) {
                    List<Queue<Move>> paths = new ArrayList<>();
                    int[][] position = c.getPlayerPosition();

                    dfs(c, position[0][0], position[0][1], new ArrayDeque<>(), paths, new boolean[30][30], Move.Left);

                    Queue<Move> q = paths.get(0);
                    int min = q.size();
                    for (Queue<Move> qs : paths) {
                        qs.poll();
                        if (qs.size() < min && qs.size() > 0) {
                            q = qs;
                        }
                        System.out.println("Q");
                        for (Move m : qs) {
                            System.out.print(m + ", ");
                        }
                        System.out.println("E");
                    }
                    if (q.peek() != null) {
                        System.out.println(q.peek());
                        g.doMove(q.poll(), c);
                    }
                    ;
                /*} else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                    }*/
                //}
            }
        }
    };

    private static void dfs(ChapsChallenge c, int x, int y, Queue<Move> q, List<Queue<Move>> paths, boolean[][] visited,
            Move m) {
        if (visited[x][y]) {
            return;
        }
        visited[x][y] = true;
        Queue<Move> qc = new ArrayDeque<>(q);
        qc.offer(m);
        int[][] keys = c.getKeys();
        int[][] treasures = c.getTreasure();
        int[][] exit = c.getExitLockPosition();
        boolean[][] visitedC = new boolean[30][30];

        for (int i = 0; i < keys.length; i++) {
            if (keys[i][0] == x && keys[i][1] == y) {
                paths.add(qc);
                return;
            }
        }
        for (int i = 0; i < treasures.length; i++) {
            if (treasures[i][0] == x && treasures[i][1] == y) {
                paths.add(qc);
                return;
            }
        }
        if (c.treasureLeft() == 0 && exit[0][0] == x && exit[0][1] == y) {
            paths.add(qc);
            return;
        }

        if (x - 1 != -1 && c.canMoveTo(x - 1, y)) {
            dfs(c, x - 1, y, qc, paths, visitedC, Move.Left);
        }
        if (y - 1 != -1 && c.canMoveTo(x, y - 1)) {
            dfs(c, x, y - 1, qc, paths, visitedC, Move.Up);
        }
        if (c.canMoveTo(x + 1, y)) {
            dfs(c, x + 1, y, qc, paths, visitedC, Move.Right);
        }
        if (c.canMoveTo(x, y + 1)) {
            dfs(c, x, y + 1, qc, paths, visitedC, Move.Down);
        }
    }

    /**
     * Matches with performaction in ChapsChallenge.java
     */
    private class Game {
        void doMove(Move move, ChapsChallenge g) {// interacts with game accordingly
            g.performAction(move.toString().toUpperCase());
        }
    }

    private enum Move {
        Left, Right, Up, Down
    }// player moves possible

    /**
     * Generates a list of random moves.
     * 
     * @param size size of move list
     * @return list of moves
     */
    private static List<Move> randomMoves(int size) {// generates random moves
        return IntStream.range(0, size)
                .map(i -> r.nextInt(Move.values().length))
                .mapToObj(ei -> Move.values()[ei])
                .toList();
    }

}
