package edu.aueb.ds.datastructures;

import java.io.PrintStream;
import java.util.NoSuchElementException;

/**
 * Defines the methods for a FIFO queue that handles generic items.
 *
 * @param <T> the type of the elements in the queue
 */
public interface Queue<T> {

    /**
     * Determines whether the queue is empty.
     *
     * @return true if the queue is empty
     */
    boolean isEmpty();

    /**
     * Inserts an item to the queue.
     *
     * @param item the item to be inserted
     */
    void put(T item);

    /**
     * Removes and returns the oldest item of the queue.
     *
     * @return oldest item of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    T get() throws NoSuchElementException;

    /**
     * Returns without removing the oldest item of the queue.
     *
     * @return oldest item of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    T peek() throws NoSuchElementException;

    /**
     * Prints the elements of the queue, starting from the oldest
     * item, to the print stream given as an argument.
     *
     * @param stream the print stream
     */
    void printQueue(PrintStream stream);

    /**
     * Returns the size of the queue, 0 if it is empty.
     *
     * @return number of elements in the queue
     */
    int size();
}