package edu.aueb.ds.datastructures;

import java.util.NoSuchElementException;

/**
 * Single-link List. Uses {@link Node} for list nodes.
 *
 * @param <T> the type of the elements in the list
 */
public class ListImpl<T> implements List<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Constructor. Initializes an empty list.
     */
    public ListImpl() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void insertAtFront(T data) {
        Node<T> newNode = new Node<>(data);

        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.setNext(head);
            head = newNode;
        }
        size++;
    }

    @Override
    public void insertAtBack(T data) {
        Node<T> newNode = new Node<>(data);

        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    @Override
    public T removeFromFront() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        T data = head.getData();
        head = head.getNext();
        size--;

        if (isEmpty()) {
            tail = null;
        }

        return data;
    }

    @Override
    public T removeFromBack() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        T data = tail.getData();

        if (head == tail) {
            // Only one element
            head = tail = null;
        } else {
            // Find the second-to-last node
            Node<T> current = head;
            while (current.getNext() != tail) {
                current = current.getNext();
            }
            current.setNext(null);
            tail = current;
        }
        size--;

        return data;
    }


    @Override
    public boolean isEmpty() {
        return head == null;
    }


    @Override
    public int size() {
        return size;
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
        StringBuilder ret = new StringBuilder();
        ret.append("[");

        while (current != null) {
            ret.append(current.data.toString());
            if (current.getNext() != null) {
                ret.append(", ");
            }
            current = current.next;
        }

        ret.append("]");
        return ret.toString();
    }
}
