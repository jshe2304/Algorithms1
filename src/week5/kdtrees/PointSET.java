package week5.kdtrees;

import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.Iterator;

public class PointSET {

    private SET<Point2D> points;

    public PointSET() {
        points = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        return points.contains(p);
    }

    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        return new Contained(rect);
    }

    private class Contained implements Iterable<Point2D> {

        private final LinkedQueue<Point2D> in = new LinkedQueue<>();

        private Contained (RectHV rect) {
            for (Point2D point : points) {
                if (rect.contains(point)) in.enqueue(point);
            }
        }

        @Override
        public Iterator<Point2D> iterator() {
            return in.iterator();
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (isEmpty()) return null;

        double dist = 1;
        Point2D min = null;

        for (Point2D point : points) {
            if (min == null) {
                dist = p.distanceTo(point);
                min = point;
                continue;
            }

            if (point == p) {
                continue;
            }

            double d = p.distanceTo(point);

            if (dist > p.distanceTo(point)) {
                dist = d;
                min = point;
            }
        }

        return min;
    }

    public static void main(String[] args) {

    }
}
