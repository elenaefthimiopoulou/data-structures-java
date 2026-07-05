package edu.aueb.ds.comparison;

import edu.aueb.ds.diskmanager.Disk;
import edu.aueb.ds.datastructures.List;
import edu.aueb.ds.greedy.Greedy;
import edu.aueb.ds.greedy.GreedyDecreasing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Experimental comparison of the "Greedy" and "Greedy-Decreasing" algorithms.
 * <p>
 * The program
 * <ol>
 *   <li>generates the random data files with {@link DataGenerator},</li>
 *   <li>runs both algorithms on every file (several trials for each number of folders N)
 *       and counts the disks they use,</li>
 *   <li>computes the average number of disks per N,</li>
 *   <li>saves the averages in {@code artifacts/results.csv}, and</li>
 *   <li>creates the chart with {@link ChartGenerator}.</li>
 * </ol>
 * It must be run from the root folder of the project.
 */
public class ComparisonRunner {

    /** The numbers of folders N to test (the same values as the ones used by {@link DataGenerator}). */
    private static final int[] FOLDER_COUNTS = {0, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};
    /** The number of random files per N (the same value as the one used by {@link DataGenerator}). */
    private static final int NUM_TRIALS = 10;
    /** The directory where the generated data files are stored. */
    private static final String DATA_DIRECTORY = "artifacts/comparison_data/";
    /** The output CSV file. */
    private static final String RESULTS_FILE = "artifacts/results.csv";
    /** The output chart file. */
    private static final String CHART_FILE = "artifacts/charts/comparison_chart.html";

    /**
     * Private constructor to prevent instantiation.
     */
    private ComparisonRunner() {}

    /**
     * Entry point of the experiment.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // 1. Generate the data files.
        DataGenerator.main(new String[0]);

        // 2. + 3. Run the algorithms on every file and compute the averages.
        ArrayList<Result> results = new ArrayList<>();
        for (int n : FOLDER_COUNTS) {
            long totalGreedy = 0;
            long totalGreedyDecreasing = 0;

            for (int trial = 1; trial <= NUM_TRIALS; trial++) {
                String path = DATA_DIRECTORY + "folders_N" + n + "_trial" + trial + ".txt";
                try {
                    // Greedy-Decreasing works on a copy, so both algorithms get the same input.
                    List<Integer> folders = Greedy.readFolderSizes(path);

                    List<Disk> greedyDisks = Greedy.runGreedyAlgorithm(folders);
                    List<Disk> decreasingDisks = GreedyDecreasing.runGreedyAlgorithm(folders);

                    totalGreedy += greedyDisks.size();
                    totalGreedyDecreasing += decreasingDisks.size();
                } catch (FileNotFoundException e) {
                    System.err.println("Could not find data file: " + path);
                    return;
                }
            }

            double avgGreedy = (double) totalGreedy / NUM_TRIALS;
            double avgGreedyDecreasing = (double) totalGreedyDecreasing / NUM_TRIALS;
            results.add(new Result(n, avgGreedy, avgGreedyDecreasing));
            System.out.printf("N = %5d   Greedy: %9.1f disks   Greedy-Decreasing: %9.1f disks%n",
                    n, avgGreedy, avgGreedyDecreasing);
        }

        // 4. Save the results in a CSV file.
        saveResults(results, RESULTS_FILE);

        // 5. Create the chart.
        new File(CHART_FILE).getParentFile().mkdirs();
        ChartGenerator.generateChart(results, CHART_FILE);

        System.out.println("Results saved in " + RESULTS_FILE);
        System.out.println("Chart saved in " + CHART_FILE);
    }

    /**
     * Writes the results to a CSV file with the columns {@code N,AvgGreedy,AvgGreedyDecreasing}.
     *
     * @param results  the results to save
     * @param filePath the path of the CSV file
     */
    private static void saveResults(java.util.List<Result> results, String filePath) {
        new File(filePath).getAbsoluteFile().getParentFile().mkdirs();
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            writer.println("N,AvgGreedy,AvgGreedyDecreasing");
            for (Result r : results) {
                writer.println(r.n + "," + String.format(java.util.Locale.ROOT, "%.1f", r.avgGreedy)
                        + "," + String.format(java.util.Locale.ROOT, "%.1f", r.avgGreedyDecreasing));
            }
        } catch (IOException e) {
            System.err.println("Error saving results: " + e.getMessage());
        }
    }
}
