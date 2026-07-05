package edu.aueb.ds.datastructures;

import java.util.Comparator;

/**
 * A max priority queue implemented using a binary heap.
 * The highest priority element is always at the root of the heap.
 *
 * @param <T> the type of elements held in this collection
 */
public class MaxPQ<T> implements PriorityQueueInterface<T> {
    /**
     * Heap based implementation of PriorityQueue.
     */
    private T[] heap; // the heap to store data in
    private int size; // current size of the queue
    private final Comparator<T> comparator; // the comparator to use between the objects

    private static final int DEFAULT_CAPACITY = 4; // default capacity
    private static final int AUTOGROW_SIZE = 4; // default auto grow

    /**
     * Default constructor for objects that implement the {@link Comparable} interface.
     * The natural ordering of the objects will be used for comparisons.
     */
    @SuppressWarnings("unchecked")
    public MaxPQ() {
        this.heap = (T[]) new Object[DEFAULT_CAPACITY + 1];
        this.size = 0;
        this.comparator = (o1, o2) -> ((Comparable<T>) o1).compareTo(o2);
    }

    /**
     * Constructs a new MaxPQ that orders its elements according to the specified comparator.
     *
     * @param comparator the comparator that will be used to order this priority queue.
     */
    @SuppressWarnings("unchecked")
    public MaxPQ(Comparator<T> comparator) {
        this.heap = (T[]) new Object[DEFAULT_CAPACITY + 1];
        this.size = 0;
        this.comparator = comparator;
    }

    /**
     * Inserts the specified element into this priority queue.
     * <p>
     * The item is placed at the first free slot of the heap (growing the array if it is full)
     * and is then moved up with {@link #swim(int)} until the heap property is restored.
     *
     *
     * <p>Complexity: O(log n) (amortized, because of the occasional array growth)
     * @param item the item to insert.
     */
    @Override
    public void add(T item) {
        if (size + 1 >= heap.length) {
            grow();
        }
        heap[++size] = item;
        swim(size);
    }

    /**
     * Retrieves, but does not remove, the head of this queue (the element with the highest priority),
     * or returns null if this queue is empty.
     *
     * @return the head of the queue, or null if the queue is empty
     */
    @Override
    public T peek() {
        // Ensure not empty
        if (size == 0)
            return null;

        // return root without removing
        return heap[1];
    }

    /**
     * Retrieves and removes the head of this queue (the element with the highest priority),
     * or returns null if this queue is empty.
     *
     * @return the head of the queue, or null if the queue is empty
     */
    @Override
    public T getMax() {
        // TODO: Implement the getMax method.
        // 1. Get the root of the heap.
        // 2. Replace the root with the last element in the heap.
        // 3. Decrement the size of the heap.
        // 4. Call the sink method to restore the heap property.
        // 5. Return the original root.

        if (size == 0)
            return null;
        T root = heap[1];
        heap[1] = heap[size];
        heap[size] = null; // do not keep a reference to the removed slot
        size--;
        sink(1);
        return root;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Helper function to swim items to the top.
     * While the item is greater than its parent (at {@code i/2}) the two are swapped.
     *
     * @param i the index of the item to swim
     */
    private void swim(int i) {
        while (i > 1 && comparator.compare(heap[i], heap[i / 2]) > 0) {
            swap(i, i / 2);
            i = i / 2;
        }
    }

    /**
     * Helper function to sink items to the bottom.
     * While the item is smaller than its greater child it is swapped with that child.
     *
     * @param i the index of the item to sink
     */
    private void sink(int i) {
        while (2 * i <= size) {
            int child = 2 * i;
            if (child + 1 <= size && comparator.compare(heap[child], heap[child + 1]) < 0) {
                child++; // the right child is the greater one
            }
            if (comparator.compare(heap[i], heap[child]) >= 0) {
                break;
            }
            swap(i, child);
            i = child;
        }
    }

    /**
     * Helper function to swap two elements in the heap
     *
     * @param i the first element's index
     * @param j the second element's index
     */
    private void swap(int i, int j) {
        T tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    /**
     * Helper function to grow the size of the heap
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        T[] newHeap = (T[]) new Object[heap.length + AUTOGROW_SIZE];

        // copy array
		//(notice: in the priority queue, elements are located in the array slots with positions in [1, size])
        for (int i = 0; i <= size; i++) {
            newHeap[i] = heap[i];
        }

        heap = newHeap;
    }
}