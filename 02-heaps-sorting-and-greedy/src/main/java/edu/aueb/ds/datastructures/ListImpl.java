package edu.aueb.ds.datastructures;

import java.util.NoSuchElementException;

/**
 * Single-link List. Uses {@link Node} for list nodes.
 *
 * @param <T> the type of the elements in the list
 */
public class ListImpl<T> implements List<T> {

    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    /**
     * Default constructor
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

        if (head == tail)
            head = tail = null;
        else
            head = head.getNext();

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

        if (head == tail)
            head = tail = null;
        else {
            Node<T> iterator = head;
            while (iterator.getNext() != tail)
                iterator = iterator.getNext();

            iterator.setNext(null);
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

        if (this.head == null) {
            this.tail = null;
        } else {
            // After setting a new head, we must traverse the list to find the new tail.
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
            return "";
        }

        Node<T> current = head;
        StringBuilder ret = new StringBuilder();

        while (current != null) {
            ret.append(current.getData().toString());
            if (current.getNext() != null) {
                ret.append(" ");
            }
            current = current.getNext();
        }

        return ret.toString();
    }
}
