package edu.aueb.ds.datastructures;

import java.io.PrintStream;
import java.util.NoSuchElementException;

/**
 * Defines the methods for a Stack that handles generic items.
 *
 * @param <T> the type of the elements in the stack
 */
public interface Stack<T> {

    /**
     * Determines whether the stack is empty.
     *
     * @return true if the stack is empty
     */
    boolean isEmpty();

    /**
     * Inserts an item to the stack.
     *
     * @param item the item to be inserted
     */
    void push(T item);

    /**
     * Removes and returns the item on the top of the stack.
     *
     * @return the item on the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    T pop() throws NoSuchElementException;

    /**
     * Returns without removing the item on the top of the stack.
     *
     * @return the item on the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    T peek() throws NoSuchElementException;

    /**
     * Prints the elements of the stack, starting from the item on the top,
     * to the stream given as an argument.
     *
     * @param stream the print stream
     */
    void printStack(PrintStream stream);

    /**
     * Returns the size of the stack, 0 if it is empty.
     *
     * @return the number of items currently in the stack
     */
    int size();

}