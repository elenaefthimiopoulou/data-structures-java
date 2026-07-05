package edu.aueb.ds.datastructures;

/**
 * This class is used to test the MaxPQ implementation.
 *
 * You can run this file to check the correctness of your implementation.
 * For instructions on how to run it from the command line, please refer
 * to the README.md file in this directory.
 */
public class PriorityQueueMain {

    /**
     * Private constructor to prevent instantiation.
     */
    private PriorityQueueMain() {}

    /**
     * The main method.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        PriorityQueueInterface<String> pq = new MaxPQ<>(new StringComparator());

        System.out.println("--- Testing GenericHeapPriorityQueue<String> ---");

        // Test 1: Peek on an empty queue
        System.out.println("1. Testing peek() on a new queue.");
        System.out.println("Expected: null");
        System.out.println("Result: " + pq.peek());
        System.out.println();

        // Test 2: GetMax on an empty queue
        System.out.println("2. Testing getMax() on an empty queue.");
        System.out.println("Expected: null");
        System.out.println("Result: " + pq.getMax());
        System.out.println();

        // Test 3: Add elements
        System.out.println("3. Adding elements: \"Papadopoulou\", \"Georgiadou\", \"Nikolaidou\", \"Papadakis\", \"Vasilopoulou\", \"Theodorou\"");
        pq.add("Papadopoulou");
        pq.add("Georgiadou");
        pq.add("Nikolaidou");
        pq.add("Papadakis");
        pq.add("Vasilopoulou");
        pq.add("Theodorou");
        System.out.println("Finished adding elements.");
        System.out.println();

        // Test 4: Peek at the max element
        System.out.println("4. Testing peek() on a non-empty queue.");
        System.out.println("Expected: Vasilopoulou");
        System.out.println("Result: " + pq.peek());
        System.out.println();

        // Test 5: Get max elements to check order and test empty case
        System.out.println("5. Getting max elements to check order (should be 6 elements + 1 null).");
        System.out.println("Expected order: Vasilopoulou, Theodorou, Papadopoulou, Papadakis, Nikolaidou, Georgiadou, null");
        System.out.print("Result: ");
        int elementsToGet = 7; // 6 elements were added, we try to get 7
        for (int i = 0; i < elementsToGet; i++) {
            System.out.print(pq.getMax() + " ");
        }
        System.out.println("\nFinished getting max elements.");
        System.out.println();

        // Test 6: Check if the queue is empty again
        System.out.println("6. Testing peek() after getting all elements.");
        System.out.println("Expected: null");
        System.out.println("Result: " + pq.peek());
        System.out.println();

        // Test 7: Add and GetMax a single element to test reuse
        System.out.println("7. Testing add and getMax on a single element.");
        System.out.println("Adding: \"Zografou\"");
        pq.add("Zografou");
        System.out.println("Peek result: " + pq.peek());
        System.out.println("GetMax result: " + pq.getMax());
        System.out.println("Peek after getMax: " + pq.peek());

        System.out.println("\n--- Testing Complete ---");
    }
}
