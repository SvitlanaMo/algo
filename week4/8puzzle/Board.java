import edu.princeton.cs.algs4.Queue;

public class Board {

    private final char[] blocks;
    private final int dim;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.dim = blocks.length;
        int n = this.dim * this.dim;
        this.blocks = new char[n];
        for (int i = 0; i < n; i++) {
            this.blocks[i] = (char) blocks[i / dim][i % dim];
        }
    }

    // board dimension n
    public int dimension() {
        return this.dim;
    }

    // number of blocks out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < this.dim * this.dim; i++) {      // don't count blank block
            if (blocks[i] != (char) (i + 1) && blocks[i] != (char) 0) {
                hamming++;
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < this.dim * this.dim; i++) {
            if (blocks[i] != (char) (i + 1)
                    && blocks[i] != (char) 0) {     // don't count blank block
                int blockVal = (int) blocks[i] - 1;
                manhattan += Math.abs(blockVal / this.dim - i / this.dim) + Math
                        .abs(blockVal % this.dim - i % this.dim);
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        if (blocks[0] == (char) 0 || blocks[1] == (char) 0)
            return swap(2, 3);
        else
            return swap(0, 1);
    }

    private Board swap(int i, int j) {      // swap two blocks of index i and j
        int[][] newBlocks = new int[this.dim][this.dim];
        for (int k = 0; k < this.dim * this.dim; k++) {
            newBlocks[k / this.dim][k % this.dim] = (int) this.blocks[k];
        }
        int temp = newBlocks[i / this.dim][i % this.dim];
        newBlocks[i / this.dim][i % this.dim] = newBlocks[j / this.dim][j % this.dim];
        newBlocks[j / this.dim][j % this.dim] = temp;
        return new Board(newBlocks);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension()) return false;
        for (int i = 0; i < this.dim * this.dim; i++) {
            if (this.blocks[i] != that.blocks[i])
                return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighborQueue = new Queue<Board>();
        int blankPos = 0;
        // find the position of blank block
        for (int i = 0; i < this.dim * this.dim; i++) {
            if (this.blocks[i] == (char) 0) {
                blankPos = i;
                break;
            }
        }
        if (blankPos / this.dim != 0)
            neighborQueue.enqueue(swap(blankPos, blankPos - this.dim));
        if (blankPos / this.dim != this.dim - 1)
            neighborQueue.enqueue(swap(blankPos, blankPos + this.dim));
        if (blankPos % this.dim != 0)
            neighborQueue.enqueue(swap(blankPos, blankPos - 1));
        if (blankPos % this.dim != this.dim - 1)
            neighborQueue.enqueue(swap(blankPos, blankPos + 1));
        return neighborQueue;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.dim + "\n");
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < this.dim; j++) {
                int cell = (int) blocks[i * this.dim + j % this.dim];
                s.append(String.format("%2d ", cell));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        // int[][] test = { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        // Board testBoard2 = new Board(test);
        // // read in the board specified in the filename
        // In in = new In(args[0]);
        // int n = in.readInt();
        // int[][] tiles = new int[n][n];
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //         tiles[i][j] = in.readInt();
        //     }
        // }
        // Board testBoard = new Board(tiles);
        // StdOut.println(testBoard);
        // StdOut.println(testBoard.swap(1, 2));
        // StdOut.println(testBoard.twin());
        // StdOut.println(testBoard.hamming());
        // StdOut.println(testBoard.manhattan());
        // StdOut.println(testBoard.isGoal());
        // StdOut.println(testBoard.neighbors());
        // StdOut.println(testBoard.equals(testBoard2));

    }
}
