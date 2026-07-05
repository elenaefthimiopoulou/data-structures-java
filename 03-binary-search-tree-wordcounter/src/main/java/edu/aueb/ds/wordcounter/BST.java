package edu.aueb.ds.wordcounter;

import edu.aueb.ds.datastructures.List;
import edu.aueb.ds.datastructures.ListImpl;
import edu.aueb.ds.datastructures.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * An implementation of the {@link WordCounter} interface using a Binary Search Tree (BST).
 * <p>
 * Every node holds a {@link WordFrequency} (the word and how many times it appeared) and the
 * size of the subtree that starts at the node, which makes {@link #getNumDistinctWords()} an
 * O(1) operation. The stop words are kept in a {@link List}.
 */
public class BST implements WordCounter {

    /**
     * Private inner class representing a node in the BST.
     */
    private class TreeNode {
        /** The word and its frequency; the word is the key of the node. */
        WordFrequency item;
        /** Pointer to the left subtree (smaller words). */
        TreeNode left;
        /** Pointer to the right subtree (greater words). */
        TreeNode right;
        /** Number of nodes in the subtree starting at this node. */
        int subtreeSize;

        /**
         * Creates a leaf node.
         *
         * @param item the word and its frequency
         */
        public TreeNode(WordFrequency item) {
            this.item = item;
            this.subtreeSize = 1;
        }
    }

    /** Root of the tree. */
    private TreeNode head;
    /** The stop words; they are ignored by {@link #insert(String)}. */
    private List<String> stopWords;
    /** Cache for the total number of words (including duplicates). */
    private int totalWords;

    /**
     * Default constructor. Initializes an empty BST and stop word list.
     */
    public BST() {
        this.stopWords = new ListImpl<>();
        this.totalWords = 0;
    }

    // ------------------------------------------------------------------ insert

    /**
     * {@inheritDoc}
     * @aueb.ds.complexity O(S + log U) on average, where S is the number of stop words and U is the number of unique words.
     *             Worst case is O(S + U) if the tree is unbalanced.
     */
    @Override
    public void insert(String w) {
        if (w == null) {
            return;
        }
        w = w.toLowerCase();
        if (isStopWord(w)) {
            return;
        }
        head = insert(head, w);
    }

    /**
     * Recursive helper to insert a word into the tree.
     *
     * @param node The current node in the recursion.
     * @param w The word to insert.
     * @return The root of the modified subtree.
     */
    private TreeNode insert(TreeNode node, String w) {
        if (node == null) {
            totalWords++;
            return new TreeNode(new WordFrequency(w));
        }
        int cmp = w.compareTo(node.item.key());
        if (cmp < 0) {
            node.left = insert(node.left, w);
        } else if (cmp > 0) {
            node.right = insert(node.right, w);
        } else {
            node.item.incrementFrequency();
            totalWords++;
        }
        node.subtreeSize = 1 + size(node.left) + size(node.right);
        return node;
    }

    /**
     * Returns the size of a subtree.
     *
     * @param node The root of the subtree (may be null).
     * @return The number of nodes in the subtree.
     */
    private int size(TreeNode node) {
        return node == null ? 0 : node.subtreeSize;
    }

    /**
     * Checks whether a word is in the list of stop words.
     *
     * @param w the (lowercase) word
     * @return true if the word is a stop word
     */
    private boolean isStopWord(String w) {
        for (Node<String> n = stopWords.getHead(); n != null; n = n.getNext()) {
            if (n.getData().equals(w)) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------ search

    /**
     * {@inheritDoc}
     * <p>
     * If the word is found and its frequency is greater than the mean frequency, its node is
     * moved to the root of the tree with rotations.
     * @aueb.ds.complexity O(log U) on average, O(U) in the worst case for an unbalanced tree.
     */
    @Override
    public WordFrequency search(String w) {
        if (w == null) {
            return null;
        }
        w = w.toLowerCase();
        WordFrequency found = find(head, w);
        if (found != null && found.getFrequency() > getMeanFrequency()) {
            head = moveToRoot(head, w);
        }
        return found;
    }

    /**
     * Finds a word without changing the tree.
     *
     * @param node The current node in the recursion.
     * @param w The word to search for.
     * @return The WordFrequency object if found, otherwise null.
     */
    private WordFrequency find(TreeNode node, String w) {
        while (node != null) {
            int cmp = w.compareTo(node.item.key());
            if (cmp == 0) {
                return node.item;
            }
            node = cmp < 0 ? node.left : node.right;
        }
        return null;
    }

    /**
     * Moves the node with the given word to the root of the subtree, using rotations.
     * The word must exist in the subtree.
     *
     * @param node the root of the subtree
     * @param w the word to bring to the root
     * @return the new root of the subtree
     */
    private TreeNode moveToRoot(TreeNode node, String w) {
        int cmp = w.compareTo(node.item.key());
        if (cmp < 0) {
            node.left = moveToRoot(node.left, w);
            node = rotateRight(node);
        } else if (cmp > 0) {
            node.right = moveToRoot(node.right, w);
            node = rotateLeft(node);
        }
        return node;
    }

    /**
     * Right rotation: the left child of {@code h} becomes the root of the subtree.
     *
     * @param h the root of the subtree (must have a left child)
     * @return the new root of the subtree
     */
    private TreeNode rotateRight(TreeNode h) {
        TreeNode x = h.left;
        h.left = x.right;
        x.right = h;
        h.subtreeSize = 1 + size(h.left) + size(h.right);
        x.subtreeSize = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Left rotation: the right child of {@code h} becomes the root of the subtree.
     *
     * @param h the root of the subtree (must have a right child)
     * @return the new root of the subtree
     */
    private TreeNode rotateLeft(TreeNode h) {
        TreeNode x = h.right;
        h.right = x.left;
        x.left = h;
        h.subtreeSize = 1 + size(h.left) + size(h.right);
        x.subtreeSize = 1 + size(x.left) + size(x.right);
        return x;
    }

    // ------------------------------------------------------------------ remove

    /**
     * {@inheritDoc}
     * @aueb.ds.complexity O(log U) on average, O(U) in the worst case for an unbalanced tree.
     */
    @Override
    public void remove(String w) {
        if (w == null) {
            return;
        }
        w = w.toLowerCase();
        WordFrequency wf = find(head, w);
        if (wf == null) {
            return;
        }
        if (wf.getFrequency() > 1) {
            wf.decrementFrequency();
        } else {
            head = remove(head, w);
        }
        totalWords--;
    }

    /**
     * Recursive helper to remove a node from the tree using Hibbard deletion.
     *
     * @param node The current node in the recursion.
     * @param w The word to remove (it must exist in the subtree).
     * @return The root of the modified subtree.
     */
    private TreeNode remove(TreeNode node, String w) {
        if (node == null) {
            return null;
        }
        int cmp = w.compareTo(node.item.key());
        if (cmp < 0) {
            node.left = remove(node.left, w);
        } else if (cmp > 0) {
            node.right = remove(node.right, w);
        } else {
            if (node.right == null) {
                return node.left;
            }
            if (node.left == null) {
                return node.right;
            }
            // two children: replace the node by its successor (the minimum of the right subtree)
            TreeNode t = node;
            node = min(t.right);
            node.right = deleteMin(t.right);
            node.left = t.left;
        }
        node.subtreeSize = 1 + size(node.left) + size(node.right);
        return node;
    }

    /**
     * Finds the node with the smallest key of a subtree.
     *
     * @param node the root of a non-empty subtree
     * @return the node with the smallest key
     */
    private TreeNode min(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Removes the node with the smallest key of a subtree.
     *
     * @param node the root of a non-empty subtree
     * @return the root of the modified subtree
     */
    private TreeNode deleteMin(TreeNode node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        node.subtreeSize = 1 + size(node.left) + size(node.right);
        return node;
    }

    // ------------------------------------------------------------------ load

    /**
     * {@inheritDoc}
     * <p>
     * Processing rules: lowercase; {@code /} acts as a space; the ending {@code 's} is removed;
     * only the letters a-z and {@code -} are kept; words with fewer than 2 characters and stop
     * words are ignored.
     * @aueb.ds.complexity O(N * S + U log U) on average, where N is the number of words in the file.
     */
    @Override
    public void load(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.toLowerCase().replace('/', ' ').split("\\s+");
                for (String token : tokens) {
                    String word = cleanWord(token);
                    if (word.length() > 1) {
                        insert(word);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error: Could not read file '" + filename + "'. " + e.getMessage());
        }
    }

    /**
     * Applies the word processing rules to a lowercase token.
     *
     * @param token a lowercase string without whitespace
     * @return the cleaned word (possibly empty)
     */
    private String cleanWord(String token) {
        // remove a possessive 's (straight or curly apostrophe) at the end of the word,
        // ignoring any punctuation that follows it, e.g. "student's," -> "student"
        token = token.replaceAll("['\u2019]s(?=[^a-z'\u2019-]*$)", "");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < token.length(); i++) {
            char c = token.charAt(i);
            if ((c >= 'a' && c <= 'z') || c == '-') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // ------------------------------------------------------------------ statistics

    /**
     * {@inheritDoc}
     * @aueb.ds.complexity O(1)
     */
    @Override
    public int getNumTotalWords() {
        return totalWords;
    }

    /**
     * {@inheritDoc}
     * @aueb.ds.complexity O(1)
     */
    @Override
    public int getNumDistinctWords() {
        return size(head);
    }

    /**
     * {@inheritDoc}
     * @aueb.ds.complexity O(log U) on average, O(U) in the worst case.
     */
    @Override
    public int getFrequency(String w) {
        if (w == null) {
            return 0;
        }
        WordFrequency wf = find(head, w.toLowerCase());
        return wf == null ? 0 : wf.getFrequency();
    }

    /**
     * {@inheritDoc}
     * @aueb.ds.complexity O(U)
     */
    @Override
    public WordFrequency getMaxFrequency() {
        return findMax(head, null);
    }

    /**
     * Recursive helper that finds the item with the highest frequency.
     * In case of a tie the alphabetically first word is returned.
     *
     * @param node the root of the subtree
     * @param best the best item found so far (may be null)
     * @return the best item of the subtree combined with {@code best}
     */
    private WordFrequency findMax(TreeNode node, WordFrequency best) {
        if (node == null) {
            return best;
        }
        best = findMax(node.left, best);
        if (best == null || node.item.getFrequency() > best.getFrequency()) {
            best = node.item;
        }
        return findMax(node.right, best);
    }

    /**
     * {@inheritDoc}
     * @aueb.ds.complexity O(1)
     */
    @Override
    public double getMeanFrequency() {
        int distinct = getNumDistinctWords();
        return distinct == 0 ? 0.0 : (double) totalWords / distinct;
    }

    // ------------------------------------------------------------------ stop words

    /**
     * {@inheritDoc}
     * @aueb.ds.complexity O(1)
     */
    @Override
    public void addStopWord(String w) {
        if (w != null) {
            stopWords.insertAtBack(w.toLowerCase());
        }
    }

    /**
     * {@inheritDoc}
     * All occurrences of the word are removed from the list.
     * @aueb.ds.complexity O(S) where S is the number of stop words.
     */
    @Override
    public void removeStopWord(String w) {
        if (w == null) {
            return;
        }
        w = w.toLowerCase();
        List<String> remaining = new ListImpl<>();
        for (Node<String> n = stopWords.getHead(); n != null; n = n.getNext()) {
            if (!n.getData().equals(w)) {
                remaining.insertAtBack(n.getData());
            }
        }
        stopWords = remaining;
    }

    // ------------------------------------------------------------------ printing

    /**
     * {@inheritDoc}
     * Words with the same frequency are printed alphabetically.
     * @aueb.ds.complexity O(U log U)
     */
    @Override
    public void printTreeByFrequency(PrintStream stream) {
        WordFrequency[] items = new WordFrequency[getNumDistinctWords()];
        collect(head, items, new int[1]);
        mergeSort(items, new WordFrequency[items.length], 0, items.length - 1);
        for (WordFrequency wf : items) {
            stream.println(format(wf));
        }
    }

    /**
     * Stores the items of a subtree in the array, in alphabetical order (in-order traversal).
     *
     * @param node the root of the subtree
     * @param items the destination array
     * @param next a one-element array holding the next free position
     */
    private void collect(TreeNode node, WordFrequency[] items, int[] next) {
        if (node == null) {
            return;
        }
        collect(node.left, items, next);
        items[next[0]++] = node.item;
        collect(node.right, items, next);
    }

    /**
     * Mergesort of the items by descending frequency, then alphabetically.
     *
     * @param a the array to sort
     * @param tmp a helper array of the same length
     * @param lo the first index of the range
     * @param hi the last index of the range
     */
    private void mergeSort(WordFrequency[] a, WordFrequency[] tmp, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = (lo + hi) >>> 1;
        mergeSort(a, tmp, lo, mid);
        mergeSort(a, tmp, mid + 1, hi);

        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            tmp[k++] = comesFirst(a[j], a[i]) ? a[j++] : a[i++];
        }
        while (i <= mid) {
            tmp[k++] = a[i++];
        }
        while (j <= hi) {
            tmp[k++] = a[j++];
        }
        for (k = lo; k <= hi; k++) {
            a[k] = tmp[k];
        }
    }

    /**
     * Ordering used by {@link #printTreeByFrequency(PrintStream)}.
     *
     * @param x the first item
     * @param y the second item
     * @return true if {@code x} must be printed strictly before {@code y}
     */
    private boolean comesFirst(WordFrequency x, WordFrequency y) {
        if (x.getFrequency() != y.getFrequency()) {
            return x.getFrequency() > y.getFrequency();
        }
        return x.key().compareTo(y.key()) < 0;
    }

    /**
     * {@inheritDoc}
     * @aueb.ds.complexity O(U)
     */
    @Override
    public void printTreeByWord(PrintStream stream) {
        printInOrder(head, stream);
    }

    /**
     * In-order traversal that prints every item.
     *
     * @param node the root of the subtree
     * @param stream the output stream
     */
    private void printInOrder(TreeNode node, PrintStream stream) {
        if (node == null) {
            return;
        }
        printInOrder(node.left, stream);
        stream.println(format(node.item));
        printInOrder(node.right, stream);
    }

    /**
     * Formats an item as {@code word: frequency}.
     *
     * @param wf the item
     * @return the formatted line
     */
    private String format(WordFrequency wf) {
        return wf.key() + ": " + wf.getFrequency();
    }
}
