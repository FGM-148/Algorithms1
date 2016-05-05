package IV.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final short [][] blocks;
    private short hammingPriority;
    private int manhattanDistance;
    private byte xBlank;
    private byte yBlank;


    /* construct a blocks from an N-by-N array of blocks
    (where blocks[i][j] = block in row i, column j) */
    public Board(int[][] blocks) {

        if (blocks == null)
            throw new NullPointerException();

        this.blocks = new short[blocks.length][blocks.length];

        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++) {
                this.blocks[i][j] = (short) blocks[i][j];
                calculateValues(i, j);
            }
    }

    private Board(short[][] blocks) {
        if (blocks == null)
            throw new NullPointerException();

        this.blocks = new short[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++) {
                this.blocks[i][j] = blocks[i][j];
                calculateValues(i, j);
            }
    }

    private void calculateValues(int i, int j) {
        calculateHamming(i, j);
        calculateManhattan(i, j);

        if (blocks[i][j] == 0) {
            xBlank = (byte) i;
            yBlank = (byte) j;
        }

    }

    // blocks dimension N
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        return hammingPriority;
    }

    private void calculateHamming(int i, int j) {
        if (!(i == blocks.length-1 && j == blocks.length-1)) {
            if (blocks[i][j] != (i * blocks.length + j + 1))
                hammingPriority++;
        }
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattanDistance;
    }

    private void calculateManhattan(int i, int j) {
        int distance = 0;
        int k = 0;
        int l = 0;

        if (blocks[i][j] != 0) {
            distance = blocks[i][j] - (j + 1 + i * (blocks.length));

            if (distance != 0) {
                if (distance > 0) {
                    k = (j + i*blocks.length + distance) / blocks.length;
                    l = (j + i*blocks.length + distance) % blocks.length;
                } else{
                    k = (j + i*blocks.length - Math.abs(distance)) / blocks.length;
                    l = (j + i*blocks.length - Math.abs(distance)) % blocks.length;
                }
                manhattanDistance += Math.abs(i-k) + Math.abs(j-l);
            }
        }
    }

    // is this blocks the goal blocks?
    public boolean isGoal() {
        return hammingPriority == 0;
    }

    // a blocks that is obtained by exchanging any pair of blocks
    public Board twin() {

        exchangeFirstAndLast();
        Board twin = new Board(blocks);
        exchangeFirstAndLast();

        return twin;
    }

    private void exchangeFirstAndLast() {
        int i = 0, j = 0;
        int k = blocks.length-1;
        int l = k;

        if (blocks[i][j] == 0)
            j++;
        if (blocks[k][l] == 0)
            l--;

        short temp = blocks[i][j];
        blocks[i][j] = blocks[k][l];
        blocks[k][l] = temp;
    }

    private Board northNeighbour() {
        blocks[xBlank][yBlank] = blocks[xBlank-1][yBlank];
        blocks[xBlank-1][yBlank] = 0;

        Board result = new Board(blocks);

        blocks[xBlank-1][yBlank] = blocks[xBlank][yBlank];
        blocks[xBlank][yBlank] = 0;
        return result;
    }

    private Board southNeighbour() {
        blocks[xBlank][yBlank] = blocks[xBlank+1][yBlank];
        blocks[xBlank+1][yBlank] = 0;

        Board result = new Board(blocks);

        blocks[xBlank+1][yBlank] = blocks[xBlank][yBlank];
        blocks[xBlank][yBlank] = 0;

        return result;
    }

    private Board eastNeighbour() {
        blocks[xBlank][yBlank] = blocks[xBlank][yBlank+1];
        blocks[xBlank][yBlank+1] = 0;

        Board result = new Board(blocks);

        blocks[xBlank][yBlank+1] = blocks[xBlank][yBlank];
        blocks[xBlank][yBlank] = 0;

        return result;
    }

    private Board westNeighbour() {
        blocks[xBlank][yBlank] = blocks[xBlank][yBlank-1];
        blocks[xBlank][yBlank-1] = 0;

        Board result = new Board(blocks);

        blocks[xBlank][yBlank-1] = blocks[xBlank][yBlank];
        blocks[xBlank][yBlank] = 0;

        return result;
    }

    // does this blocks equal y?
    public boolean equals(Object y) {

        if (y == null)
            return false;

        Class yClass = y.getClass();

        if (!this.getClass().equals(yClass))
            return false;

        Board that = (Board) y;

        if (that.dimension() != blocks.length)
            return false;

        for (int i = 0; i < blocks.length; i++) {
            if (that.blocks[i].length != blocks.length)
                return false;
            for (int j = 0; j < blocks.length; j++)
                if (blocks[i][j] != that.blocks[i][j])
                    return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        List<Board> result = new ArrayList<>(4);

        if (xBlank > 0)
            result.add(northNeighbour());
        if (xBlank < blocks.length-1)
            result.add(southNeighbour());
        if (yBlank > 0)
            result.add(westNeighbour());
        if (yBlank < blocks.length-1)
            result.add(eastNeighbour());

        return result;
    }

    // string representation of this blocks (in the output format specified below)
    public String toString() {

        StringBuilder result = new StringBuilder(blocks.length + "\n");

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++)
                result.append(String.format("%2d ", blocks[i][j]));
        }
        return result.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {

    }
}
