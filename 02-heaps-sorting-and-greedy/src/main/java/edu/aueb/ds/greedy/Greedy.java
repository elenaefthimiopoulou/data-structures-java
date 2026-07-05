package edu.aueb.ds.greedy;

import edu.aueb.ds.datastructures.List;
import edu.aueb.ds.datastructures.ListImpl;
import edu.aueb.ds.datastructures.MaxPQ;
import edu.aueb.ds.datastructures.Node;
import edu.aueb.ds.datastructures.PriorityQueueInterface;
import edu.aueb.ds.diskmanager.Disk;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Implements the "Greedy" algorithm for the Bin Packing problem.
 */
public class Greedy {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Greedy() {}

    /**
     * Executes the core greedy algorithm. It processes folders in the order
     * they are given and places each folder onto the disk with the most
     * available free space. If no existing disk can accommodate the folder,
     * a new disk is created.
     * <p>
     * The used disks are kept in a {@link MaxPQ} ordered by free space, so the emptiest
     * disk is found in O(1) and updated in O(log d), where d is the number of disks.
     * If the emptiest disk cannot hold the folder, no other disk can.
     *
     *
     * <p>Complexity: O(n log n) for n folders
     * @param folders A list of folder sizes to be placed on disks.
     * @return A list of disks used to store the folders.
     * @throws IllegalArgumentException if a folder is larger than the capacity of a disk
     */
    public static List<Disk> runGreedyAlgorithm(List<Integer> folders) {
        List<Disk> usedDisks = new ListImpl<>();
        PriorityQueueInterface<Disk> pq = new MaxPQ<>();

        for (Node<Integer> node = folders == null ? null : folders.getHead();
             node != null; node = node.getNext()) {
            int size = node.getData();
            if (size > Disk.CAPACITY) {
                throw new IllegalArgumentException("Folder of " + size + " MB does not fit on any disk");
            }

            Disk disk = pq.getMax();           // the used disk with the most free space
            if (disk == null || disk.getFreeSpace() < size) {
                if (disk != null) {
                    pq.add(disk);              // it stays available for smaller folders
                }
                disk = new Disk();             // open a new disk
                usedDisks.insertAtBack(disk);
            }
            disk.addFolder(size);
            pq.add(disk);                      // re-insert with the updated free space
        }
        return usedDisks;
    }

    /**
     * Reads folder sizes from a text file that contains one integer (MB) per line.
     * Blank lines are ignored.
     *
     * @param filePath the path of the file
     * @return the folder sizes, in the order they appear in the file
     * @throws FileNotFoundException if the file does not exist
     */
    public static List<Integer> readFolderSizes(String filePath) throws FileNotFoundException {
        List<Integer> sizes = new ListImpl<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    sizes.insertAtBack(Integer.parseInt(line));
                }
            }
        }
        return sizes;
    }

    /**
     * Runs the Greedy algorithm on a file and prints the resulting disks.
     *
     * @param args {@code args[0]} is the path of the file with the folder sizes
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java edu.aueb.ds.greedy.Greedy <folders-file>");
            return;
        }
        try {
            List<Disk> disks = runGreedyAlgorithm(readFolderSizes(args[0]));
            System.out.println("Greedy used " + disks.size() + " disk(s):");
            for (Node<Disk> n = disks.getHead(); n != null; n = n.getNext()) {
                System.out.println("  " + n.getData());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: could not find file '" + args[0] + "'.");
        }
    }
}
