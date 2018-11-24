import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        if (args.length == 0) throw new IllegalArgumentException();
        int n = Integer.parseInt(args[0]);
        In in = new In();

        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!in.isEmpty()) {
            String str = in.readString();
            rq.enqueue(str);
        }

        for (int i = 0; i < n; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}
