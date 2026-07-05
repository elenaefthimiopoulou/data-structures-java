package edu.aueb.ds.datastructures;

import java.util.NoSuchElementException;

/**
 * Defines the methods for a generic List.
 *
 * @param <T> the type of the elements in the list
 */
public interface List<T> {
    /**
     * Inserts the data at the front of the list
     *
     * @param data the inserted data
     */
    void insertAtFront(T data);

    /**
     * Inserts the data at the end of the list
     *
     * @param data the inserted item
     */
    void insertAtBack(T data);

    /**
     * Returns and removes the data from the list head
     *
     * @return the data contained in the list head
     * @throws NoSuchElementException if the list is empty
     */
    T removeFromFront() throws NoSuchElementException;

    /**
     * Returns and removes the data from the list tail
     *
     * @return the data contained in the list tail
     * @throws NoSuchElementException if the list is empty
     */
    T removeFromBack() throws NoSuchElementException;

    /**
     * Determine whether list is empty
     *
     * @return true if list is empty
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list.
     */
    int size();

    /**
     * Returns a string representation of the list.
     * The string representation consists of a list of the list's elements in the
     * order they are returned by its iterator, enclosed in square brackets
     * ("[]"). Adjacent elements are separated by the characters ", " (comma and space).
     * e.g., for a list containing "a", "b", and "c", the output should be "[a, b, c]".
     * An empty list will be represented as "[]".
     *
     * @return a string representation of the list
     */
    @Override
    String toString();
}