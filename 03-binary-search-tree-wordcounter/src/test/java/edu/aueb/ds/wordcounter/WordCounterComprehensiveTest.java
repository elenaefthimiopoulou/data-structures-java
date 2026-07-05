package edu.aueb.ds.wordcounter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class WordCounterComprehensiveTest {

    private static WordCounter wc;
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("--- Running Comprehensive WordCounter Tests ---");
        runAllTests();
        System.out.println("\n--- Test Summary ---");
        System.out.println("Total Tests: " + (testsPassed + testsFailed));
        System.out.println("Passed: " + testsPassed);
        System.out.println("Failed: " + testsFailed);
        System.out.println("--------------------");

        if (testsFailed > 0) {
            System.exit(1); // Exit with error code if any test failed
        }
    }

    private static void runAllTests() {
        testInsertAndFrequency();
        testSearchAndOptimization();
        testRemove();
        testStopWords();
        testLoadFromFile();
        testGetMaxFrequency();
        testGetMeanFrequency();
        testPrintByWord();
        testPrintByFrequency();
    }

    // Resets the WordCounter instance for a clean test environment
    private static void setup() {
        wc = new BST();
    }

    // Custom assertion method to check for equality
    private static void check(Object expected, Object actual, String message) {
        if (expected.equals(actual)) {
            System.out.println("[PASS] " + message);
            testsPassed++;
        } else {
            System.err.println("[FAIL] " + message);
            System.err.println("  -> Expected: " + expected);
            System.err.println("  -> Actual:   " + actual);
            testsFailed++;
        }
    }

    // Custom assertion for null objects
    private static void checkNull(Object actual, String message) {
        if (actual == null) {
            System.out.println("[PASS] " + message);
            testsPassed++;
        } else {
            System.err.println("[FAIL] " + message);
            System.err.println("  -> Expected: null");
            System.err.println("  -> Actual:   " + actual);
            testsFailed++;
        }
    }

    // Custom assertion for non-null objects
    private static void checkNotNull(Object actual, String message) {
        if (actual != null) {
            System.out.println("[PASS] " + message);
            testsPassed++;
        } else {
            System.err.println("[FAIL] " + message);
            System.err.println("  -> Expected: non-null");
            System.err.println("  -> Actual:   null");
            testsFailed++;
        }
    }

    public static void testInsertAndFrequency() {
        System.out.println("\n--- Testing: insert, getFrequency, getNumTotalWords, getNumDistinctWords ---");
        setup();
        wc.insert("hello");
        wc.insert("world");
        wc.insert("hello");

        check(2, wc.getFrequency("hello"), "Frequency of 'hello' is 2");
        check(1, wc.getFrequency("world"), "Frequency of 'world' is 1");
        check(0, wc.getFrequency("java"), "Frequency of a non-existent word is 0");
        check(3, wc.getNumTotalWords(), "Total words is 3");
        check(2, wc.getNumDistinctWords(), "Distinct words is 2");
    }

    public static void testSearchAndOptimization() {
        System.out.println("\n--- Testing: search (including root-moving optimization) ---");
        setup();
        wc.insert("frequent");
        wc.insert("frequent");
        wc.insert("frequent"); // freq = 3
        wc.insert("rare");     // freq = 1
        wc.insert("medium");   // freq = 2
        wc.insert("medium");

        // Mean frequency is (3 + 1 + 2) / 3 = 2.0
        // "frequent" has a frequency > 2.0, so it should be moved to the root.
        // We can't directly check if it's at the root, but we can verify the search works.
        WordFrequency wf = wc.search("frequent");
        checkNotNull(wf, "Search for 'frequent' returns a non-null object");
        if (wf != null) {
            check(3, wf.getFrequency(), "Frequency of searched 'frequent' is 3");
        }

        WordFrequency wfRare = wc.search("rare");
        checkNotNull(wfRare, "Search for 'rare' (freq < mean) returns a non-null object");
        if (wfRare != null) {
            check(1, wfRare.getFrequency(), "Frequency of searched 'rare' is 1");
        }

        checkNull(wc.search("nonexistent"), "Search for a non-existent word returns null");
    }

    public static void testRemove() {
        System.out.println("\n--- Testing: remove ---");
        setup();
        wc.insert("one");
        wc.insert("two");
        wc.insert("two");

        wc.remove("two");
        check(1, wc.getFrequency("two"), "Frequency of 'two' is 1 after first remove");
        check(2, wc.getNumDistinctWords(), "Distinct words is still 2");
        check(2, wc.getNumTotalWords(), "Total words is 2 after first remove");

        wc.remove("two");
        check(0, wc.getFrequency("two"), "Frequency of 'two' is 0 after second remove");
        check(1, wc.getNumDistinctWords(), "Distinct words is 1 after final remove of 'two'");
        check(1, wc.getNumTotalWords(), "Total words is 1 after final remove");

        wc.remove("one");
        check(0, wc.getFrequency("one"), "Frequency of 'one' is 0 after remove");
        check(0, wc.getNumDistinctWords(), "Distinct words is 0 after removing last word");
        check(0, wc.getNumTotalWords(), "Total words is 0 after removing last word");

        wc.remove("nonexistent"); // Should not throw an error or change counts
        check(0, wc.getNumDistinctWords(), "Removing a non-existent word does not change distinct count");
    }

    public static void testStopWords() {
        System.out.println("\n--- Testing: addStopWord, removeStopWord ---");
        setup();
        wc.addStopWord("the");
        wc.addStopWord("is");

        wc.insert("the");
        wc.insert("fox");
        wc.insert("is");
        wc.insert("fast");

        check(0, wc.getFrequency("the"), "Stopword 'the' is not counted");
        check(0, wc.getFrequency("is"), "Stopword 'is' is not counted");
        check(1, wc.getFrequency("fox"), "Non-stopword 'fox' is counted");
        check(2, wc.getNumDistinctWords(), "Distinct count ignores stopwords");

        wc.removeStopWord("is");
        wc.insert("is");
        check(1, wc.getFrequency("is"), "'is' is counted after being removed from stopwords");
        check(3, wc.getNumDistinctWords(), "Distinct count updates after stopword is removed and word re-inserted");
    }

    public static void testLoadFromFile() {
        System.out.println("\n--- Testing: load ---");
        setup();
        String testFilename = "src/test/data/comprehensive_test_file.txt";
        try (PrintWriter out = new PrintWriter(testFilename)) {
            out.println("Hello world, this is a test file.");
            out.println("A TEST with hello again/again.");
        } catch (IOException e) {
            System.err.println("CRITICAL: Failed to create test file. Aborting test.");
            testsFailed++;
            return;
        }

        wc.addStopWord("a");
        wc.addStopWord("is");
        wc.addStopWord("this");

        wc.load(testFilename);

        check(2, wc.getFrequency("hello"), "Load: 'hello' frequency is 2");
        check(1, wc.getFrequency("world"), "Load: 'world' frequency is 1");
        check(2, wc.getFrequency("test"), "Load: 'test' frequency is 2");
        check(1, wc.getFrequency("file"), "Load: 'file' frequency is 1");
        check(2, wc.getFrequency("again"), "Load: 'again' (from slash) frequency is 2");
        check(0, wc.getFrequency("this"), "Load: stopword 'this' is ignored");
        check(9, wc.getNumTotalWords(), "Load: Total words are correct");
        check(6, wc.getNumDistinctWords(), "Load: Distinct words are correct");

        new File(testFilename).delete();
    }

    public static void testGetMaxFrequency() {
        System.out.println("\n--- Testing: getMaxFrequency ---");
        setup();
        checkNull(wc.getMaxFrequency(), "Max frequency is null for an empty tree");

        wc.insert("apple");
        wc.insert("banana");
        wc.insert("cherry");
        wc.insert("banana");
        wc.insert("cherry");
        wc.insert("cherry");

        WordFrequency max = wc.getMaxFrequency();
        checkNotNull(max, "Max frequency is not null for a populated tree");
        if (max != null) {
            check("cherry", max.key(), "Word with max frequency is 'cherry'");
            check(3, max.getFrequency(), "Max frequency value is 3");
        }
    }

    public static void testGetMeanFrequency() {
        System.out.println("\n--- Testing: getMeanFrequency ---");
        setup();
        check(0.0, wc.getMeanFrequency(), "Mean frequency is 0.0 for an empty tree");

        wc.insert("apple");   // freq 1
        wc.insert("banana");  // freq 2
        wc.insert("banana");
        wc.insert("cherry");  // freq 3
        wc.insert("cherry");
        wc.insert("cherry");

        // Total words = 6, Distinct words = 3. Mean = 6 / 3 = 2.0
        check(2.0, wc.getMeanFrequency(), "Mean frequency is calculated correctly");
    }

    public static void testPrintByWord() {
        System.out.println("\n--- Testing: printTreeByWord ---");
        setup();
        wc.insert("cherry");
        wc.insert("apple");
        wc.insert("banana");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wc.printTreeByWord(new PrintStream(baos));
        String output = baos.toString().trim().replace("\r\n", "\n");

        String expected = "apple: 1\nbanana: 1\ncherry: 1";
        check(expected, output, "printTreeByWord output is alphabetically sorted");
    }

    public static void testPrintByFrequency() {
        System.out.println("\n--- Testing: printTreeByFrequency ---");
        setup();
        wc.insert("apple");   // 2
        wc.insert("cherry");  // 3
        wc.insert("banana");  // 1
        wc.insert("cherry");
        wc.insert("apple");
        wc.insert("cherry");
        wc.insert("date");    // 2, should come after 'apple'
        wc.insert("date");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        wc.printTreeByFrequency(new PrintStream(baos));
        String output = baos.toString().trim().replace("\r\n", "\n");

        String expected = "cherry: 3\napple: 2\ndate: 2\nbanana: 1";
        check(expected, output, "printTreeByFrequency is sorted by descending frequency, then alphabetically");
    }
}
