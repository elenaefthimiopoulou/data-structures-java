package edu.aueb.ds.diskmanager;

import edu.aueb.ds.datastructures.MaxPQ;

/**
 * This class is used to test the Disk class implementation.
 *
 * You can run this file to check the correctness of your implementation.
 * For instructions on how to run it from the command line, please refer
 * to the README.md file in this directory.
 */
public class DiskTestMain {

    /**
     * The main method.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        System.out.println("--- Testing Disk Class ---");

        // Test 1: Unique IDs
        System.out.println("\n1. Testing unique IDs...");
        Disk disk1 = new Disk();
        Disk disk2 = new Disk();
        Disk disk3 = new Disk();
        System.out.println("Disk 1 ID: " + disk1.getId());
        System.out.println("Disk 2 ID: " + disk2.getId());
        System.out.println("Disk 3 ID: " + disk3.getId());
        if (disk1.getId() != disk2.getId() && disk1.getId() != disk3.getId() && disk2.getId() != disk3.getId()) {
            System.out.println("SUCCESS: IDs are unique.");
        } else {
            System.out.println("FAILURE: IDs are not unique.");
        }

        // Test 2: Free Space
        System.out.println("\n2. Testing free space calculation...");
        Disk disk4 = new Disk();
        System.out.println("Initial free space: " + disk4.getFreeSpace() + " MB");
        int folderSize1 = 100000;
        disk4.addFolder(folderSize1);
        System.out.println("Free space after adding a " + folderSize1 + " MB folder: " + disk4.getFreeSpace() + " MB");
        int expectedFreeSpace = 1000000 - folderSize1;
        if (disk4.getFreeSpace() == expectedFreeSpace) {
            System.out.println("SUCCESS: Free space is calculated correctly.");
        } else {
            System.out.println("FAILURE: Free space is not calculated correctly.");
        }

        // Test 3: Comparable implementation
        System.out.println("\n3. Testing Comparable implementation...");
        Disk diskA = new Disk(); // More free space
        Disk diskB = new Disk(); // Less free space
        diskB.addFolder(200000);

        System.out.println("Disk A free space: " + diskA.getFreeSpace());
        System.out.println("Disk B free space: " + diskB.getFreeSpace());
        int comparison = diskA.compareTo(diskB);
        System.out.println("diskA.compareTo(diskB): " + comparison);
        if (comparison > 0) {
            System.out.println("SUCCESS: compareTo returns a positive value as Disk A has more free space.");
        } else {
            System.out.println("FAILURE: compareTo should return a positive value.");
        }

        // Test 4: MaxPQ with Disks
        System.out.println("\n4. Testing MaxPQ with Disks...");
        MaxPQ<Disk> pq = new MaxPQ<>();
        pq.add(diskB);
        pq.add(diskA);

        System.out.println("Adding Disk A and Disk B to a MaxPQ.");
        Disk maxDisk = pq.getMax();
        System.out.println("The disk with the maximum free space is: Disk " + maxDisk.getId() + " with " + maxDisk.getFreeSpace() + " MB");
        if (maxDisk.getId() == diskA.getId()) {
            System.out.println("SUCCESS: MaxPQ returns the disk with the most free space.");
        } else {
            System.out.println("FAILURE: MaxPQ did not return the disk with the most free space.");
        }

        System.out.println("\n--- Testing Complete ---");
    }
}
