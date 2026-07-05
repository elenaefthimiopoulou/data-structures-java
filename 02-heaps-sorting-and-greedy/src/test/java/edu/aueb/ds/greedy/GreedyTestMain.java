package edu.aueb.ds.greedy;

import edu.aueb.ds.datastructures.List;
import edu.aueb.ds.diskmanager.Disk;
import edu.aueb.ds.sort.Sort;

import java.io.FileNotFoundException;

/**
 * This class is used to test the Greedy and GreedyDecreasing algorithms.
 *
 * It reads a predefined test case from a file and checks if the algorithms
 * produce the expected number of disks.
 *
 * For instructions on how to run it from the command line, please refer
 * to the README.md file in this directory.
 */
public class GreedyTestMain {

    // Path to the test file, relative to the project root.
    private static final String TEST_FILE_PATH = "artifacts/greedy_test_cases/folders.txt";

    /**
     * The main method.
     * @param args The command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("--- Testing Greedy Algorithms ---");

        try {
            // Test Greedy Algorithm
            testGreedy();

            // Test Greedy-Decreasing Algorithm
            testGreedyDecreasing();

        } catch (FileNotFoundException e) {
            System.err.println("TEST FAILED: Could not find the test file at " + TEST_FILE_PATH);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during testing.");
            e.printStackTrace();
        }

        System.out.println("\n--- Testing Complete ---");
    }

    /**
     * Tests the standard Greedy algorithm.
     */
    private static void testGreedy() throws FileNotFoundException {
        System.out.println("\n1. Testing Greedy Algorithm...");
        List<Integer> folderSizes = Greedy.readFolderSizes(TEST_FILE_PATH);
        List<Disk> usedDisks = Greedy.runGreedyAlgorithm(folderSizes);

        int expectedDisks = 3;
        int actualDisks = usedDisks != null ? usedDisks.size() : 0;

        System.out.println("Expected number of disks: " + expectedDisks);
        System.out.println("Actual number of disks:   " + actualDisks);

        if (actualDisks == expectedDisks) {
            System.out.println("SUCCESS: Greedy algorithm produced the correct number of disks.");
        } else {
            System.out.println("FAILURE: Greedy algorithm produced the wrong number of disks.");
        }
    }

    /**
     * Tests the Greedy-Decreasing algorithm.
     */
    private static void testGreedyDecreasing() throws FileNotFoundException {
        System.out.println("\n2. Testing Greedy-Decreasing Algorithm...");
        List<Integer> folderSizes = Greedy.readFolderSizes(TEST_FILE_PATH);
        Sort.mergeSort(folderSizes); // Sort the folders first
        List<Disk> usedDisks = GreedyDecreasing.runGreedyAlgorithm(folderSizes);

        int expectedDisks = 3;
        int actualDisks = usedDisks != null ? usedDisks.size() : 0;

        System.out.println("Expected number of disks: " + expectedDisks);
        System.out.println("Actual number of disks:   " + actualDisks);

        if (actualDisks == expectedDisks) {
            System.out.println("SUCCESS: Greedy-Decreasing algorithm produced the correct number of disks.");
        } else {
            System.out.println("FAILURE: Greedy-Decreasing algorithm produced the wrong number of disks.");
        }
    }
}
