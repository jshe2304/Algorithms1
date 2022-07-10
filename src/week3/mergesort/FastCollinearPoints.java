package week3.mergesort;
import week3.LineSegment;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final LinkedQueue<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        segments = new LinkedQueue<>();


        for (int i = 0; i < points.length; i++) {

            Arrays.sort(points);
            Point p = points[i];
            Arrays.sort(points, p.slopeOrder());

            /*
            System.out.println(p);
            for (Point point : points) {
                System.out.print(point);
            }
            System.out.println("\n");
            */

            double currentSlope = Double.NaN;
            int length = 2;

            Point firstPoint = null;

            for (int j = 1; j < points.length; j++) {
                Point q = points[j];

                double qSlope = p.slopeTo(q);

                if (qSlope == currentSlope) {
                    length++;
                } else {
                    if (length >= 4 && p.compareTo(firstPoint) < 0) {
                        //System.out.println(firstPoint + " " + p);
                        segments.enqueue(new LineSegment(p, points[j - 1]));
                    }
                    currentSlope = qSlope;
                    firstPoint = q;
                    length = 2;
                }

            }

            if (length >= 4 && p.compareTo(firstPoint) <= 0) {
                //System.out.println(firstPoint + "  " + p);
                segments.enqueue(new LineSegment(p, points[points.length - 1]));
            }
        }

        //for (int i = 0; i < points.length; i++) {
        //    System.out.println(points[i]);
        //}

    }

    public int numberOfSegments() {
        return segments.size();
    }
    public LineSegment[] segments() {
        //System.out.println(numberOfSegments());

        LineSegment[] arr = new LineSegment[numberOfSegments()];

        int i = 0;

        for (LineSegment line : segments) {
            arr[i] = line;
            i++;
        }

        /*
        int size = segments.size();

        for (int i = 0; i < size; i++) {
            arr[i] = segments.dequeue();
        }

         */

        return arr;
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
        var ls = collinear.segments();
        //System.out.println(ls.length);
        for (LineSegment segment : ls) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        /*
        Point[] points = new Point[] {new Point(0, 0), new Point(0, 1), new Point(1, 2), new Point(1, 1), new Point(2, 1), new Point(2, 0), new Point(2, 2), new Point(3, 3), new Point(0, 2), new Point(0, 3), new Point(1, 0), new Point(3, 0), new Point(4, 4), new Point(1, 3)};

        FastCollinearPoints fast = new FastCollinearPoints(points);


        System.out.println("Number of Segments");
        System.out.println(fast.numberOfSegments());

        System.out.println("Segments");
        for (LineSegment line : fast.segments()) {
            System.out.print(line + ", ");
        }
         */

    }
}
