import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private final SET<Point2D> pointsTree = new SET<Point2D>();

    // construct an empty set of points
    public PointSET() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return this.pointsTree.isEmpty();
    }

    // number of points in the set
    public int size() {
        return this.pointsTree.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!this.pointsTree.contains(p)) {
            this.pointsTree.add(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return this.pointsTree.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : this.pointsTree) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("RectHV rect is not illegal!");
        ArrayList<Point2D> li = new ArrayList<Point2D>();
        for (Point2D point : this.pointsTree) {
            if (rect.contains(point)) {
                li.add(point);
            }
        }
        return li;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (this.pointsTree.size() == 0) return null;
        Point2D minPoint = null;

        double minDistance = Double.MAX_VALUE;

        for (Point2D point : this.pointsTree) {
            double distance = p.distanceTo(point);
            if (distance < minDistance) {
                minDistance = distance;
                minPoint = point;
            }
        }

        return minPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
