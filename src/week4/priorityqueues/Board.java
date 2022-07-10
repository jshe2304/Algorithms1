package week4.priorityqueues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Board {

    private final int[][] tiles;

    private final int n;

    private int emptyRow;
    private int emptyCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        n = tiles.length;

        this.tiles = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];

                if (tiles[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder board = new StringBuilder(n + "\n");

        for (int[] row : tiles) {
            for (int tile : row) {
                board.append(" ").append(tile);
            }
            board.append("\n");
        }

        return board.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((tiles[i][j]) != ((j + i * n + 1) % (Math.pow(n, 2)))) {
                    count++;
                }
            }
        }

        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int dist = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = tiles[i][j];

                if (tile == 0) {
                    dist += ((n-1) - j) + ((n-1) - i);
                } else {
                    dist += Math.abs(((tile - 1) / n) - i) + Math.abs(((tile - 1) % n) - j);
                }
            }
        }

        return dist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((tiles[i][j]) != ((j + i * n + 1) % (Math.pow(n, 2)))) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {

        if (y == this) return true;

        if (!(y instanceof Board board)) return false;

        if (board.dimension() != n) return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != board.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() { return new Neighbors();}

    private class Neighbors implements Iterable<Board> {
        @Override
        public Iterator<Board> iterator() {
            return new NeighborIterator();
        }
    }

    private class NeighborIterator implements Iterator<Board> {

        //    1
        //  2 0 4
        //    3
        private int side = 1;

        private int moves = 4;

        private NeighborIterator() {
            if (emptyRow == 0) moves--;
            else if (emptyRow == n - 1) moves--;
            if (emptyCol == 0) moves--;
            else if (emptyCol == n - 1) moves--;
        }

        private Board swap () {
            int[][] neighbor = new int[n][n];

            for (int i = 0; i < n; i++) {
                //noinspection ManualArrayCopy
                for (int j = 0; j < n; j++) {
                    neighbor[i][j] = tiles[i][j];
                }
            }

            switch (side) {
                case 1 -> {
                    int tile = neighbor[emptyRow - 1][emptyCol];
                    neighbor[emptyRow - 1][emptyCol] = 0;
                    neighbor[emptyRow][emptyCol] = tile;
                }
                case 2 -> {
                    int tile = neighbor[emptyRow][emptyCol - 1];
                    neighbor[emptyRow][emptyCol - 1] = 0;
                    neighbor[emptyRow][emptyCol] = tile;
                }
                case 3 -> {
                    int tile = neighbor[emptyRow + 1][emptyCol];
                    neighbor[emptyRow + 1][emptyCol] = 0;
                    neighbor[emptyRow][emptyCol] = tile;
                }
                case 4 -> {
                    int tile = neighbor[emptyRow][emptyCol + 1];
                    neighbor[emptyRow][emptyCol + 1] = 0;
                    neighbor[emptyRow][emptyCol] = tile;
                }
            }
            side++;
            return new Board(neighbor);
        }

        @Override
        public boolean hasNext() {
            return moves != 0;
        }

        @Override
        public Board next() {
            try {
                moves--;
                return (swap());
            } catch (Exception e) {
                moves++;
                side++;
                return (next());
            }
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int ax = 0;
        int ay = 0;

        int bx = 0;
        int by = 0;

        while (ax == bx && ay == by) {
            ax = StdRandom.uniform(n);
            ay = StdRandom.uniform(n);
            bx = StdRandom.uniform(n);
            by = StdRandom.uniform(n);
        }

        int[][] twin = new int[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, twin[i], 0, n);
        }

        int tile = twin[ax][ay];
        twin[ax][ay] = twin[bx][by];
        twin[bx][by] = tile;

        return new Board(twin);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[][] {new int[]{1, 0, 2}, new int[]{3, 4, 5}, new int[]{6, 7, 8}};
        //int[][] goal = new int[][] {new int[]{1, 2, 3}, new int[]{4, 5, 6}, new int[]{8, 7, 0}};
        //int[][] goal2 = new int[][] {new int[]{1, 2}, new int[]{3, 0}};

        Board board = new Board(tiles);

        System.out.println(board);
        //System.out.println(board.dimension());
        //System.out.println(board.hamming());
        //System.out.println(board.manhattan());
        System.out.println(board.isGoal());
        //System.out.println(board.equals(new Board(goal)));
        //System.out.println(board.equals(new Board(goal2)));

        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor);
        }

        //System.out.println(board.twin());
    }
}