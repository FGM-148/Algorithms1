package I.Percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF unionFind;
    private int sizeOfGrid;
    private byte[][] grid;

    public Percolation(int N) {
        if (N<1)
            throw new IllegalArgumentException();
        unionFind = new WeightedQuickUnionUF(N*N+2);
        grid = new byte[N+1][N+1];
        sizeOfGrid = N;
    }

    public void open(int i, int j) {

        if (validIndices(i, j)) {
            if (!isOpen(i, j)) {
                setOpen(i, j);
                if (i==1) {
                    unionFind.union(0, xyTo1D(i, j));
                    setFull(i, j);
                }
                if (i == sizeOfGrid) {
                    unionFind.union(sizeOfGrid*sizeOfGrid+1, xyTo1D(i, j));
                }
                updateNeighbours(i, j);
                if (isFull(i, j)) //if the site is full after the update, change all open sites in the area of the site(i, j) to full
                    populateFull(i, j);
            }
        }
        else
            throw new IndexOutOfBoundsException();
    }

    public boolean isOpen(int i, int j) {
        if (!validIndices(i, j))
            throw new IndexOutOfBoundsException();
        return grid[i][j] > 0;
    }

    public boolean isFull(int i, int j) {
        if (!validIndices(i, j))
            throw new IndexOutOfBoundsException();
        return grid[i][j] > 1;
    }

    private void setOpen(int i, int j) {
        if (!validIndices(i, j))
            throw new IndexOutOfBoundsException();
        grid[i][j] = 1;
    }

    private void setFull(int i, int j) {
        if (!validIndices(i, j))
            throw new IndexOutOfBoundsException();
        grid[i][j] = 2;
    }

    public boolean percolates() {
        return unionFind.connected(0, sizeOfGrid*sizeOfGrid+1);
    }

    private int xyTo1D(int i, int j) {
        return (i-1) * sizeOfGrid + j;
    }

    private boolean validIndices(int i, int j){
        if (i<= sizeOfGrid && j<=sizeOfGrid && j > 0 && i > 0)
            return true;
        else
            return false;
    }

    private void updateNeighbours(int i, int j) { //checks all neighbouring sites, add them if they're open

        updateNorthSite(i, j);
        updateWestSite(i, j);
        updateSouthSite(i, j);
        updateEastSite(i, j);

    }

    private void updateEastSite(int i, int j) {
        if (validIndices(i,j+1)&&isOpen(i,j+1)) {
            unionFind.union(xyTo1D(i, j+1), xyTo1D(i, j));
            if (isFull(i, j+1)) {
                setFull(i, j);
            }
        }
    }

    private void updateSouthSite(int i, int j) {
        if (validIndices(i+1,j)&&isOpen(i+1,j)) {
            unionFind.union(xyTo1D(i+1, j), xyTo1D(i, j));
            if (isFull(i+1, j)) {
                setFull(i, j);
            }
        }
    }

    private void updateNorthSite(int i, int j) {
        if (validIndices(i-1,j)&&isOpen(i-1,j)) {
            unionFind.union(xyTo1D(i-1, j), xyTo1D(i, j));
            if (isFull(i-1, j)) {
                setFull(i, j);
            }
        }
    }

    private void updateWestSite(int i, int j) {
        if (validIndices(i,j-1) && isOpen(i,j-1)) {
            unionFind.union(xyTo1D(i, j-1), xyTo1D(i, j));
            if (isFull(i, j-1)) {
                setFull(i, j);
            }
        }
    }

    // One recursive function calling on its self, causes StackOverflow error occur randomly on the grading site for huge problems.
    private void populateFull(int i, int j) {

        if (validIndices(i+1,j)&&isOpen(i+1,j)&&!isFull(i+1,j))
            fillingDown(i+1,j);

        if (validIndices(i-1,j)&&isOpen(i-1,j)&&!isFull(i-1,j))
            fillingUp(i-1, j);

        if (validIndices(i,j+1)&&isOpen(i,j+1)&&!isFull(i,j+1))
            fillingRight(i, j+1);

        if (validIndices(i,j-1)&&isOpen(i,j-1)&&!isFull(i,j-1))
            fillingLeft(i, j-1);
    }

    private void fillingLeft(int i, int j) {
        final int rec = j;
        do {
            grid[i][j--] = 2;
        }
        while(validIndices(i, j) && !isFull(i,j) && isOpen(i,j));

        while(++j<=rec) {
            if (validIndices(i + 1, j) && isOpen(i + 1, j) && !isFull(i + 1, j) && isOpen(i + 1, j)) {
                fillingDown(i + 1, j);
            }
            if (validIndices(i - 1, j) && isOpen(i - 1, j) && !isFull(i - 1, j) && isOpen(i - 1, j)) {
                fillingUp(i - 1, j);
            }
        }
    }

    private void fillingRight(int i, int j) {
        final int rec = j;
        do {
            grid[i][j++] = 2;
        }
        while(validIndices(i, j) && !isFull(i,j) && isOpen(i,j));
        while(--j>=rec) {
            if (validIndices(i + 1, j) && isOpen(i + 1, j) && !isFull(i + 1, j) && isOpen(i + 1, j)) {
                fillingDown(i + 1, j);
            }
            if (validIndices(i - 1, j) && isOpen(i - 1, j) && !isFull(i - 1, j) && isOpen(i - 1, j)) {
                fillingUp(i - 1, j);
            }
        }
    }

    private void fillingDown(int i, int j) {
        final int rec = i;
        do {
            grid[i++][j] = 2;
        }
        while(validIndices(i, j) && !isFull(i,j) && isOpen(i,j));

        while(--i>=rec) {
            if (validIndices(i, j - 1) && isOpen(i, j - 1) && !isFull(i, j - 1) && isOpen(i, j - 1)) {
                fillingLeft(i, j - 1);
            }
            if (validIndices(i, j + 1) && isOpen(i, j + 1) && !isFull(i, j + 1) && isOpen(i, j + 1)) {
                fillingRight(i, j + 1);
            }
        }
    }

    private void fillingUp(int i, int j) {
        final int rec = i;
        do {
            grid[i--][j] = 2;
        }
        while(validIndices(i, j) && !isFull(i,j)  && isOpen(i,j));

        while(++i<=rec) {
            if (validIndices(i, j - 1) && isOpen(i, j - 1) && !isFull(i, j - 1) && isOpen(i, j - 1)) {
                fillingLeft(i, j - 1);
            }
            if (validIndices(i, j + 1) && isOpen(i, j + 1) && !isFull(i, j + 1) && isOpen(i, j + 1)) {
                fillingRight(i, j + 1);
            }
        }
    }

    public static void main(String[] args){

    }

}
