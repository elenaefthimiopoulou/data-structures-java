package edu.aueb.ds.datastructures;

import java.util.Comparator;

/**
 * Comparator for Integers.
 */
public class IntegerComparator implements Comparator<Integer> {
    /**
     * Default constructor.
     */
    public IntegerComparator() {}

    @Override
    public int compare(Integer t1, Integer t2) {
        return t1 - t2;
    }
}
