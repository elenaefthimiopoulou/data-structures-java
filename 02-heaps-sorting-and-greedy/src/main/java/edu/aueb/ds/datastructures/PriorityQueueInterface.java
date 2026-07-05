package edu.aueb.ds.datastructures;


/**
 * Interface for Priority Queue
 * @param <T> the type of elements held in this collection
 */
public interface PriorityQueueInterface<T> {
    /**
     * Inserts the specified element into this priority queue.
     *
     * @param item the item to insert.
     */
    void add(T item);


    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     *
     * @return the head of the queue
     */
    T peek();


    /**
     * Retrieves and removes the head of this queue, or returns null if this queue is empty.
     *
     * @return the head of the queue
     */
    T getMax();

    /**
     * Returns true if this priority queue contains no elements.
     *
     * @return true if this priority queue contains no elements
     */
    boolean isEmpty();
}
