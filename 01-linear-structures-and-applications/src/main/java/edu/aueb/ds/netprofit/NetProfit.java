package edu.aueb.ds.netprofit;

import edu.aueb.ds.datastructures.Queue;
import edu.aueb.ds.datastructures.QueueImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Computes the total net profit of a series of stock transactions using the
 * FIFO accounting principle: the shares that are sold are always the ones that
 * were bought first.
 * <p>
 * Every {@code buy} transaction is stored as a {@link Lot} at the back of a
 * {@link Queue}. Every {@code sell} transaction consumes lots from the front
 * of the queue until all the shares of the sale are matched.
 * <p>
 * Input file format (one transaction per line):
 * <pre>
 * buy  &lt;shares&gt; price &lt;price&gt;
 * sell &lt;shares&gt; price &lt;price&gt;
 * </pre>
 */
public class NetProfit {

    /**
     * A batch of shares bought at the same time and at the same price.
     */
    private static class Lot {
        /** Number of shares of this batch that have not been sold yet. */
        int shares;
        /** Price paid per share. */
        final double price;

        /**
         * Creates a new batch.
         *
         * @param shares the number of shares bought
         * @param price  the price per share
         */
        Lot(int shares, double price) {
            this.shares = shares;
            this.price = price;
        }
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private NetProfit() {
    }

    /**
     * Entry point of the program.
     *
     * @param args {@code args[0]} must be the path of the input file
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java NetProfit <path_to_text_file>");
            return;
        }

        String filePath = args[0];
        Queue<Lot> lots = new QueueImpl<>();
        int available = 0;          // total shares currently owned
        double totalProfit = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length != 4) {
                    continue; // ignore malformed lines
                }

                String type = parts[0];
                boolean isBuy = type.equals("buy");
                boolean isSell = type.equals("sell");
                if ((!isBuy && !isSell) || !parts[2].equals("price")) {
                    continue; // ignore malformed lines
                }

                int shares = Integer.parseInt(parts[1]);
                double price = Double.parseDouble(parts[3]);

                if (isBuy) {
                    if (shares > 0) {
                        lots.put(new Lot(shares, price));
                        available += shares;
                    }
                } else {
                    if (shares > available) {
                        System.out.println("Error: Attempting to sell " + shares
                                + " shares, but only " + available + " are available.");
                        return;
                    }

                    int remaining = shares;
                    while (remaining > 0) {
                        Lot oldest = lots.peek();
                        int used = Math.min(remaining, oldest.shares);
                        totalProfit += used * (price - oldest.price);
                        oldest.shares -= used;
                        remaining -= used;
                        if (oldest.shares == 0) {
                            lots.get(); // the whole batch has been sold
                        }
                    }
                    available -= shares;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Could not read file '" + filePath + "'. " + e.getMessage());
            return;
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format in the input file.");
            return;
        }

        System.out.println("Total net profit: " + totalProfit);
    }
}
