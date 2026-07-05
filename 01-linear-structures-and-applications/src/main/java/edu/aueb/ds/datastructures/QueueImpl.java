package edu.aueb.ds.datastructures;

import java.io.PrintStream;
import java.util.NoSuchElementException;

/**
 * FIFO queue implemented on top of a single-link {@link ListImpl} that keeps
 * a pointer to its tail. Items are inserted at the <b>back</b> of the list and
 * removed from the <b>front</b>, so that every operation runs in O(1).
 *
 * @param <T> the type of the elements in the queue
 */
public class QueueImpl<T> implements Queue<T> {

    /** The underlying list; its head is the oldest item of the queue. */
    private final ListImpl<T> list;

    /**
     * Creates an empty queue.
     */
    public QueueImpl() {
        list = new ListImpl<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * {@inheritDoc}
     * Complexity: O(1) (insertion at the back, using the tail pointer).
     */
    @Override
    public void put(T item) {
        list.insertAtBack(item);
    }

    /**
     * {@inheritDoc}
     * Complexity: O(1) (removal from the front of the list).
     */
    @Override
    public T get() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return list.removeFromFront();
    }

    /**
     * {@inheritDoc}
     * Complexity: O(1) (the oldest item is removed and immediately re-inserted at the front).
     */
    @Override
    public T peek() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        T item = list.removeFromFront();
        list.insertAtFront(item);
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printQueue(PrintStream stream) {
        stream.println(list);
    }

    /**
     * {@inheritDoc}
     * Complexity: O(1) (the list keeps its size in a field).
     */
    @Override
    public int size() {
        return list.size();
    }
}
