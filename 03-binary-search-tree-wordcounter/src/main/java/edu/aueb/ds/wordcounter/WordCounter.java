package edu.aueb.ds.wordcounter;

import java.io.PrintStream;

/**
 * Defines the public interface for a Word Counter.
 * <p>
 * An implementing class is expected to handle loading text, processing words,
 * counting frequencies, and providing various statistics and reports.
 */
public interface WordCounter {

    /**
     * Inserts a word into the counter. If the word already exists, its
     * frequency is incremented. Otherwise, it is added with a frequency of 1.
     *
     * @param w The word to insert.
     */
    void insert(String w);

    /**
     * Searches for a word in the counter.
     *
     * @param w The word to search for.
     * @return The {@link WordFrequency} object for the word, or {@code null} if not found.
     */
    WordFrequency search(String w);

    /**
     * Removes a word from the counter. If the word's frequency is greater than 1,
     * its frequency is decremented. If the frequency is 1, the word is removed entirely.
     *
     * @param w The word to remove.
     */
    void remove(String w);

    /**
     * Loads a text file, processes its content according to specific rules
     * (e.g., case-insensitivity, punctuation removal), and inserts the
     * resulting words into the counter.
     *
     * @param filename The path to the text file.
     */
    void load(String filename);

    /**
     * Returns the total number of words counted, including duplicates.
     *
     * @return The total word count.
     */
    int getNumTotalWords();

    /**
     * Returns the number of unique (distinct) words in the counter.
     *
     * @return The unique word count.
     */
    int getNumDistinctWords();

    /**
     * Returns the frequency of a specific word.
     *
     * @param w The word to get the frequency of.
     * @return The frequency of the word, or 0 if the word is not found.
     */
    int getFrequency(String w);

    /**
     * Finds and returns the word with the highest frequency in the counter.
     *
     * @return The {@link WordFrequency} object for the most frequent word,
     *         or {@code null} if the counter is empty.
     */
    WordFrequency getMaxFrequency();

    /**
     * Calculates and returns the mean frequency of all words in the counter.
     *
     * @return The mean frequency as a double.
     */
    double getMeanFrequency();

    /**
     * Adds a word to the list of stop words. Stop words are ignored during the `load` process.
     *
     * @param w The word to add to the stop word list.
     */
    void addStopWord(String w);

    /**
     * Removes a word from the stop word list.
     *
     * @param w The word to remove from the stop word list.
     */
    void removeStopWord(String w);

    /**
     * Prints all unique words and their frequencies to the given stream,
     * sorted by frequency in descending order. Words with the same frequency
     * are sorted alphabetically.
     *
     * @param stream The {@link PrintStream} to write the output to.
     */
    void printTreeByFrequency(PrintStream stream);

    /**
     * Prints all unique words and their frequencies to the given stream,
     * sorted alphabetically by word.
     *
     * @param stream The {@link PrintStream} to write the output to.
     */
    void printTreeByWord(PrintStream stream);
}
