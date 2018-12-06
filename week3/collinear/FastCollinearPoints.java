import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class FastCollinearPoints {
    // finds all line segments containing 4 or more points
    private final ArrayList<LineSegment> lsArr = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        checkNull(points);

        Point[] copy = points.clone();

        checkDuplicate(copy);

        for (Point pivot : points) {
            Arrays.sort(copy, pivot.slopeOrder());
            int j = 0;
            double previous = 0.0;
            LinkedList<Point> collinear = new LinkedList<Point>();
            for (Point p : copy) {
                if (j == 0 || p.slopeTo(pivot) != previous) {
                    if (collinear.size() >= 3) {
                        collinear.add(pivot);
                        Collections.sort(collinear);
                        if (pivot.equals(collinear.getFirst())) {
                            LineSegment ls = new LineSegment(collinear.getFirst(),
                                                             collinear.getLast());
                            lsArr.add(ls);
                        }

                    }
                    collinear.clear();
                }

                collinear.add(p);
                previous = p.slopeTo(pivot);
                j++;
            }

            // in case reach 4 or more in the last step
            if (collinear.size() >= 3) {
                collinear.add(pivot);
                Collections.sort(collinear);
                if (pivot.equals(collinear.getFirst())) {
                    LineSegment ls = new LineSegment(collinear.getFirst(),
                                                     collinear.getLast());
                    lsArr.add(ls);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
