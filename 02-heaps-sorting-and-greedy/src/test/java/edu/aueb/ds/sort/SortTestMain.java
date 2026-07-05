package edu.aueb.ds.sort;

import edu.aueb.ds.datastructures.List;
import edu.aueb.ds.datastructures.ListImpl;

/**
 * This class is used to test the Sort class implementation.
 *
 * You can run this file to check the correctness of your implementation.
 * For instructions on how to run it from the command line, please refer
 * to the README.md file in this directory.
 */
public class SortTestMain {

    /**
     * The main method.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        System.out.println("--- Testing Sort Class ---");

        // Test 1: Sort a list of integers
        System.out.println("\n1. Testing mergeSort...");
        List<Integer> list = new ListImpl<>();
        list.insertAtBack(5);
        list.insertAtBack(2);
        list.insertAtBack(8);
        list.insertAtBack(1);
        list.insertAtBack(9);
        list.insertAtBack(4);

        System.out.println("Original list: " + list);
        Sort.mergeSort(list);
        System.out.println("Sorted list (descending): " + list);

        // A simple check for descending order
        boolean success = true;
        Integer prev = null;
        while (!list.isEmpty()) {
            Integer current = list.removeFromFront();
            if (prev != null && prev < current) {
                success = false;
                break;
            }
            prev = current;
        }

        if (success) {
            System.out.println("SUCCESS: The list is sorted in descending order.");
        } else {
            System.out.println("FAILURE: The list is not sorted correctly.");
        }

        System.out.println("\n--- Testing Complete ---");
    }
}
