package edu.aueb.ds.wordcounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WordCounterTest {

    public static void main(String[] args) {
        runTest("test1");
        runTest("papadimitriou");
        runTest("artificial_intelligence");
    }

    private static void runTest(String testName) {
        System.out.println("--- Running Test: " + testName + " ---");
        WordCounter wc = new BST();
        if (testName.equals("papadimitriou")) {
            String[] stopWords = {
                "a", "an", "the", "of", "in", "to", "and", "is", "he", "at", "for", "with", "was", "on",
                "his", "from", "that", "it", "by", "as", "his", "had", "has", "also", "been", "its",
                "they", "their", "which", "what", "where", "then", "our"
            };
            for (String word : stopWords) {
                wc.addStopWord(word);
            }
        } else if (testName.equals("artificial_intelligence")) {
            String[] stopWords = {
                "about", "after", "all", "also", "an", "and", "any", "are", "as", "at", "be",
                "because", "been", "but", "by", "can", "could", "did", "do", "even", "for",
                "from", "had", "has", "have", "he", "her", "him", "his", "how", "if", "in", "into",
                "is", "it", "its", "just", "made", "many", "may", "more", "most", "much",
                "not", "of", "on", "or", "other", "out", "over", "said", "should", "since",
                "so", "some", "still", "such", "than", "that", "the", "their", "them", "then",
                "there", "these", "they", "this", "those", "through", "to", "under", "up",
                "use", "very", "was", "we", "well", "were", "what", "when", "where", "which",
                "while", "who", "will", "with", "would", "you", "your", "s", "us", "she",
                // New words from user's final review
                "used", "including", "make", "include", "using", "between", "however", "one",
                "possible", "several", "first", "each", "no", "only", "two", "being", "go",
                "like", "often", "both", "known", "according", "right", "among", "another",
                "choose", "does", "during", "especially", "given", "must", "require", "take",
                "uses", "whether", "widely", "within", "without", "although", "already",
                "cannot", "eventually", "exactly", "further", "itself", "might", "once",
                "others", "our", "own", "rather", "same", "sometimes", "therefore",
                "throughout", "thus", "too", "until", "upon", "whose"
            };
            for (String word : stopWords) {
                wc.addStopWord(word);
            }
        } else {
            wc.addStopWord("a");
            wc.addStopWord("an");
            wc.addStopWord("the");
        }

        wc.load("src/test/data/" + testName + ".txt");

        // Load ground truth
        Map<String, Integer> groundTruth = new HashMap<>();
        int expectedTotalWords = 0;
        try (Scanner s = new Scanner(new File("src/test/ground_truth/" + testName + "_ground_truth.txt"))) {
            String line = s.nextLine();
            expectedTotalWords = Integer.parseInt(line.split(": ")[1]);
            while (s.hasNextLine()) {
                line = s.nextLine();
                String[] parts = line.split(": ");
                groundTruth.put(parts[0], Integer.parseInt(parts[1]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ground truth file not found for " + testName);
            return;
        }

        // Compare results
        int actualTotalWords = wc.getNumTotalWords();
        System.out.println("Total Words Check: " + (actualTotalWords == expectedTotalWords ? "Correct" : "Incorrect") +
                           " (Expected: " + expectedTotalWords + ", Got: " + actualTotalWords + ")");

        // --- NEW, MORE FLEXIBLE TESTING LOGIC ---

        // 1. Compare distinct word counts
        int actualDistinctWords = wc.getNumDistinctWords();
        int expectedDistinctWords = groundTruth.size();
        System.out.println("Distinct Words Comparison:");
        if (actualDistinctWords > expectedDistinctWords) {
            System.out.println("  -> Found " + (actualDistinctWords - expectedDistinctWords) + " more unique words than ground truth (implies a less refined stop word list or different processing).");
        } else if (actualDistinctWords < expectedDistinctWords) {
            System.out.println("  -> Found " + (expectedDistinctWords - actualDistinctWords) + " fewer unique words than ground truth (implies an over-aggressive stop word list or an error).");
        } else {
            System.out.println("  -> Correct number of distinct words found.");
        }

        // 2. Find important terms from ground truth that are missing in our implementation
        java.util.List<WordFrequency> missingTerms = new java.util.ArrayList<>();
        for (Map.Entry<String, Integer> entry : groundTruth.entrySet()) {
            if (wc.getFrequency(entry.getKey()) == 0) {
                missingTerms.add(new WordFrequency(entry.getKey(), entry.getValue()));
            }
        }

        System.out.println("Missing Terms Check:");
        if (missingTerms.isEmpty()) {
            System.out.println("  -> All ground truth terms were found by the implementation.");
        } else {
            System.out.println("  -> " + missingTerms.size() + " terms from ground truth are missing.");
            // Sort missing terms by frequency to find the most important ones
            missingTerms.sort((a, b) -> Integer.compare(b.getFrequency(), a.getFrequency()));
            System.out.println("  -> Top 10 most frequent missing terms:");
            for (int i = 0; i < 10 && i < missingTerms.size(); i++) {
                WordFrequency term = missingTerms.get(i);
                System.out.println("      - '" + term.key() + "' (Expected Frequency: " + term.getFrequency() + ")");
            }
        }
        // --- END NEW LOGIC ---

        // --- Print Top 20 Words from current implementation ---
        System.out.println("\n  Top 20 Most Frequent Words (from this implementation):");
        printTopNWords(wc, 20);
        // --- END ---

        System.out.println();
    }

    private static void printTopNWords(WordCounter wc, int n) {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        wc.printTreeByFrequency(new java.io.PrintStream(baos));
        String fullOutput = baos.toString();

        String[] lines = fullOutput.split("\\r?\\n");
        for (int i = 0; i < n && i < lines.length; i++) {
            System.out.println("    " + (i + 1) + ". " + lines[i]);
        }
    }
}
