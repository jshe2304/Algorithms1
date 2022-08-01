package week5.kdtrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class KdTree {

    private static class Node {
        Node left;
        Node right;
        final Point2D point;
        final boolean vert;

        final RectHV rect;
        private Node (Point2D point, boolean vert, RectHV rect) {
            this.point = point;
            this.vert = vert;
            this.rect = rect;
        }
    }

    private Node root;
    private int size = 0;

    public KdTree() {}
    public boolean isEmpty() { return size==0; }
    public int size() { return size; }
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (!contains(p)) root = put(root, p, true, new RectHV(0, 0, 1, 1));
    }
    private Node put(Node node, Point2D p, boolean vert, RectHV rect) {
        if (node == null) {
            size++;
            return new Node(p, vert, rect);
        }

        Point2D parent = node.point;
        RectHV container = node.rect;

        if (node.vert) {
            if (p.x() < parent.x()) node.left = put(node.left, p, false, new RectHV(container.xmin(), container.ymin(), parent.x(), container.ymax()));
            else node.right = put(node.right, p, false, new RectHV(parent.x(), container.ymin(), container.xmax(), container.ymax()));
        } else {
            if (p.y() < parent.y()) node.left = put(node.left, p, true, new RectHV(container.xmin(), container.ymin(), container.xmax(), parent.y()));
            else node.right = put(node.right, p, true, new RectHV(container.xmin(), parent.y(), container.xmax(), container.ymax()));
        }

        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        Node node = root;

        while (node != null) {
            Point2D point = node.point;

            if (point.equals(p)) return true;

            if (node.vert) {
                if (p.x() < point.x()) node = node.left;
                else node = node.right;
            } else {
                if (p.y() < node.point.y()) node = node.left;
                else node = node.right;
            }
        }

        return false;
    }
    public void draw() {
        draw(root);
    }

    private void draw(Node node) {
        if (node != null) {

            if (node.vert) {
                StdDraw.setPenColor(255, 0, 0);
                StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
            } else {
                StdDraw.setPenColor(0, 0, 255);
                StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
            }

            StdDraw.setPenColor(0, 0, 0);
            StdDraw.filledCircle(node.point.x(), node.point.y(), 0.005);

            draw(node.left);
            draw(node.right);
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        Queue<Point2D> set = new Queue<>();

        range(root, set, rect);

        return set;
    }

    private void range(Node node, Queue<Point2D> set, RectHV rect) {
        if (node != null) {

            if (!node.rect.intersects(rect)) return;

            if (rect.contains(node.point)) set.enqueue(node.point);

            range(node.left, set, rect);
            range(node.right, set, rect);
        }
    }

    private Node nearest;
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (isEmpty()) return null;

        nearest = root;

        nearest(root, p);

        return nearest.point;
    }

    private void nearest(Node node, Point2D p) {
        if (node == null) return;

        double dist = nearest.point.distanceTo(p);

        if (node.rect.distanceTo(p) > dist) return;

        if (node.point.distanceTo(p) < dist) nearest = node;

        if (node.vert) {
            if (p.x() < node.point.x()) {
                nearest(node.left, p);
                nearest(node.right, p);
            } else {
                nearest(node.right, p);
                nearest(node.left, p);
            }
        } else {
            if (p.y() < node.point.y()) {
                nearest(node.left, p);
                nearest(node.right, p);
            } else {
                nearest(node.right, p);
                nearest(node.left, p);
            }
        }
    }
    public static void main(String[] args) {
        String filename = "C:\\Users\\jshe2\\IdeaProjects\\Algorithms1\\src\\week5\\kdtrees\\input10.txt";
        In in = new In(filename);

        KdTree kdtree = new KdTree();

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        //for (Point2D p : kdtree.range(new RectHV(0.1, 0.25, 0.5, 0.5))) {
        //    System.out.println(p);
        //}

        System.out.println(kdtree.size());

        //kdtree.draw();

        //System.out.println(kdtree.nearest(new Point2D(1, 0)));

        //System.out.println(kdtree.contains(new Point2D(0.498, 0.208)));
    }

}
