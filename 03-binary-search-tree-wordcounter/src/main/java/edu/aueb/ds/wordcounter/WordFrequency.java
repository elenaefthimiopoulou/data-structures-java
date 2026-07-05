package edu.aueb.ds.wordcounter;

/**
 * Represents a unique word and its corresponding frequency count.
 * This class is used as the data item within the BST nodes.
 */
public class WordFrequency {
    private String word;
    private int frequency;

    /**
     * Constructs a new WordFrequency object with an initial frequency of 1.
     * The word is automatically converted to lowercase.
     *
     * @param word The word to be stored.
     */
    public WordFrequency(String word) {
        this.word = word.toLowerCase();
        this.frequency = 1;
    }

    /**
     * Constructs a new WordFrequency object with a specified frequency.
     * This constructor is primarily used for testing purposes.
     *
     * @param word The word to be stored.
     * @param frequency The initial frequency of the word.
     */
    public WordFrequency(String word, int frequency) {
        this.word = word.toLowerCase();
        this.frequency = frequency;
    }

    /**
     * Returns the word, which serves as the key in the BST.
     *
     * @return The word as a lowercase string.
     */
    public String key() {
        return word;
    }

    /**
     * Returns the current frequency count of the word.
     *
     * @return The frequency count.
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Increments the frequency count of the word by one.
     */
    public void incrementFrequency() {
        this.frequency++;
    }

    /**
     * Decrements the frequency count of the word by one, if it is greater than 0.
     */
    public void decrementFrequency() {
        if (this.frequency > 0) {
            this.frequency--;
        }
    }

    /**
     * Returns a string representation of the WordFrequency object.
     *
     * @return A string in the format "WordFrequency{word='...', frequency=...}".
     */
    @Override
    public String toString() {
        return "WordFrequency{"
                + "word='" + word + "'" + ", frequency=" + frequency +
                '}';
    }
}
