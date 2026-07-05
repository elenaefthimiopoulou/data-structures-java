package edu.aueb.ds;

import edu.aueb.ds.datastructures.Queue;
import edu.aueb.ds.datastructures.QueueImpl;
import edu.aueb.ds.datastructures.Stack;
import edu.aueb.ds.datastructures.StackImpl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/**
 * A test class for Stack and Queue implementations.
 */
public class TestDataStructures {

    /**
     * Default constructor.
     */
    public TestDataStructures() {
    }

    private static final long MAX_EXECUTION_TIME_MS = 120_000; // 2 minutes



    /**
     * The main method of the test class.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        System.out.println("--- Testing Generic Stack and Queue ---");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(() -> {
            testInteger();
            testDouble();
            testString();
        });

        try {
            future.get(MAX_EXECUTION_TIME_MS, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true); // Interrupt the thread
            System.out.println("\nERROR: Total execution time exceeded 2 minutes. Terminating.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdownNow(); // Shutdown the executor service
            long endTime = System.currentTimeMillis();
            System.out.println("\nTotal execution time: " + (endTime - startTime) + "ms");
        }
    }

    private static void testString() {
        System.out.println("\n--- Testing with String ---");
        Stack<String> stack = new StackImpl<>();
        Queue<String> queue = new QueueImpl<>();
        int[] itemCounts = {1_000, 10_000, 100_000};
        testImplementations(stack, queue, "Hello", "World", "!", () -> "Item", itemCounts);
    }

    private static void testInteger() {
        System.out.println("\n--- Testing with Integer ---");
        Stack<Integer> stack = new StackImpl<>();
        Queue<Integer> queue = new QueueImpl<>();
        int[] itemCounts = {1_000, 10_000, 100_000};
        testImplementations(stack, queue, 1, 2, 3, () -> 42, itemCounts);
    }

    private static void testDouble() {
        System.out.println("\n--- Testing with Double ---");
        Stack<Double> stack = new StackImpl<>();
        Queue<Double> queue = new QueueImpl<>();
        int[] itemCounts = {1_000, 10_000, 100_000};
        testImplementations(stack, queue, 1.1, 2.2, 3.3, () -> 42.0, itemCounts);
    }

    private static <T> void testImplementations(Stack<T> stack, Queue<T> queue, T item1, T item2, T item3, Supplier<T> itemSupplier, int[] itemCounts) {
        // --- Edge Case Testing ---
        System.out.println("\n--- Edge Case Testing ---");

        // Test isEmpty on empty structures
        System.out.println("Test isEmpty on empty structures");
        System.out.println("Is stack empty? " + stack.isEmpty());
        System.out.println("Is queue empty? " + queue.isEmpty());

        // Test pop/get on empty structures
        System.out.println("Test pop/get on empty structures");
        try {
            stack.pop();
            System.out.println("FAILED: Popping from empty stack should have thrown an exception.");
        } catch (NoSuchElementException e) {
            System.out.println("SUCCESS: Caught expected exception when popping from empty stack: " + e);
        }
        try {
            queue.get();
            System.out.println("FAILED: Getting from empty queue should have thrown an exception.");
        } catch (NoSuchElementException e) {
            System.out.println("SUCCESS: Caught expected exception when getting from empty queue: " + e);
        }

        // Test peek on empty structures
        System.out.println("Test peek on empty structures");
        try {
            stack.peek();
            System.out.println("FAILED: Peeking from empty stack should have thrown an exception.");
        } catch (NoSuchElementException e) {
            System.out.println("SUCCESS: Caught expected exception when peeking from empty stack: " + e);
        }
        try {
            queue.peek();
            System.out.println("FAILED: Peeking from empty queue should have thrown an exception.");
        } catch (NoSuchElementException e) {
            System.out.println("SUCCESS: Caught expected exception when peeking from empty queue: " + e);
        }

        // --- LIFO vs. FIFO Demonstration ---
        System.out.println("\n--- LIFO vs. FIFO Demonstration ---");

        // Push/Put items
        stack.push(item1);
        stack.push(item2);
        stack.push(item3);
        queue.put(item1);
        queue.put(item2);
        queue.put(item3);

        System.out.println("Stack (LIFO) output:");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
        System.out.println("\n");

        System.out.println("Queue (FIFO) output:");
        while (!queue.isEmpty()) {
            System.out.print(queue.get() + " ");
        }
        System.out.println("\n");

        System.out.println("Note: The stack should have printed the items in reverse order of insertion (Last-In, First-Out), ");
        System.out.println("and the queue should have printed the items in the order of insertion (First-In, First-Out).");

        // --- Performance Testing ---
        System.out.println("\n--- Performance Testing ---");
        System.out.println("Approximate times for good implementation:");
        System.out.println("1,000 items: ~0.04ms");
        System.out.println("10,000 items: ~0.24ms");
        System.out.println("100,000 items: ~4.00ms");

        int runs = 5;
        Map<String, double[]> medianTimes = new HashMap<>();

        for (int i = 0; i < itemCounts.length; i++) {
            int count = itemCounts[i];
            System.out.println("\n--- Testing with " + String.format("%,d", count) + " items ---");

            double[] stackPushTimes = new double[runs];
            double[] stackPopTimes = new double[runs];
            double[] stackSizeTimes = new double[runs];
            double[] queuePutTimes = new double[runs];
            double[] queueGetTimes = new double[runs];
            double[] queueSizeTimes = new double[runs];

            for (int run = 0; run < runs; run++) {
                System.gc();

                // Stack performance
                long startTime = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    stack.push(itemSupplier.get());
                }
                long endTime = System.nanoTime();
                stackPushTimes[run] = (endTime - startTime) / 1_000_000.0;

                startTime = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    stack.size();
                }
                endTime = System.nanoTime();
                stackSizeTimes[run] = (endTime - startTime) / 1_000_000.0;

                startTime = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    stack.pop();
                }
                endTime = System.nanoTime();
                stackPopTimes[run] = (endTime - startTime) / 1_000_000.0;

                // Queue performance
                startTime = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    queue.put(itemSupplier.get());
                }
                endTime = System.nanoTime();
                queuePutTimes[run] = (endTime - startTime) / 1_000_000.0;

                startTime = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    queue.size();
                }
                endTime = System.nanoTime();
                queueSizeTimes[run] = (endTime - startTime) / 1_000_000.0;

                startTime = System.nanoTime();
                for (int j = 0; j < count; j++) {
                    queue.get();
                }
                endTime = System.nanoTime();
                queueGetTimes[run] = (endTime - startTime) / 1_000_000.0;
            }

            Arrays.sort(stackPushTimes);
            Arrays.sort(stackPopTimes);
            Arrays.sort(stackSizeTimes);
            Arrays.sort(queuePutTimes);
            Arrays.sort(queueGetTimes);
            Arrays.sort(queueSizeTimes);

            double stackPushMedian = stackPushTimes[runs / 2];
            double stackPopMedian = stackPopTimes[runs / 2];
            double stackSizeMedian = stackSizeTimes[runs / 2];
            double queuePutMedian = queuePutTimes[runs / 2];
            double queueGetMedian = queueGetTimes[runs / 2];
            double queueSizeMedian = queueSizeTimes[runs / 2];

            System.out.println(String.format("Stack push median time: %.2fms", stackPushMedian));
            System.out.println(String.format("Stack pop median time:  %.2fms", stackPopMedian));
            System.out.println(String.format("Stack size median time: %.2fms", stackSizeMedian));
            System.out.println(String.format("Queue put median time:  %.2fms", queuePutMedian));
            System.out.println(String.format("Queue get median time:  %.2fms", queueGetMedian));
            System.out.println(String.format("Queue size median time: %.2fms", queueSizeMedian));

            if (i > 0) {
                if (medianTimes.get("Stack push")[i-1] > 0 && stackPushMedian > 20 * (1 + medianTimes.get("Stack push")[i-1])) {
                    System.out.println("WARNING: The median time for 'Stack push' increased significantly between " + String.format("%,d", itemCounts[i-1]) + " and " + String.format("%,d", count) + " items.");
                    System.out.println("This could indicate a non-O(1) implementation.");
                }
                if (medianTimes.get("Stack pop")[i-1] > 0 && stackPopMedian > 20 * (1 + medianTimes.get("Stack pop")[i-1])) {
                    System.out.println("WARNING: The median time for 'Stack pop' increased significantly between " + String.format("%,d", itemCounts[i-1]) + " and " + String.format("%,d", count) + " items.");
                    System.out.println("This could indicate a non-O(1) implementation.");
                }
                if (medianTimes.get("Stack size")[i-1] > 0 && stackSizeMedian > 20 * (1 + medianTimes.get("Stack size")[i-1])) {
                    System.out.println("WARNING: The median time for 'Stack size' increased significantly between " + String.format("%,d", itemCounts[i-1]) + " and " + String.format("%,d", count) + " items.");
                    System.out.println("This could indicate a non-O(1) implementation.");
                }
                if (medianTimes.get("Queue put")[i-1] > 0 && queuePutMedian > 20 * (1 + medianTimes.get("Queue put")[i-1])) {
                    System.out.println("WARNING: The median time for 'Queue put' increased significantly between " + String.format("%,d", itemCounts[i-1]) + " and " + String.format("%,d", count) + " items.");
                    System.out.println("This could indicate a non-O(1) implementation.");
                }
                if (medianTimes.get("Queue get")[i-1] > 0 && queueGetMedian > 20 * (1 + medianTimes.get("Queue get")[i-1])) {
                    System.out.println("WARNING: The median time for 'Queue get' increased significantly between " + String.format("%,d", itemCounts[i-1]) + " and " + String.format("%,d", count) + " items.");
                    System.out.println("This could indicate a non-O(1) implementation.");
                }
                if (medianTimes.get("Queue size")[i-1] > 0 && queueSizeMedian > 20 * (1 + medianTimes.get("Queue size")[i-1])) {
                    System.out.println("WARNING: The median time for 'Queue size' increased significantly between " + String.format("%,d", itemCounts[i-1]) + " and " + String.format("%,d", count) + " items.");
                    System.out.println("This could indicate a non-O(1) implementation.");
                }
            }

            medianTimes.computeIfAbsent("Stack push", k -> new double[itemCounts.length])[i] = stackPushMedian;
            medianTimes.computeIfAbsent("Stack pop", k -> new double[itemCounts.length])[i] = stackPopMedian;
            medianTimes.computeIfAbsent("Stack size", k -> new double[itemCounts.length])[i] = stackSizeMedian;
            medianTimes.computeIfAbsent("Queue put", k -> new double[itemCounts.length])[i] = queuePutMedian;
            medianTimes.computeIfAbsent("Queue get", k -> new double[itemCounts.length])[i] = queueGetMedian;
            medianTimes.computeIfAbsent("Queue size", k -> new double[itemCounts.length])[i] = queueSizeMedian;
        }
        System.out.println("\nAutomated analysis complete.");
    }
}