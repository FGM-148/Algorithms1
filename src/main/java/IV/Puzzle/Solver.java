package IV.Puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private TreeSearch initialBoardSearch;


    // find a solutionList to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        initialBoardSearch =  new TreeSearch(initial);
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return initialBoardSearch.isSolvable();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        return initialBoardSearch.getSolutionMoves();
    }

    // sequence of boards in a shortest solutionList; null if unsolvable
    public Iterable<Board> solution() {
        return initialBoardSearch.getSolutionList();
    }

    // ***PROVIDED BY API IN SPECIFICATION***
    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solutionList to standard output
        if (!solver.isSolvable())
            StdOut.println("No solutionList possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
