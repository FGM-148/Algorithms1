package IV.Puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Solver {

    private boolean solvable;
    private LinkedList<Board> solutionList;
    private int solutionMoves;


    // find a solutionList to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        TreeSearch initialBoardSearch =  new TreeSearch(initial);

    }

    // is the initial board solvable?
    public boolean isSolvable() {

        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        return solutionMoves;
    }

    // sequence of boards in a shortest solutionList; null if unsolvable
    public Iterable<Board> solution() {

        Iterable<Board> result;
        return solutionList;
    }

    private class TreeSearch {

        MinPQ<SearchNode> boardMinPQ = new MinPQ<>();
        MinPQ<SearchNode> twinMinPQ = new MinPQ<>();
        SearchNode root;
        SearchNode twinRoot;

        TreeSearch(Board initial) {
            root = new SearchNode(initial);
            twinRoot = new SearchNode(initial.twin());

            boardMinPQ.insert(root);
            twinMinPQ.insert(twinRoot);

            searchForGoal();
        }

        void searchForGoal() {

            SearchNode dequedBoard = boardMinPQ.delMin();
            SearchNode dequedTwin = twinMinPQ.delMin();

            while (!dequedBoard.isGoal() && !dequedTwin.isGoal()) {
                for (SearchNode child : dequedBoard.children)
                    boardMinPQ.insert(child);

                for (SearchNode child : dequedTwin.children)
                    twinMinPQ.insert(child);

                dequedBoard = boardMinPQ.delMin();
                dequedTwin = twinMinPQ.delMin();
                dequedBoard.setChildren();
                dequedTwin.setChildren();
            }

            if (dequedTwin.isGoal())
                solvable = false;
            else
                solvable = true;

            if (isSolvable()) {
                solutionMoves = dequedBoard.moves;
                solutionList = new LinkedList<>();

                Iterator<Board> it = dequedBoard.iterator();

                while (it.hasNext())
                    solutionList.addFirst(it.next());
            }
            else
                solutionMoves = -1;
        }
    }

    private class SearchNode implements Comparable<SearchNode>, Iterable<Board>{

        SearchNode previous;
        Board board;
        final int moves;
        final int priority;
        final int rootPriority;
        Iterable<SearchNode> children;

        SearchNode(Board b, SearchNode prev, int m) {
            previous = prev;
            board = b;
            moves = m;
            priority = priorityFunction(board, moves);
            if (prev != null)
                rootPriority = prev.rootPriority;
            else
                rootPriority = priority;
        }

        SearchNode(Board b) {
            this(b, null, 0);
            ArrayList<SearchNode> childrenList = new ArrayList<>();
            for (Board neighbour : board.neighbors()) {
                    childrenList.add(new SearchNode(neighbour, this, moves+1));
            }
            children = childrenList;
        }

        int priorityFunction(Board board, int moves) {
            int currentPriority = board.manhattan() + moves;
            if (rootPriority > currentPriority)
                return rootPriority;
            else
                return currentPriority;
        }

        boolean isGoal() {
            return board.isGoal();
        }

        @Override
        public int compareTo(SearchNode that) {
            if (this.priority > that.priority)
                return 1;
            if (this.priority < that.priority)
                return -1;
            return 0;
        }

        void setChildren() {
            ArrayList<SearchNode> childrenList = new ArrayList<>();

            for (Board neighbour : board.neighbors()) {
                if(!neighbour.equals(previous.board))
                    childrenList.add(new SearchNode(neighbour, this, moves+1));
            }
            children = childrenList;
        }

        @Override
        public Iterator<Board> iterator() {
            return new Iterator<Board>() {
                private SearchNode current = SearchNode.this;

                @Override
                public boolean hasNext() {
                    return current!= null;
                }

                @Override
                public Board next() {
                    Board item = current.board;
                    current = current.previous;
                    return item;
                }
            };
        }
    }


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
