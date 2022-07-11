package week4.priorityqueues;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Solver {

    private static class Node implements Comparable<Node> {

        private final Board board;
        private final Node previous;
        private final int priority;

        private final int moves;

        private Node(Board board, int moves, Node previous) {
            this.board = board;
            this.previous = previous;
            priority = moves + board.manhattan();
            this.moves = moves;
        }

        public int priority() {
            return priority;
        }

        public Board board() {
            return board;
        }

        public Node previous() {
            return previous;
        }

        public int moves() {
            return moves;
        }
        @Override
        public int compareTo(Node node) {
            return priority - node.priority();
        }

        public String toString() {
            String node = "";
            node += board;
            node += priority;
            return node;
        }
    }

    private boolean solvable = true;

    private Node solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<Node> pq = new MinPQ<>();
        MinPQ<Node> pq2 = new MinPQ<>();


        Node dequeued = new Node(initial, 0, null);
        pq.insert(dequeued);

        Board twin = initial.twin();
        Node dequeued2 = new Node(twin, 0, null);
        pq2.insert(dequeued2);

        while (!dequeued.board().isGoal() && !dequeued2.board().isGoal()) {
            dequeued = pq.delMin();
            dequeued2 = pq2.delMin();

            for (Board neighbor : dequeued.board().neighbors()) {
                Node node = new Node(neighbor, dequeued.moves() + 1, dequeued);
                if (dequeued.previous() == null || !node.board().equals(dequeued.previous().board())) pq.insert(node);

            }

            for (Board neighbor : dequeued2.board().neighbors()) {
                Node node = new Node(neighbor, dequeued2.moves() + 1, dequeued2);
                if (dequeued2.previous() == null || !node.board().equals(dequeued2.previous().board())) pq2.insert(node);
            }

        }

        if (dequeued2.board().isGoal()) {
            solvable = false;
        } else {
            solution = dequeued;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (solvable)
            return solution.moves();
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solvable) {
            return new Solutions();
        }
        return null;
    }

    private class Solutions implements Iterable<Board> {

        @Override
        public Iterator<Board> iterator() {
            Node current = solution;

            LinkedStack<Board> stack = new LinkedStack<>();

            while (current != null) {
                stack.push(current.board());
                current = current.previous();
            }

            return stack.iterator();
        }
    }

    // test client (see below)
    public static void main(String[] args) {


        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }


        /*

        //int[][] tiles = new int[][] {new int[]{0, 1, 3}, new int[]{4, 2, 5}, new int[]{7, 8, 6}};//solvable
        //int[][] tiles = new int[][] {new int[]{1, 2, 3}, new int[]{4, 5, 6}, new int[]{8, 7, 0}};//unsolvable
        int[][] tiles = new int[][] {new int[]{1, 2, 3}, new int[]{4, 6, 5}, new int[]{8, 7, 0}};//solvable acc to rule
        //int[][] tiles = new int[][] {new int[]{0, 3, 1}, new int[]{4, 2, 5}, new int[]{7, 8, 6}};
        //int[][] tiles = new int[][] {new int[]{1, 2, 3, 4, 5}, new int[]{6, 7, 8, 10, 0}, new int[]{11, 12, 14, 9, 20}, new int[]{16, 17, 13, 18, 19}, new int[]{21, 22, 23, 15, 24}};

        Board board = new Board(tiles);

        //System.out.println(board);
        //System.out.println();

        Solver solver = new Solver(board);

        System.out.println(solver.isSolvable());
        System.out.println(solver.moves());

        for (Board solution : solver.solution()) {
            System.out.println(solution);
        }

        */

    }
}