import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private int n;
    private int openedSitesNumber = 0;
    private int[] openedSitesArray;

    // up, right, down, left
    private int[][] directions = new int[][] {
            { -1, 0 },
            { 0, 1 },
            { 1, 0 },
            { 0, -1 }
    };

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("invalid n");
        this.n = n;
        int size = n * n + 2;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.openedSitesArray = new int[n * n + 2];

        // first element to connect top sites
        this.openedSitesArray[0] = 1;

        // second element to connect bottom sites
        this.openedSitesArray[1] = 1;
    }

    private int posToIdx(int row, int col) {
        // row 1 col 1 is mapping the idx 2
        int idx = (row - 1) * this.n + col - 1 + 2;
        return idx;
    }

    private int[][] getSurroundings(int row, int col) {
        int[][] surroundings = new int[4][4];
        int idx = 0;
        for (int[] direction : this.directions) {
            surroundings[idx] = new int[] { row + direction[0], col + direction[1] };
            idx++;
        }
        return surroundings;
    }

    private boolean isTop(int row, int col) {
        return row == 1 && col <= this.n && col > 0;
    }

    private void connectToTop(int row, int col) {
        this.uf.union(0, this.posToIdx(row, col));
    }

    private void connectToBottom(int row, int col) {
        this.uf.union(1, this.posToIdx(row, col));
    }

    private boolean isBottom(int row, int col) {
        return row == this.n && col <= this.n && col > 0;
    }

    private boolean inValidRange(int row, int col) {
        return row > 0 && col > 0 && row <= this.n && col <= this.n;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!inValidRange(row, col)) throw new IllegalArgumentException("out of range");
        this.openedSitesArray[this.posToIdx(row, col)] = 1;
        this.openedSitesNumber++;

        int[][] surroundings = this.getSurroundings(row, col);
        for (int[] surround : surroundings) {
            int sRow = surround[0];
            int sCol = surround[1];
            if (sRow > 0 && sRow <= this.n && sCol > 0 && sCol <= this.n && this.isOpen(sRow, sCol))
                this.uf.union(this.posToIdx(row, col), this.posToIdx(sRow, sCol));
        }

        if (this.isTop(row, col)) this.connectToTop(row, col);

        // we can not connect to bottom directly, we need to check if the site is "FULL" before connecting to bottom site.
        if (this.isBottom(row, col) && this.uf.connected(0, this.posToIdx(row, col))) {
            this.connectToBottom(row, col);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!inValidRange(row, col)) throw new IllegalArgumentException("out of range");
        return this.openedSitesArray[this.posToIdx(row, col)] == 1;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!inValidRange(row, col)) throw new IllegalArgumentException("out of range");
        int idx = this.posToIdx(row, col);
        // the following line to solve the case
        // where first open bottom site then all the way up to the top
        // like I shape, it will not percolate immediately
        // cause it didn't call open on bottom again.
        if (this.isBottom(row, col) && this.uf.connected(0, idx)) this.connectToBottom(row, col);
        return this.isOpen(row, col) && this.uf.connected(0, idx);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return this.openedSitesNumber;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.uf.connected(0, 1);
    }

    public static void main(String[] args) {
        // Percolation perc = new Percolation(6);
        // perc.open(1, 6);
        // perc.open(2, 6);
    }
}
