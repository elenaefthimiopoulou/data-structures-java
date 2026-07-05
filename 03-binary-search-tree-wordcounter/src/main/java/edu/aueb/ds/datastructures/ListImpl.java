package edu.aueb.ds.datastructures;

import java.util.NoSuchElementException;

/**
 * A custom implementation of a singly-linked list.
 * Uses {@link Node} for list nodes.
 *
 * @param <T> the type of the elements in the list
 */
public class ListImpl<T> implements List<T> {

    /** The first node of the list. */
    private Node<T> head = null;
    /** The last node of the list; it makes {@link #insertAtBack(Object)} an O(1) operation. */
    private Node<T> tail = null;
    /** The number of elements in the list. */
    private int size = 0;

    /**
     * Default constructor.
     */
    public ListImpl() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertAtFront(T data) {
        Node<T> n = new Node<>(data);
        if (isEmpty()) {
            head = n;
            tail = n;
        } else {
            n.setNext(head);
            head = n;
        }
        size++;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Runs in O(1): the new node is linked after the current tail, which then moves to the new node.
     */
    @Override
    public void insertAtBack(T data) {
        Node<T> n = new Node<>(data);
        if (isEmpty()) {
            head = n;
            tail = n;
        } else {
            tail.setNext(n);
            tail = n;
        }
        size++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T removeFromFront() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException();

        T data = head.getData();

        if (head == tail) {
            head = tail = null;
        } else {
            head = head.getNext();
        }

        size--;
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T removeFromBack() throws NoSuchElementException {
        if (isEmpty())
            throw new NoSuchElementException();

        T data = tail.getData();

        if (head == tail) {
            head = tail = null;
        } else {
            Node<T> iterator = head;
            while (iterator.getNext() != tail)
                iterator = iterator.getNext();

            iterator.setNext(null);
            // the second-to-last node becomes the new tail
            tail = iterator;
        }

        size--;
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Node<T> getHead() {
        return this.head;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHead(Node<T> head) {
        this.head = head;
        // This logic is complex and provided for the student.
        if (this.head == null) {
            this.tail = null;
        } else {
            Node<T> current = this.head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            this.tail = current;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        Node<T> current = head;
        StringBuilder ret = new StringBuilder("[");
        while (current != null) {
            ret.append(current.getData().toString());
            if (current.getNext() != null) {
                ret.append(", ");
            }
            current = current.getNext();
        }
        ret.append("]");
        return ret.toString();
    }
}
