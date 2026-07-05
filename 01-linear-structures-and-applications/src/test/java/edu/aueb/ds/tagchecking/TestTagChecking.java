package edu.aueb.ds.tagchecking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A test runner for the TagChecking program.
 * This class runs TagChecking as a separate process for each test case
 * in the 'html_tests' directory and compares the output to the expected
 * solutions defined in 'solutions.txt'.
 */
public class TestTagChecking {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TestTagChecking() {
    }

    private static final String SOLUTIONS_FILE = "solutions.txt";
    private static final String CLASS_TO_TEST = "edu.aueb.ds.tagchecking.TagChecking";
    private static final String PROJECT_DIRECTORY = "."; // Assumes runner is executed from the project root

    /**
     * The main entry point for the test runner. It locates test files,
     * loads the expected solutions, executes the TagChecking program for each test,
     * and prints a summary of the results.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("--- Running HTML Tag Checker Tests ---\\n");

        File testsDir = new File("src/test/resources/tag_checker_test_cases");
        if (!testsDir.exists() || !testsDir.isDirectory()) {
            System.out.println("ERROR: 'tag_checker_test_cases' directory not found.");
            return;
        }

        try {
            Map<String, String> solutions = loadSolutions(new File(testsDir, SOLUTIONS_FILE));
            List<File> testFiles = findTestFiles(testsDir);

            int passed = 0;
            int failed = 0;

            for (File testFile : testFiles) {
                String testId = testFile.getName().replace(".html", "");
                String expectedOutput = solutions.get(testId);

                if (expectedOutput == null) {
                    System.out.println("\u001B[33m[SKIP]\u001B[0m " + testId + ": No solution found in " + SOLUTIONS_FILE);
                    continue;
                }

                // Run the TagChecking class as a separate process
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", "out/production", CLASS_TO_TEST, testFile.getPath());
                Process process = pb.start();

                // Capture the output
                StringBuilder outputBuilder = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        outputBuilder.append(line).append("\n");
                    }
                }

                // Wait for the process to finish
                boolean finished = process.waitFor(10, TimeUnit.SECONDS); // 10-second timeout per test
                if (!finished) {
                    process.destroy();
                    System.out.println("\u001B[31m[FAIL]\u001B[0m " + testId + " (Timeout)");
                    failed++;
                    continue;
                }

                String actualOutput = outputBuilder.toString().trim().replaceAll("\\r\\n", "\n");

                if (actualOutput.equals(expectedOutput)) {
                    System.out.println("\u001B[32m[PASS]\u001B[0m " + testId);
                    passed++;
                } else {
                    System.out.println("\u001B[31m[FAIL]\u001B[0m " + testId);
                    System.out.println("       - Expected: \"" + expectedOutput.replaceAll("\n", "\n                 ") + "\"");
                    System.out.println("       - Actual:   \"" + actualOutput.replaceAll("\n", "\n                 ") + "\"");
                    failed++;
                }
            }

            System.out.println("\n--- Test Summary ---");
            System.out.println("Total Tests: " + (passed + failed));
            System.out.println("\u001B[32mPassed: " + passed + "\u001B[0m");
            System.out.println("\u001B[31mFailed: " + failed + "\u001B[0m");

        } catch (IOException | InterruptedException e) {
            System.out.println("An error occurred while running the tests: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Map<String, String> loadSolutions(File solutionsFile) throws IOException {
        Map<String, String> solutions = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(solutionsFile))) {
            String line;
            String currentTestId = null;
            StringBuilder currentSolution = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#") || line.startsWith("---")) {
                    continue;
                }

                if (line.matches("^[A-Z]{2}-\\d{2}:.*")) {
                    if (currentTestId != null) {
                        solutions.put(currentTestId, currentSolution.toString().trim());
                    }
                    int colonIndex = line.indexOf(":");
                    currentTestId = line.substring(0, colonIndex).trim();
                    currentSolution = new StringBuilder(line.substring(colonIndex + 1).trim());
                } else {
                    if (currentSolution.length() > 0) {
                        currentSolution.append("\n").append(line.trim());
                    }
                }
            }
            if (currentTestId != null) solutions.put(currentTestId, currentSolution.toString().trim());
        }
        return solutions;
    }

    private static List<File> findTestFiles(File testsDir) {
        File[] files = testsDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".html"));
        List<File> fileList = files != null ? new ArrayList<>(Arrays.asList(files)) : new ArrayList<>();
        fileList.sort(Comparator.comparing(File::getName));
        return fileList;
    }
}