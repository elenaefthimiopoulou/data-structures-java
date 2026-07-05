package edu.aueb.ds.netprofit;

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
 * A test runner for the NetProfit program.
 * This class runs NetProfit as a separate process for each test case
 * in the 'transactions_test_cases' directory and compares the output to the expected
 * solutions defined in 'solutions.txt'.
 */
public class TestNetProfit {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TestNetProfit() {
    }

    private static final String SOLUTIONS_FILE = "solutions.txt";
    private static final String CLASS_TO_TEST = "edu.aueb.ds.netprofit.NetProfit";
    private static final String PROJECT_DIRECTORY = "."; // Assumes runner is executed from the project root

    /**
     * The main entry point for the test runner. It locates test files,
     * loads the expected solutions, executes the NetProfit program for each test,
     * and prints a summary of the results.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("--- Running NetProfit Calculator Tests ---\\n");

        File testsDir = new File("src/test/resources/net_profit_test_cases");
        if (!testsDir.exists() || !testsDir.isDirectory()) {
            System.out.println("ERROR: 'net_profit_test_cases' directory not found.");
            return;
        }

        try {
            Map<String, String> solutions = loadSolutions(new File(testsDir, SOLUTIONS_FILE));
            List<File> testFiles = findTestFiles(testsDir);

            int passed = 0;
            int failed = 0;

            for (File testFile : testFiles) {
                String testId = testFile.getName().replace(".txt", "");
                String expectedOutput = solutions.get(testId);

                if (expectedOutput == null) {
                    System.out.println("\u001B[33m[SKIP]\u001B[0m " + testId + ": No solution found in " + SOLUTIONS_FILE);
                    continue;
                }

                ProcessBuilder pb = new ProcessBuilder("java", "-cp", "out/production", CLASS_TO_TEST, testFile.getPath());
                Process process = pb.start();

                StringBuilder outputBuilder = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        outputBuilder.append(line);
                    }
                }

                boolean finished = process.waitFor(15, TimeUnit.SECONDS);
                if (!finished) {
                    process.destroy();
                    System.out.println("\u001B[31m[FAIL]\u001B[0m " + testId + " (Timeout)");
                    failed++;
                    continue;
                }

                String actualOutput = outputBuilder.toString().trim();

                if (outputsMatch(actualOutput, expectedOutput)) {
                    System.out.println("\u001B[32m[PASS]\u001B[0m " + testId);
                    passed++;
                } else {
                    System.out.println("\u001B[31m[FAIL]\u001B[0m " + testId);
                    System.out.println("       - Expected: \"" + expectedOutput + "\"");
                    System.out.println("       - Actual:   \"" + actualOutput + "\"");
                    failed++;
                }
            }
            
            // Manually test TC-10 for non-existent file
            testNonExistentFile(solutions, passed, failed);


        } catch (IOException | InterruptedException e) {
            System.out.println("An error occurred while running the tests: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Specifically tests the case where the input file does not exist (TC-10).
     * It runs the NetProfit program with a non-existent file path and checks if the
     * output contains the expected error message.
     *
     * @param solutions The map of loaded solutions.
     * @param passed    The current count of passed tests.
     * @param failed    The current count of failed tests.
     * @throws IOException          If an I/O error occurs.
     * @throws InterruptedException If the process is interrupted.
     */
    private static void testNonExistentFile(Map<String, String> solutions, int passed, int failed) throws IOException, InterruptedException {
        String testId = "TC-10_input";
        String expectedOutput = solutions.get(testId);
        if (expectedOutput == null) {
            System.out.println("\u001B[33m[SKIP]\u001B[0m " + testId + ": No solution found.");
        } else {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", "out/production", CLASS_TO_TEST, "non_existent_file.txt");
            Process process = pb.start();
            StringBuilder outputBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    outputBuilder.append(line);
                }
            }
            process.waitFor();
            String actualOutput = outputBuilder.toString().trim();
            
            // We check if the actual output contains the essence of the expected error.
            if (actualOutput.contains("Could not read file")) {
                 System.out.println("\u001B[32m[PASS]\u001B[0m " + testId);
                 passed++;
            } else {
                System.out.println("\u001B[31m[FAIL]\u001B[0m " + testId);
                System.out.println("       - Expected contains: \"Could not read file\"");
                System.out.println("       - Actual:   \"" + actualOutput + "\"");
                failed++;
            }
        }

        System.out.println("\n--- Test Summary ---");
        System.out.println("Total Tests: " + (passed + failed));
        System.out.println("\u001B[32mPassed: " + passed + "\u001B[0m");
        System.out.println("\u001B[31mFailed: " + failed + "\u001B[0m");
    }


    /**
     * Compares the actual output of the program with the expected output.
     * For numerical profit values, it allows for a small floating-point tolerance.
     * For error messages, it requires an exact match.
     *
     * @param actual   The actual output from the NetProfit program.
     * @param expected The expected output from the solutions file.
     * @return true if the outputs match, false otherwise.
     */
    private static boolean outputsMatch(String actual, String expected) {
        if (actual.startsWith("Error:") || expected.startsWith("Error:")) {
            return actual.equals(expected);
        }
        try {
            String actualValueStr = actual.substring(actual.indexOf(":") + 1).trim();
            String expectedValueStr = expected.substring(expected.indexOf(":") + 1).trim();
            double actualValue = Double.parseDouble(actualValueStr);
            double expectedValue = Double.parseDouble(expectedValueStr);
            return Math.abs(actualValue - expectedValue) < 0.001;
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return false; // If parsing fails, they don't match
        }
    }

    /**
     * Loads the test solutions from the specified solutions file.
     * The file is expected to have one solution per line in the format "TestID: Solution".
     *
     * @param solutionsFile The file containing the test solutions.
     * @return A map where keys are test IDs and values are the expected outputs.
     * @throws IOException If an error occurs while reading the file.
     */
    private static Map<String, String> loadSolutions(File solutionsFile) throws IOException {
        Map<String, String> solutions = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(solutionsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                int colonIndex = line.indexOf(":");
                if (colonIndex != -1) {
                    String testId = line.substring(0, colonIndex).trim();
                    String solution = line.substring(colonIndex + 1).trim();
                    solutions.put(testId, solution);
                }
            }
        }
        return solutions;
    }

    /**
     * Finds all test case files (ending in .txt) in the specified directory.
     *
     * @param testsDir The directory to search for test files.
     * @return A sorted list of test case files.
     */
    private static List<File> findTestFiles(File testsDir) {
        File[] files = testsDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt") && name.startsWith("TC-"));
        List<File> fileList = files != null ? new ArrayList<>(Arrays.asList(files)) : new ArrayList<>();
        fileList.sort(Comparator.comparing(File::getName));
        return fileList;
    }
}