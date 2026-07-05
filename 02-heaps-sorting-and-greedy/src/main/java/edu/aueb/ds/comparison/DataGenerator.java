package edu.aueb.ds.comparison;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * A program to generate random data files for the algorithm comparison.
 */
public class DataGenerator {

    private static final int NUM_TRIALS = 10; // Number of random files to generate for each folder count
    private static final int[] FOLDER_COUNTS = {0, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000}; // The different N values to test
    private static final String DATA_DIRECTORY = "artifacts/comparison_data/"; // Directory to store generated test files

    /**
     * Private constructor to prevent instantiation.
     */
    private DataGenerator() {}

    /**
     * The main entry point for the data generation program.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Generating data files...");
        new java.io.File(DATA_DIRECTORY).mkdirs(); // Ensure the data directory exists

        for (int n : FOLDER_COUNTS) {
            for (int i = 0; i < NUM_TRIALS; i++) {
                String filePath = DATA_DIRECTORY + "folders_N" + n + "_trial" + (i + 1) + ".txt";
                try {
                    generateRandomDataFile(filePath, n);
                } catch (IOException e) {
                    System.err.println("An error occurred while generating file: " + filePath);
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Data files generated successfully in " + DATA_DIRECTORY);
    }

    /**
     * Generates a text file with a specified number of random folder sizes.
     * Each folder size is an integer between 1 and 1,000,000, inclusive.
     *
     * @param filePath The path of the file to create (including the filename).
     * @param numFolders The number of random folder sizes to generate in the file.
     * @throws IOException If an I/O error occurs when writing to the file.
     */
    private static void generateRandomDataFile(String filePath, int numFolders) throws IOException {
        Random random = new Random();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (int i = 0; i < numFolders; i++) {
                // Generate a random folder size between 1 and 1,000,000 MB
                int size = random.nextInt(1000000) + 1;
                writer.println(size);
            }
        }
    }
}
