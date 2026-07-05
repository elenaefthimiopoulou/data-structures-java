package edu.aueb.ds.datastructures;

import java.io.PrintStream;
import java.util.NoSuchElementException;

/**
 * LIFO stack implemented on top of a single-link {@link ListImpl}.
 * The top of the stack is the <b>front</b> of the list, so that every
 * operation runs in O(1).
 *
 * @param <T> the type of the elements in the stack
 */
public class StackImpl<T> implements Stack<T> {

    /** The underlying list; its head is the top of the stack. */
    private final ListImpl<T> list;

    /**
     * Creates an empty stack.
     */
    public StackImpl() {
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
     * Complexity: O(1) (insertion at the front of the list).
     */
    @Override
    public void push(T item) {
        list.insertAtFront(item);
    }

    /**
     * {@inheritDoc}
     * Complexity: O(1) (removal from the front of the list).
     */
    @Override
    public T pop() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return list.removeFromFront();
    }

    /**
     * {@inheritDoc}
     * Complexity: O(1) (the top element is removed and immediately re-inserted at the front).
     */
    @Override
    public T peek() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        T item = list.removeFromFront();
        list.insertAtFront(item);
        return item;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printStack(PrintStream stream) {
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
