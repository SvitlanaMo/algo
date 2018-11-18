import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private Percolation perc;
    private double openedSites = 0;
    private double[] means;
    private int currentTime = 0;
    private int trials;
    private int n;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        this.n = n;
        this.means = new double[trials];


        while (this.currentTime < trials) {
            this.perc = new Percolation(n);
            this.dig();
            this.currentTime++;
            this.openedSites = 0;
        }
    }

    private void dig() {
        while (!this.perc.percolates()) {
            int upperBound = this.n + 1;
            int lowerBound = 1;

            // return a random nteger uniformly [0, n), between 0 (inclusive) and n (exclusive)
            int nRow = StdRandom.uniform(lowerBound, upperBound);
            int nCol = StdRandom.uniform(lowerBound, upperBound);

            if (!this.perc.isOpen(nRow, nCol)) {
                this.openedSites++;
                this.perc.open(nRow, nCol);
            }
        }
        this.means[this.currentTime] = this.openedSites / (this.n * this.n);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.means);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.means);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - (1.96 * this.stddev() / Math.sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (1.96 * this.stddev() / Math.sqrt(this.trials));
    }

    // test client (described below)
    public static void main(String[] args) {
        if (args.length < 2) throw new IllegalArgumentException("args length wrong");
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        if (n <= 0 || t <= 0) throw new IllegalArgumentException("args format wrong");

        PercolationStats ps = new PercolationStats(n, t);
        StdOut.println("mean = " + ps.mean());
        StdOut.println("stddev = " + ps.stddev());
        StdOut.println(
                "interval = " + "[ " + ps.confidenceLo() + ", " + ps.confidenceHi() + " ]");
    }
}
