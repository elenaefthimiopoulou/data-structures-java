package edu.aueb.ds.sort;

import edu.aueb.ds.datastructures.List;
import edu.aueb.ds.datastructures.Node;

/**
 * Provides a static method to sort a custom {@link List} of integers using a native linked list Mergesort algorithm.
 * This implementation uses the list's {@code size()} method to efficiently split the list for recursion.
 */
public class Sort {

    /**
     * Private constructor to prevent instantiation.
     */
    private Sort() {}

    /**
     * Sorts a custom {@link List} of integers in descending order using Mergesort.
     * This method is a wrapper that initiates the recursive sorting process.
     *
     *
     * <p>Complexity: O(n log n)
     * @param list The list of integers to be sorted. The list is modified directly.
     */
    public static void mergeSort(List<Integer> list) {
        if (list == null || list.size() <= 1) {
            return;
        }

        Node<Integer> head = list.getHead();
        head = mergeSortRecursive(head, list.size());
        list.setHead(head);
    }

    /**
     * The recursive core of the Mergesort algorithm.
     * It splits the list into two halves based on the provided size, sorts them recursively, and then merges them.
     *
     * @param head The head of the linked list (or sublist) to sort.
     * @param size The number of elements in the current list segment.
     * @return The head of the new, sorted linked list segment.
     */
    private static Node<Integer> mergeSortRecursive(Node<Integer> head, int size) {
        if (size <= 1) {
            return head; // zero or one node: already sorted
        }

        // 1. Find the last node of the first half and cut the list there.
        int mid = size / 2;
        Node<Integer> lastOfLeft = head;
        for (int i = 1; i < mid; i++) {
            lastOfLeft = lastOfLeft.getNext();
        }
        Node<Integer> rightHead = lastOfLeft.getNext();
        lastOfLeft.setNext(null);

        // 2. Sort the two halves recursively.
        Node<Integer> left = mergeSortRecursive(head, mid);
        Node<Integer> right = mergeSortRecursive(rightHead, size - mid);

        // 3. Merge the sorted halves.
        return merge(left, right);
    }

    /**
     * Merges two sorted linked lists into a single, sorted linked list in descending order.
     * The merge is stable: for equal values the node of the left list comes first.
     *
     * @param left The head of the first sorted list.
     * @param right The head of the second sorted list.
     * @return The head of the final merged and sorted list.
     */
    private static Node<Integer> merge(Node<Integer> left, Node<Integer> right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        Node<Integer> head;
        if (left.getData() >= right.getData()) {
            head = left;
            left = left.getNext();
        } else {
            head = right;
            right = right.getNext();
        }

        Node<Integer> tail = head;
        while (left != null && right != null) {
            if (left.getData() >= right.getData()) {
                tail.setNext(left);
                left = left.getNext();
            } else {
                tail.setNext(right);
                right = right.getNext();
            }
            tail = tail.getNext();
        }

        // One of the lists is exhausted: append what remains of the other one.
        tail.setNext(left != null ? left : right);
        return head;
    }
}
