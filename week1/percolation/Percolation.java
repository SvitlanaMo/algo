import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF uf;
    private final int n;
    private int openedSitesNumber = 0;
    private boolean[] openedSitesArray;
    private boolean isPerlocated;
    private boolean[] topConnected;
    private boolean[] bottomConnected;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("invalid n");
        this.n = n;
        int size = n * n + 2;
        this.uf = new WeightedQuickUnionUF(size);
        this.openedSitesArray = new boolean[size];
        this.isPerlocated = false;
        this.topConnected = new boolean[size];
        this.bottomConnected = new boolean[size];

        // first element to connect top sites
        this.openedSitesArray[0] = true;

        // second element to connect bottom sites
        this.openedSitesArray[1] = true;
    }

    private int posToIdx(int row, int col) {
        // row 1 col 1 is mapping the idx 2
        int idx = (row - 1) * this.n + col - 1 + 2;
        return idx;
    }

    private int[][] getSurroundings(int row, int col) {
        int[][] surroundings = new int[][] {
                { row - 1, col },
                { row, col + 1 },
                { row + 1, col },
                { row, col - 1 }
        };
        return surroundings;
    }

    private boolean isTop(int row, int col) {
        return row == 1 && col <= this.n && col > 0;
    }

    private void connectToTop(int idx) {
        this.uf.union(0, idx);
    }

    private void connectToBottom(int idx) {
        this.uf.union(1, idx);
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
        int idx = this.posToIdx(row, col);
        if (!this.openedSitesArray[idx]) {
            this.openedSitesArray[idx] = true;
            this.openedSitesNumber++;
            boolean top = false;
            boolean bottom = false;

            // if (this.isBottom(row, col)) this.bottomOpenedSites[col - 1] = idx;

            int[][] surroundings = this.getSurroundings(row, col);
            for (int[] surround : surroundings) {
                int sRow = surround[0];
                int sCol = surround[1];
                if (this.inValidRange(sRow, sCol) && this.isOpen(sRow, sCol)) {
                    int sIdx = this.posToIdx(sRow, sCol);
                    if (this.topConnected[this.uf.find(idx)] || this.topConnected[this.uf
                            .find(sIdx)]) {
                        top = true;
                    }

                    if (this.bottomConnected[this.uf.find(idx)] || this.bottomConnected[this.uf
                            .find(sIdx)]) {
                        bottom = true;
                    }
                    this.uf.union(idx, sIdx);
                }
            }

            if (this.isTop(row, col)) top = true;
            if (this.isBottom(row, col)) bottom = true;

            this.topConnected[this.uf.find(idx)] = top;
            this.bottomConnected[this.uf.find(idx)] = bottom;

            if (top && bottom) {
                this.isPerlocated = true;
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!inValidRange(row, col)) throw new IllegalArgumentException("out of range");
        return this.openedSitesArray[this.posToIdx(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!inValidRange(row, col)) throw new IllegalArgumentException("out of range");
        int idx = this.posToIdx(row, col);
        return this.isOpen(row, col) && this.topConnected[this.uf.find(idx)];
    }

    // number of open sites
    public int numberOfOpenSites() {
        return this.openedSitesNumber;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.isPerlocated;
    }

    public static void main(String[] args) {
        // In in = new In(args[0]);      // input file
        // int n = in.readInt();         // n-by-n percolation system
        // Percolation perc = new Percolation(n);
        // int count = 1;
        // boolean cont = true;
        // while (!in.isEmpty() && cont) {
        //     int i = in.readInt();
        //     int j = in.readInt();
        //     perc.open(i, j);
        //
        //     StdOut.println("times: " + count);
        //     StdOut.println("isOpen: " + perc.isOpen(i, j));
        //     StdOut.println("isFull: " + perc.isFull(i, j));
        //     StdOut.println("percolates: " + perc.percolates());
        //     StdOut.println("No of Open Sites: " + perc.numberOfOpenSites());
        //
        //     count++;
        //     StdOut.println("==========>>>>");
        //     if (perc.percolates()) cont = false;
        // }
    }
}
