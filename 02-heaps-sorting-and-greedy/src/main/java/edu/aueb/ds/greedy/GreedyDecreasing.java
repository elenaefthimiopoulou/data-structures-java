package edu.aueb.ds.greedy;

import edu.aueb.ds.datastructures.List;
import edu.aueb.ds.datastructures.ListImpl;
import edu.aueb.ds.datastructures.Node;
import edu.aueb.ds.diskmanager.Disk;
import edu.aueb.ds.sort.Sort;

import java.io.FileNotFoundException;

/**
 * Implements the "Greedy-Decreasing" algorithm for the Bin Packing problem.
 */
public class GreedyDecreasing {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private GreedyDecreasing() {}

    /**
     * This method implements the greedy-decreasing heuristic. It first sorts
     * the folders in descending order of size and then applies the same
     * greedy strategy as the Greedy algorithm.
     * <p>
     * The sorting is done on a copy, so the list given as argument is not modified.
     *
     *
     * <p>Complexity: O(n log n) for n folders (Mergesort + Greedy)
     * @param folders A list of folder sizes to be placed on disks.
     * @return A list of disks used to store the folders.
     */
    public static List<Disk> runGreedyAlgorithm(List<Integer> folders) {
        // 1. Sort a copy of the folders in descending order.
        List<Integer> sorted = new ListImpl<>();
        for (Node<Integer> n = folders == null ? null : folders.getHead(); n != null; n = n.getNext()) {
            sorted.insertAtBack(n.getData());
        }
        Sort.mergeSort(sorted);

        // 2. Apply the greedy strategy on the sorted folders.
        return Greedy.runGreedyAlgorithm(sorted);
    }

    /**
     * Runs the Greedy-Decreasing algorithm on a file and prints the resulting disks.
     *
     * @param args {@code args[0]} is the path of the file with the folder sizes
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java edu.aueb.ds.greedy.GreedyDecreasing <folders-file>");
            return;
        }
        try {
            List<Disk> disks = runGreedyAlgorithm(Greedy.readFolderSizes(args[0]));
            System.out.println("Greedy-Decreasing used " + disks.size() + " disk(s):");
            for (Node<Disk> n = disks.getHead(); n != null; n = n.getNext()) {
                System.out.println("  " + n.getData());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: could not find file '" + args[0] + "'.");
        }
    }
}
