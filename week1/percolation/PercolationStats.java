import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private double openedSites = 0;
    private double[] means;
    private final int trials;
    private final int n;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("args format wrong");
        this.trials = trials;
        this.n = n;
        this.means = new double[trials];
        int currentTime = 0;

        while (currentTime < trials) {
            Percolation perc = new Percolation(n);
            this.dig(perc, currentTime);
            currentTime++;
            this.openedSites = 0;
        }
    }

    private void dig(Percolation perc, int currentTime) {
        while (!perc.percolates()) {
            int upperBound = this.n + 1;
            int lowerBound = 1;

            // return a random nteger uniformly [0, n), between 0 (inclusive) and n (exclusive)
            int nRow = StdRandom.uniform(lowerBound, upperBound);
            int nCol = StdRandom.uniform(lowerBound, upperBound);

            if (!perc.isOpen(nRow, nCol)) {
                this.openedSites++;
                perc.open(nRow, nCol);
            }
        }
        this.means[currentTime] = this.openedSites / (this.n * this.n);
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
        return this.mean() - (PercolationStats.CONFIDENCE_95 * this.stddev() / Math
                .sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (PercolationStats.CONFIDENCE_95 * this.stddev() / Math
                .sqrt(this.trials));
    }

    // test client (described below)
    public static void main(String[] args) {
        if (args.length < 2) throw new IllegalArgumentException("args length wrong");
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        StdOut.println("mean = " + ps.mean());
        StdOut.println("stddev = " + ps.stddev());
        StdOut.println(
                "interval = " + "[ " + ps.confidenceLo() + ", " + ps.confidenceHi() + " ]");
    }
}
