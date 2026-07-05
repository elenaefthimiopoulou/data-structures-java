package edu.aueb.ds.tagchecking;

import edu.aueb.ds.datastructures.Stack;
import edu.aueb.ds.datastructures.StackImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Checks that the tags of an HTML file are balanced and properly nested.
 * <p>
 * The program scans the file line by line and pushes every opening tag on a
 * {@link Stack}. Every closing tag must match the tag on top of the stack
 * (Last-In, First-Out). Only the <b>first</b> error is reported.
 * Tag names are compared case-insensitively and tags that never have a
 * closing counterpart (such as {@code <br>} or {@code <img>}) are ignored.
 */
public class TagChecking {

    /** Tags that have no closing tag and therefore are ignored. */
    private static final Set<String> SELF_CLOSING_TAGS = new HashSet<>(
            Arrays.asList("br", "img", "hr", "input", "meta", "link")
    );

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TagChecking() {
    }

    /**
     * Entry point of the program.
     *
     * @param args {@code args[0]} must be the path of the HTML file to check
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java TagChecking <html-file-path>");
            return;
        }
        checkTags(args[0]);
    }

    /**
     * Checks the tags of the given HTML file and prints the result
     * (success, or a description of the first error found) to the standard output.
     *
     * @param filePath the path of the HTML file
     */
    public static void checkTags(String filePath) {
        Stack<String> tagStack = new StackImpl<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                int pos = 0;

                while (pos < line.length()) {
                    int open = line.indexOf('<', pos);
                    if (open == -1) {
                        break; // no more tags in this line
                    }
                    int close = line.indexOf('>', open);
                    if (close == -1) {
                        break; // '<' without a matching '>' on this line
                    }
                    pos = close + 1;

                    String content = line.substring(open + 1, close);

                    // Not a tag: empty "<>", comments / doctype "<!...>", or "<" followed by a space
                    if (content.isEmpty() || content.charAt(0) == '!'
                            || Character.isWhitespace(content.charAt(0))) {
                        continue;
                    }

                    if (content.charAt(0) == '/') {
                        // ---- closing tag ----
                        String name = content.substring(1).trim().toLowerCase();

                        if (tagStack.isEmpty()) {
                            System.out.println("Error at line " + lineNumber
                                    + ": Found closing tag </" + name + "> with no matching opening tag.");
                            return;
                        }
                        String expected = tagStack.pop();
                        if (!expected.equals(name)) {
                            System.out.println("Error at line " + lineNumber
                                    + ": Mismatched tag. Expected </" + expected
                                    + ">, but found </" + name + ">.");
                            return;
                        }
                    } else {
                        // ---- opening tag ----
                        String name = content.split("\\s+")[0].toLowerCase();
                        boolean selfClosed = name.endsWith("/") || content.endsWith("/");
                        if (name.endsWith("/")) {
                            name = name.substring(0, name.length() - 1);
                        }
                        if (!selfClosed && !SELF_CLOSING_TAGS.contains(name)) {
                            tagStack.push(name);
                        }
                    }
                }
            }

            if (tagStack.isEmpty()) {
                System.out.println("The HTML file has balanced tags.");
            } else {
                // The stack pops the innermost (most recently opened) tag first
                StringBuilder unclosed = new StringBuilder();
                while (!tagStack.isEmpty()) {
                    if (unclosed.length() > 0) {
                        unclosed.append(", ");
                    }
                    unclosed.append(tagStack.pop());
                }
                // The expected output (see solutions.txt) puts the list on a new line
                System.out.println("Error: Reached end of file, but the following tags were not closed:");
                System.out.println("[" + unclosed + "]");
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
