import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    // finds all line segments containing 4 points
    private final ArrayList<LineSegment> lsArr = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        checkDuplicate(points);
        int len = points.length;
        Point[] copy = points.clone();
        Arrays.sort(copy);
        int idx = 0;

        for (int i = idx; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    for (int m = k + 1; m < len; m++) {
                        if (copy[i].equals(copy[j]) || copy[i].equals(copy[k])
                                || copy[i].equals(copy[m]) || copy[j].equals(copy[k])
                                || copy[k].equals(copy[m])) {
                            throw new IllegalArgumentException();
                        }
                        if (copy[i] == null || copy[j] == null || copy[k] == null
                                || copy[m] == null) {
                            throw new IllegalArgumentException();
                        }

                        if (copy[i].slopeTo(copy[j]) == copy[i].slopeTo(copy[k])
                                && copy[i].slopeTo(copy[k]) == copy[i].slopeTo(copy[m])
                        ) {
                            LineSegment ls = new LineSegment(copy[i], copy[m]);
                            lsArr.add(ls);
                        }

                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.lsArr.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return this.lsArr.toArray(new LineSegment[this.lsArr.size()]);
    }


    private void checkNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("The array \"Points\" is null.");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException(
                        "The array \"Points\" contains null element.");
            }
        }
    }

    private void checkDuplicate(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Duplicate(s) found.");
            }
        }
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
