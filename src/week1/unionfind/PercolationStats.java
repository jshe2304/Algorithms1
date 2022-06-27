package week1.unionfind;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;


public class PercolationStats {



    private double[] perculationThresholds;

    private final int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n<1 || trials<1) {
            throw new IllegalArgumentException();
        }

        this.trials = trials;

        perculationThresholds = new double[trials];

        for (int i=0; i<trials; i++) {
            //System.out.println("trial " + i);

            Percolation grid = new Percolation(n);

            boolean percolates = false;

            //int whileIterations = 0;

            while (!percolates) {
                grid.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
                percolates = grid.percolates();
                //whileIterations++;
            }

            perculationThresholds[i] = (grid.numberOfOpenSites())/(n*n*1.0);

            //System.out.println("Open Sites:" + grid.numberOfOpenSites());
            //System.out.println("While Iterations:" + whileIterations);
            //System.out.println();
        }

        //for (double i: perculationThresholds) {
        //    System.out.print(i + " ");
        //}

    }

    // sample mean of percolation threshold
    public double mean() {
        return (StdStats.mean(perculationThresholds));
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return (StdStats.stddev(perculationThresholds));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean()-(1.96*stddev())/(Math.sqrt(trials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean()+(1.96*stddev())/(Math.sqrt(trials)));
    }

    // test client (see below)
    public static void main(String[] args) {

        int n = 0;
        int trials = 0;

        try {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            System.out.println("Input valid integers for grid size and number of trials");
        }

        PercolationStats stats = new PercolationStats(n, trials);

        System.out.println("mean\t\t= " + stats.mean());
        System.out.println("stddev\t\t= " + stats.stddev());
        System.out.println("95% confidence interval\t= [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");

    }

}