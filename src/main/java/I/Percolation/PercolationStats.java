package I.Percolation;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] threshhold;
    private double T;

    public PercolationStats(int N, int T) {

        if (N < 1 || T < 1)
            throw new IllegalArgumentException();

        double totalNumber = N*N;
        double numberOfOpened = 0.0;
        this.T = T;
        threshhold = new double[T];
        Percolation grid;

        for (int i = 0; i < T; i++) {
            grid = new Percolation(N);
            while(!grid.percolates()) {
                int row = StdRandom.uniform(N)+1;
                int col = StdRandom.uniform(N)+1;
                if (!grid.isOpen(row, col)) {
                    grid.open(row, col);
                    numberOfOpened++;
                }
            }
            threshhold[i] = numberOfOpened / totalNumber;
            numberOfOpened = 0.0;
        }
    }

    public double mean() {
        return StdStats.mean(threshhold);
    }

    public double stddev()      {
        return StdStats.stddev(threshhold);
    }

    public double confidenceLo() {
        return mean() - 1.96*stddev() / Math.sqrt(T);
    }

    public double confidenceHi() {
        return mean() + 1.96*stddev() / Math.sqrt(T);
    }

    public static void main(String[] args) {

        PercolationStats stats = new PercolationStats(StdIn.readInt(),StdIn.readInt());

        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());
        StdOut.println("95% confidence interval = " + stats.confidenceLo() + " , " + stats.confidenceHi());

    }

}
