package edu.aueb.ds.datastructures;

import java.util.Comparator;

/**
 * Comparator for Strings.
 */
public class StringComparator implements Comparator<String> {
    /**
     * Default constructor.
     */
    public StringComparator() {}

    @Override
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
}
