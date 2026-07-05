package edu.aueb.ds.comparison;

/**
 * A simple class to store the results for a single value of N.
 */
public class Result {
    /** The number of folders. */
    int n;
    /** The average number of disks used by Greedy. */
    double avgGreedy;
    /** The average number of disks used by Greedy-Decreasing. */
    double avgGreedyDecreasing;

    /**
     * Creates a result.
     *
     * @param n the number of folders
     * @param avgGreedy the average number of disks used by Greedy
     * @param avgGreedyDecreasing the average number of disks used by Greedy-Decreasing
     */
    Result(int n, double avgGreedy, double avgGreedyDecreasing) {
        this.n = n;
        this.avgGreedy = avgGreedy;
        this.avgGreedyDecreasing = avgGreedyDecreasing;
    }
}
