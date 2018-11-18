import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private int n;
    private int[] openedSizes;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        this.n = n;
        this.uf = new WeightedQuickUnionUF(n * n);
        this.openedSizes = new int[n * n];
    }

    private int posToIdx(int row, int col) {
        return (row - 1) * n + col - 1;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.openedSizes[this.posToIdx(row, col)] == 1;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return true;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return 1;
    }

    // does the system percolate?
    public boolean pecrcolates() {
        return true;
    }

    public static void main(String[] args) {
        int[] arr = new int[3];
        StdOut.println(arr[0]);
    }
}
