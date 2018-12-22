import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)

    private SearchNode sN;
    private int moves = 0;
    private boolean solvable = false;
    // use Comparator out of class, use Comparable in class
    private MinPQ<SearchNode> minPQ = new MinPQ<>(
            (SearchNode s1, SearchNode s2) -> s1.board.manhattan() + s1.moves - s2.board.manhattan()
                    - s2.moves);

    // SearchNode for A* algorithm tree
    private class SearchNode {
        private Board board;
        private int moves;
        private SearchNode parent;
        private boolean isTwin;

        public SearchNode(Board board, int moves, SearchNode parent, boolean isTwin) {
            this.board = board;
            this.moves = moves;
            this.parent = parent;
            this.isTwin = isTwin;
        }

    }

    private Stack<Board> solutionStack = new Stack<>();


    public Solver(Board initial) {
        minPQ.insert(new SearchNode(initial, 0, null, false));
        minPQ.insert(new SearchNode(initial.twin(), 0, null, true));
        while (true) {
            this.sN = minPQ.delMin();
            // terminate search when a goal searchNode dequeue
            if (sN.board.isGoal()) {
                if (sN.isTwin)
                    this.moves = -1;
                else {
                    this.solvable = true;
                    this.moves = sN.moves;
                    // trace back its parent boards as solution
                    solutionStack.push(sN.board);
                    while (sN.parent != null) {
                        sN = sN.parent;
                        solutionStack.push(sN.board);
                    }
                }
                break;
            }
            for (Board neighbor : sN.board.neighbors()) {
                if (sN.parent == null || !neighbor
                        .equals(sN.parent.board))       // don't enqueue a visited board
                    minPQ.insert(new SearchNode(neighbor, sN.moves + 1, sN, sN.isTwin));
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return this.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return this.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (this.solvable)
            return solutionStack;
        else
            return null;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        // In in = new In(args[0]);
        // int n = in.readInt();
        // int[][] blocks = new int[n][n];
        // for (int i = 0; i < n; i++)
        //     for (int j = 0; j < n; j++)
        //         blocks[i][j] = in.readInt();
        // Board initial = new Board(blocks);
        //
        // // solve the puzzle
        // Solver solver = new Solver(initial);
        //
        // // print solution to standard output
        // if (!solver.isSolvable())
        //     StdOut.println("No solution possible");
        // else {
        //     StdOut.println("Minimum number of moves = " + solver.moves());
        //     for (Board board : solver.solution())
        //         StdOut.println(board);
        // }
    }
}
