package week1.unionfind;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] open;
    private final int n;

    private int count = 0;

    private WeightedQuickUnionUF grid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n<1) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        //id n-squared is the top virtual site and id n-squared+1 is the bottom virtual site
        grid = new WeightedQuickUnionUF(n*n+2);

        //automatically sets everything to false
        open = new boolean[n*n];
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int id = (col-1)+n*(row-1);

        //System.out.println("Opening " + id);

        if (row < 1 || row>n || col<1 || col>n) {
            throw new IllegalArgumentException();
        }

        if (!open[id]) {
            //System.out.println(id + " is open");

            open[id] = true;

            //if opening sites at top, connect to top virtual site
            //if opening sites at the bottom, connect to bottom virtual site
            if (id < n) {
                grid.union(id, n*n);
            }

            if (id > n*(n-1)-1) {
                grid.union(id, n*n+1);
            }

            if ((id%n != 0) && open[id-1]) {
                //checks left
                //for union, if trees same size, q added to p tree
                grid.union(id, id-1);
                //System.out.println("Unifying " + id + "," + (id-1));
            }
            if ((id%n != n-1) && open[id+1]) {
                //checks right
                grid.union(id, id+1);
                //System.out.println("Unifying " + id + "," + (id+1));
            }
            if ((id>=n) && open[id-n]) {
                //checks up
                grid.union(id, id-n);
                //System.out.println("Unifying " + id + "," + (id-n));
            }
            if ((id<=(n*(n-1)-1)) && open[id+n]) {
                //checks down
                grid.union(id, id+n);
                //System.out.println("Unifying " + id + "," + (id+n));
            }

            count++;
        }

        //System.out.println();
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        if (row < 1 || row>n || col<1 || col>n) {
            throw new IllegalArgumentException();
        }

        //returns entry in open[] array for the id calculated from row, col
        return open[(col-1)+n*(row-1)];
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        if (row < 1 || row>n || col<1 || col>n) {
            throw new IllegalArgumentException();
        }

        if (isOpen(row, col)) {
            return (grid.find(n*n) == grid.find((col-1)+n*(row-1)));
        } else {
            return false;
        }

        //check if root of top virtual site equal to root of id calculated from row, col

    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return (grid.find(n*n) == grid.find(n*n+1));
    }

    // test client (optional)
    public static void main(String[] args){

        Percolation myGrid = new Percolation(10);

        /*
        myGrid.open(1, 1);
        myGrid.open(2, 1);
        myGrid.open(2, 4);
        myGrid.open(3, 1);
        myGrid.open(3, 2);
        myGrid.open(3, 3);
        myGrid.open(3, 4);
        myGrid.open(4, 4);
        myGrid.open(5, 4);
         */

        System.out.println(myGrid.isFull(1, 6));
        myGrid.open(1, 6);
        System.out.println(myGrid.isFull(1, 6));


        System.out.println(myGrid.isOpen(2, 1));

        System.out.println(myGrid.numberOfOpenSites());
        System.out.println(myGrid.percolates());

    }
}