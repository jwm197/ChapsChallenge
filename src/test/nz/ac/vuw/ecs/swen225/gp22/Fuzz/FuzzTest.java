package test.nz.ac.vuw.ecs.swen225.gp22.Fuzz;
import nz.ac.vuw.ecs.swen225.gp22.App.*;

import static org.junit.Assert.fail;
import org.junit.Test;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.stream.IntStream;


/**
 * Fuzz module 
 */
public class FuzzTest{
    //plays level 1
    @Test
    public void test1(){
        try {
            inputs1.forEach(i->((TestAuto) i).check());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //plays level 2
    @Test
    public void test2(){
        try {
            inputs2.forEach(i->((TestAuto) i).check());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //inputs to be tested
    private static List<Serializable> inputs1 = List.of(new TestAuto("level1.xml"));
    private static List<Serializable> inputs2 = List.of(new TestAuto("level2.xml")); 
    
    /**
     * input to be tested
     */
    private record TestInput(String level, List<Move> moves) implements Serializable{//a collection of inputs
        void check(){
            Game g = new FuzzTest().new Game();
            ChapsChallenge c = new ChapsChallenge();
            Stack<Move> stackOfMoves = new Stack<>();
            stackOfMoves.addAll(moves);
            c.gameScreen(level);
            while(!stackOfMoves.isEmpty()) {
            	if (!c.animating()) {
            		g.doMove(stackOfMoves.pop(), c);
            	}
            }
        }
    };
    
    private record TestAuto(String level) implements Serializable{//a collection of inputs
        void check(){
            Game g = new FuzzTest().new Game();
            ChapsChallenge c = new ChapsChallenge();
            c.gameScreen(level);
            while(true) {
            	if(!c.animating()) {
            		List<Queue> paths = new ArrayList<>();
            		Queue<Move> q = new ArrayDeque<>();
            		int[][] position = c.getPlayerPosition();
            		boolean[][] visited = new boolean[15][15];
            		dfs(c, position.length, position[0].length, new ArrayDeque<Move>(), paths, visited);
            		int min = q.size();
            		for(Queue<Move> qs : paths) {
            			if(qs.size()<=min) {
            				q = qs;
            			}
            		}
            		if (q.peek()!=null) {
            			g.doMove(q.poll(), c);
            		};
            	}
            }
        }
    };
    
    private static void dfs(ChapsChallenge c, int x, int y, Queue<Move> q, List<Queue> paths, boolean[][] visited) {
    	if(visited[x][y]) {return;}
    	visited[x][y] = true;
    	int[][] keys = c.getKeys();
    	int[][] treasures = c.getTreasure();
    	int[][] ps = c.getPlayerPosition();
    	int[][] exit = c.getExitLockPosition();
    	
    	for(int i = 0; i < keys.length; i++) {
    		if(keys[i][0]==x&&keys[i][1]==y){
    			paths.add(q);
    			return;
    		}
    	}
    	for(int i = 0; i < treasures.length; i++) {
    		if(treasures[i][0]==x&&treasures[i][1]==y){
    			paths.add(q);
    			return;
    		}
    	}
    	if(c.treasureLeft()==0&&exit.length==x&&exit[0].length==y) {
    		paths.add(q);
    		return;
    	}
    	
	    	if(x-1!=-1&&c.canMoveTo(x-1, y)) {
	    		q.offer(Move.Left);
				dfs(c, x-1, y, q, paths, visited);
			}
	    	if(y-1!=-1&&c.canMoveTo(x, y-1)) {
	    		q.offer(Move.Down);
				dfs(c, x, y-1, q, paths, visited);
			}
	    	if(c.canMoveTo(x+1, y)) {
	    		q.offer(Move.Right);
				dfs(c, x+1, y, q, paths, visited);
			}
	    	if(c.canMoveTo(x, y+1)) {
	    		q.offer(Move.Up);
				dfs(c, x, y+1, q, paths, visited);
			}
	   }
    
    
    
    
    /**
     * Matches with performaction in ChapsChallenge.java
     */
    private class Game{
        void doMove(Move move, ChapsChallenge g){//interacts with game accordingly   
            switch(move.ordinal()){
                case 0:
                g.performAction("LEFT");
                case 1:
                g.performAction("RIGHT");
                case 2:
                g.performAction("UP");
                case 3:
                g.performAction("DOWN");
                case 4:
                g.performAction("ESC");
                case 5:
                g.performAction("SPACE");
                case 6:
                g.performAction("CTRL-1");
                case 7:
                g.performAction("CTRL-2");
                case 8:
                g.performAction("CTRL-R");
                case 9:
                g.performAction("CTRl-S");
                case 10:
                g.performAction("CTRL-X");

            }
        }
    }
    private enum Move {Left, Right, Up, Down, Pause, Continue, Load1, Load2, Load, Exit, Menu, None}// player moves possible
    /**
     * Generates a list of random moves.
     * @param size size of move list
     * @return list of moves
     */
    private static List<Move> randomMoves(int size){//generates random moves
    	Random r = new Random();
        return IntStream.range(0, size)
        .map(i->r.nextInt(Move.values().length))
        .mapToObj(ei->Move.values()[ei%4])
        .toList();
    }

    
}
