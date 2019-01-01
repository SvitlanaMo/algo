import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;


public class KdTree {
    private Node root = null;
    private int size;

    public KdTree() {
        this.size = 0;
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean compareX;
    }


    // is the set empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // number of points in the set
    public int size() {
        return this.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!this.contains(p)) {
            if (this.root == null) {
                Node newNode = new Node();
                newNode.p = p;
                newNode.compareX = true;
                newNode.rect = new RectHV(0, 0, 1, 1);
                this.root = newNode;
                this.size++;
            }
            else {
                this.insertRec(p, this.root, null, true);
                this.size++;
            }
        }

    }

    private Node insertRec(Point2D p, Node current, Node parent, boolean compareX) {
        if (current == null) {
            Node newNode = new Node();
            newNode.p = p;
            newNode.compareX = compareX;

            if (parent.compareX) {
                int cmp = Double.compare(p.x(), parent.p.x());
                if (cmp < 0) {
                    // left side of the vertical line
                    newNode.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.p.x(),
                                              parent.rect.ymax());
                }
                else if (cmp > 0) {
                    // right side of the vertical line
                    newNode.rect = new RectHV(parent.p.x(), parent.rect.ymin(), parent.rect.xmax(),
                                              parent.rect.ymax());
                }
                else {
                    newNode.rect = parent.rect;
                }
            }
            else {
                int cmp = Double.compare(p.y(), parent.p.y());
                if (cmp < 0) {
                    // below the horizontal line
                    newNode.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                              parent.rect.xmax(),
                                              parent.p.y());
                }
                else if (cmp > 0) {
                    // above the horizontal line
                    newNode.rect = new RectHV(parent.rect.xmin(), parent.p.y(), parent.rect.xmax(),
                                              parent.rect.ymax());
                }
                else {
                    newNode.rect = parent.rect;
                }
            }

            return newNode;
        }

        if (compareX) {
            int cmp = Double.compare(p.x(), current.p.x());
            if (cmp < 0) {
                current.lb = this.insertRec(p, current.lb, current, !compareX);
                return current;
            }
            else if (cmp > 0) {
                current.rt = this.insertRec(p, current.rt, current, !compareX);
                return current;
            }
            else {
                int cmp2 = Double.compare(p.y(), current.p.y());
                if (cmp2 < 0) {
                    current.lb = this.insertRec(p, current.lb, current, !compareX);
                    return current;
                }
                else {
                    current.rt = this.insertRec(p, current.rt, current, !compareX);
                    return current;
                }
            }
        }
        else {
            int cmp = Double.compare(p.y(), current.p.y());
            if (cmp < 0) {
                current.lb = this.insertRec(p, current.lb, current, !compareX);
                return current;
            }
            else if (cmp > 0) {
                current.rt = this.insertRec(p, current.rt, current, !compareX);
                return current;
            }
            else {
                int cmp2 = Double.compare(p.x(), current.p.x());
                if (cmp2 < 0) {
                    current.lb = this.insertRec(p, current.lb, current, !compareX);
                    return current;
                }
                else {
                    current.rt = this.insertRec(p, current.rt, current, !compareX);
                    return current;
                }
            }
        }

    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return this.containsRec(p, this.root, true);
    }

    private boolean containsRec(Point2D p, Node root, boolean compareX) {
        if (root == null) {
            return false;
        }

        if (p.equals(root.p)) {
            return true;
        }

        if (compareX) {
            int cmp = Double.compare(p.x(), root.p.x());
            if (cmp < 0) {
                return this.containsRec(p, root.lb, !compareX);
            }
            else if (cmp > 0) {
                return this.containsRec(p, root.rt, !compareX);
            }
            else {
                int cmp2 = Double.compare(p.y(), root.p.y());
                if (cmp2 < 0) {
                    return this.containsRec(p, root.lb, !compareX);
                }
                else {
                    return this.containsRec(p, root.rt, !compareX);
                }
            }
        }
        else {
            int cmp = Double.compare(p.y(), root.p.y());
            if (cmp < 0) {
                return this.containsRec(p, root.lb, !compareX);
            }
            else if (cmp > 0) {
                return this.containsRec(p, root.rt, !compareX);
            }
            else {
                int cmp2 = Double.compare(p.x(), root.p.x());
                if (cmp2 < 0) {
                    return this.containsRec(p, root.lb, !compareX);
                }
                else {
                    return this.containsRec(p, root.rt, !compareX);
                }
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        // StdOut.println(this.root.p.toString());
        // StdOut.println(this.root.lb.p.toString());
        // StdOut.println(this.root.rt.p.toString());
        // StdOut.println(this.root.lb.lb.p.toString());
        // StdOut.println(this.root.lb.rt.p.toString());
        // StdOut.println(this.root.rt.rt.p.toString());
        //
        // StdOut.println(this.contains(new Point2D(0.7, 0.5)));

        this.visit(this.root);

    }

    private void visit(Node root) {
        if (root == null) return;

        if (root.compareX) {
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.RED);
            Point2D start = new Point2D(root.p.x(), root.rect.ymin());
            Point2D end = new Point2D(root.p.x(), root.rect.ymax());
            start.drawTo(end);
        }
        else {
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BLUE);
            Point2D start = new Point2D(root.rect.xmin(), root.p.y());
            Point2D end = new Point2D(root.rect.xmax(), root.p.y());
            start.drawTo(end);
        }

        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        root.p.draw();
        this.visit(root.lb);
        this.visit(root.rt);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> li = new ArrayList<Point2D>();

        if (root != null) {
            return range(root, rect);
        }
        else {
            return li;
        }
    }

    private ArrayList<Point2D> range(Node x, RectHV rect) {
        ArrayList<Point2D> points = new ArrayList<Point2D>();
        if (x.rect.intersects(rect)) {
            if (rect.contains(x.p)) {
                points.add(x.p);
            }
            if (x.lb != null) {
                points.addAll(range(x.lb, rect));
            }
            if (x.rt != null) {
                points.addAll(range(x.rt, rect));
            }
        }
        return points;
    }

    // private void rangeRec(Node root, RectHV rect, ArrayList<Point2D> li) {
    //     if (root == null) return;
    //     if (rect.contains(root.p)) {
    //         li.add(root.p);
    //     }
    //
    //     if (rect.intersects(root.rect)) {
    //         this.rangeRec(root.lb, rect, li);
    //         this.rangeRec(root.rt, rect, li);
    //     }
    //     else {
    //         if (root.compareX) {
    //             // vertical
    //             if (root.p.x() > rect.xmax()) {
    //                 // area is on the left of the line
    //                 this.rangeRec(root.lb, rect, li);
    //             }
    //             else {
    //                 this.rangeRec(root.rt, rect, li);
    //             }
    //         }
    //         else {
    //             // horizontal
    //             if (root.p.y() > rect.ymax()) {
    //                 this.rangeRec(root.lb, rect, li);
    //             }
    //             else {
    //                 this.rangeRec(root.rt, rect, li);
    //             }
    //         }
    //     }
    // }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return this.nearestRec(p, this.root, this.root.p);
    }

    private Point2D nearestRec(Point2D p, Node root, Point2D minPoint) {
        if (root.p.equals(p)) return root.p;
        double currMinDistance = minPoint.distanceTo(p);

        if (Double.compare(root.rect.distanceTo(p), currMinDistance) >= 0) {
            return minPoint;
        }
        else {
            if (Double.compare(root.p.distanceTo(p), currMinDistance) < 0) {
                minPoint = root.p;
            }

            if (root.lb != null) {
                minPoint = this.nearestRec(p, root.lb, minPoint);
            }

            if (root.rt != null) {
                minPoint = this.nearestRec(p, root.rt, minPoint);
            }
        }

        return minPoint;

    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // KdTree kd = new KdTree();
        // Point2D p1 = new Point2D(0.7, 0.2);
        // Point2D p2 = new Point2D(0.5, 0.4);
        // Point2D p3 = new Point2D(0.2, 0.3);
        // Point2D p4 = new Point2D(0.4, 0.7);
        // Point2D p5 = new Point2D(0.9, 0.6);
        // //Point2D p6 = new Point2D(0.9, 0.8);
        //
        // kd.insert(p1);
        // kd.insert(p2);
        // kd.insert(p3);
        // kd.insert(p4);
        // kd.insert(p5);
        // //kd.insert(p6);
        //
        // kd.draw();
    }
}
