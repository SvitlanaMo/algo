import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    // finds all line segments containing 4 points
    private int numberOfSeg = 0;
    private LineSegment[] lsArr;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        int len = points.length;
        this.lsArr = new LineSegment[len / 4];
        Arrays.sort(points);
        int idx = 0;

        for (int i = idx; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    for (int m = k + 1; m < len; m++) {
                        if (points[i].equals(points[j]) || points[i].equals(points[k])
                                || points[i].equals(points[m]) || points[j].equals(points[k])
                                || points[k].equals(points[m])) {
                            throw new IllegalArgumentException();
                        }
                        if (points[i] == null || points[i] == null
                                || points[i] == null || points[j] == null
                                || points[k] == null) {
                            throw new IllegalArgumentException();
                        }

                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                                && points[i].slopeTo(points[k]) == points[i].slopeTo(points[m])
                        ) {
                            LineSegment ls = new LineSegment(points[i], points[m]);
                            this.lsArr[this.numberOfSeg] = ls;
                            this.numberOfSeg++;
                        }

                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.numberOfSeg;
    }

    // the line segments
    public LineSegment[] segments() {
        return this.lsArr;
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
