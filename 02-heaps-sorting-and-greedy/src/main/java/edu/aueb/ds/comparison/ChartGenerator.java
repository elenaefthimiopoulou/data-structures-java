package edu.aueb.ds.comparison;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates an HTML file containing a chart to visualize the algorithm comparison results.
 * It reads an HTML template and injects the data using Chart.js.
 */
public class ChartGenerator {

    /**
     * Private constructor to prevent instantiation.
     */
    private ChartGenerator() {}

    private static final String TEMPLATE_PATH = "artifacts/resources/chart_template.html";

    /**
     * Generates and saves an HTML file with a chart comparing the performance of the two algorithms.
     *
     * @param results A list of Result objects containing the data points for the chart.
     * @param filePath The path where the final HTML file will be saved.
     */
    public static void generateChart(List<Result> results, String filePath) {
        // Prepare data for JavaScript injection
        String greedyDataString = results.stream()
                .map(r -> String.format("{x: %d, y: %.1f}", r.n, r.avgGreedy))
                .collect(Collectors.joining(", "));

        String greedyDecDataString = results.stream()
                .filter(r -> r.avgGreedyDecreasing >= 0)
                .map(r -> String.format("{x: %d, y: %.1f}", r.n, r.avgGreedyDecreasing))
                .collect(Collectors.joining(", "));

        try {
            String htmlContent = getHtmlTemplate(greedyDataString, greedyDecDataString);
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
                writer.println(htmlContent);
            }
        } catch (IOException e) {
            System.err.println("Error generating chart HTML file: " + e.getMessage());
        }
    }

    /**
     * Reads the HTML template from a file and injects the chart data.
     *
     * @param greedyData The data points for the Greedy algorithm.
     * @param greedyDecData The data points for the Greedy-decreasing algorithm.
     * @return A string containing the complete, data-filled HTML for the chart.
     * @throws IOException If the template file cannot be read.
     */
    private static String getHtmlTemplate(String greedyData, String greedyDecData) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEMPLATE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }
        String template = contentBuilder.toString();

        // Replace placeholders with actual data
        template = template.replace("{{GREEDY_DATA}}", greedyData);
        template = template.replace("{{GREEDY_DEC_DATA}}", greedyDecData);

        return template;
    }
}