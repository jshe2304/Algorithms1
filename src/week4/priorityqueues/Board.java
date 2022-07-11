package week4.priorityqueues;

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
                if (tiles[i][j] != 0 && (tiles[i][j]) != ((j + i * n + 1) % (Math.pow(n, 2)))) count++;
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
                if (tile != 0) dist += Math.abs(((tile - 1) / n) - i) + Math.abs(((tile - 1) % n) - j);
            }
        }

        return dist;
    }

    // is this board the goal board?
    public boolean isGoal() {

        if (tiles[n-1][n-1] != 0) return false;

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

        if (!(y instanceof Board)) return false;

        Board board = (Board) y;

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
                case 1 : {
                    int tile = neighbor[emptyRow - 1][emptyCol];
                    neighbor[emptyRow - 1][emptyCol] = 0;
                    neighbor[emptyRow][emptyCol] = tile;
                    break;
                }
                case 2 : {
                    int tile = neighbor[emptyRow][emptyCol - 1];
                    neighbor[emptyRow][emptyCol - 1] = 0;
                    neighbor[emptyRow][emptyCol] = tile;
                    break;
                }
                case 3 : {
                    int tile = neighbor[emptyRow + 1][emptyCol];
                    neighbor[emptyRow + 1][emptyCol] = 0;
                    neighbor[emptyRow][emptyCol] = tile;
                    break;
                }
                case 4 : {
                    int tile = neighbor[emptyRow][emptyCol + 1];
                    neighbor[emptyRow][emptyCol + 1] = 0;
                    neighbor[emptyRow][emptyCol] = tile;
                    break;
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
            } catch (IndexOutOfBoundsException e) {
                moves++;
                side++;
                return (next());
            }
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int[][] twin = new int[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, twin[i], 0, n);
        }

        if (twin[0][0] != 0 && twin[n-1][n-1] != 0) {
            int tile = twin[0][0];
            twin[0][0] = twin[n-1][n-1];
            twin[n-1][n-1] = tile;
        } else {
            int tile = twin[0][n-1];
            twin[0][n-1] = twin[n-1][0];
            twin[n-1][0] = tile;
        }

        return new Board(twin);
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        Board board = new Board(new int[][] {new int[]{0, 1, 3}, new int[]{4, 2, 5}, new int[]{7, 8, 6}});

        System.out.println(board);

        System.out.println(board.dimension());

        System.out.println(board.hamming());

        System.out.println(board.manhattan());

        System.out.println(board.isGoal());

        System.out.println(board.equals(new Board(new int[][] {new int[]{0, 3, 1}, new int[]{4, 2, 5}, new int[]{7, 8, 6}})));

        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor);
        }

        System.out.println(board.twin());
    }
}