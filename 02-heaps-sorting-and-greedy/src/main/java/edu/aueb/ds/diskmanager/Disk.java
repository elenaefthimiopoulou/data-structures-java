package edu.aueb.ds.diskmanager;

import edu.aueb.ds.datastructures.List;
import edu.aueb.ds.datastructures.ListImpl;

/**
 * Represents a 1TB disk (1,000,000 MB) on which whole folders are stored.
 * <p>
 * Every disk receives a unique, automatically increasing id when it is created.
 * Disks are {@link Comparable} by their <b>free space</b>: the disk with more free space is
 * "greater", so in a {@code MaxPQ<Disk>} the root is always the emptiest disk.
 */
public class Disk implements Comparable<Disk> {

    /** The capacity of every disk, in MB (1TB). */
    public static final int CAPACITY = 1_000_000;

    /** The id given to the next disk that is created. */
    private static int nextId = 1;

    /** The unique id of this disk. */
    private final int id;
    /** The sizes (in MB) of the folders stored on this disk. */
    private final List<Integer> folders;
    /** The space (in MB) that is currently used. */
    private int usedSpace;

    /**
     * Creates a new, empty disk with a new unique id.
     */
    public Disk() {
        this.id = nextId++;
        this.folders = new ListImpl<>();
        this.usedSpace = 0;
    }

    /**
     * Returns the unique id of the disk.
     *
     * @return the id of the disk
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the free space that remains on the disk.
     *
     * @return the free space in MB
     */
    public int getFreeSpace() {
        return CAPACITY - usedSpace;
    }

    /**
     * Returns the list with the sizes of the folders stored on this disk.
     *
     * @return the list of folder sizes (in MB)
     */
    public List<Integer> getFolders() {
        return folders;
    }

    /**
     * Stores a folder on the disk, if it fits.
     *
     * @param size the size of the folder in MB
     * @return {@code true} if the folder was stored, {@code false} if there is not enough free space
     */
    public boolean addFolder(int size) {
        if (size < 0 || size > getFreeSpace()) {
            return false;
        }
        folders.insertAtBack(size);
        usedSpace += size;
        return true;
    }

    /**
     * Compares this disk with another disk based on their free space.
     *
     * @param other the other disk
     * @return a negative integer, zero or a positive integer as this disk has less,
     *         equal or more free space than the other disk
     */
    @Override
    public int compareTo(Disk other) {
        return Integer.compare(this.getFreeSpace(), other.getFreeSpace());
    }

    /**
     * Returns a description of the disk: its id, free space and folders.
     *
     * @return a string representation of the disk
     */
    @Override
    public String toString() {
        return "Disk " + id + " (free: " + getFreeSpace() + " MB) folders: [" + folders + "]";
    }
}
