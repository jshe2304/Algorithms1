
package week3.mergesort;
import edu.princeton.cs.algs4.In;
import week3.LineSegment;

import edu.princeton.cs.algs4.StdOut;


import edu.princeton.cs.algs4.LinkedQueue;
public class BruteCollinearPoints {

    private final LinkedQueue<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        segments = new LinkedQueue<>();

        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                for (int k = j+1; k < points.length; k++) {
                    for (int l = k+1; l < points.length; l++) {
                        if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k]) && points[j].slopeTo(points[k]) == points[k].slopeTo(points[l])) {
                            segments.enqueue(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {

        LineSegment[] arr = new LineSegment[numberOfSegments()];

        int i = 0;

        for (LineSegment line : segments) {
            arr[i] = line;
            i++;
        }

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

        // print and draw the line segments
        BruteCollinearPoints brute = new BruteCollinearPoints(points);
        for (LineSegment segment : brute.segments()) {
            StdOut.println(segment);
        }
    }
}
