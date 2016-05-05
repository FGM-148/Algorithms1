package IV.Puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

class TreeSearch {

    private MinPQ<SearchNode> boardMinPQ = new MinPQ<>();
    private MinPQ<SearchNode> twinMinPQ = new MinPQ<>();
    private SearchNode root;
    private SearchNode twinRoot;
    private boolean solvable;
    private int solutionMoves;
    private LinkedList<Board> solutionList;

    TreeSearch(Board initial) {
            root = new SearchNode(initial);
            twinRoot = new SearchNode(initial.twin());

            boardMinPQ.insert(root);
            twinMinPQ.insert(twinRoot);

            searchForGoal();
    }

    public int getSolutionMoves() {
        return solutionMoves;
    }

    public LinkedList<Board> getSolutionList() {
        return solutionList;
    }

    private void searchForGoal() {

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
            setResult(dequedBoard, dequedTwin);
        }

    private void setResult(SearchNode dequedBoard, SearchNode dequedTwin) {
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
        } else
            solutionMoves = -1;
    }

    public boolean isSolvable() {
        return solvable;
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

}
