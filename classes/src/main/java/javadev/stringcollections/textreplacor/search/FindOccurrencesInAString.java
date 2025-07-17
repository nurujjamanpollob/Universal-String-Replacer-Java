package javadev.stringcollections.textreplacor.search;

import javadev.stringcollections.textreplacor.ReplaceStringInFiles;
import javadev.stringcollections.textreplacor.object.Line;
import javadev.stringcollections.textreplacor.object.TextSearchResult;
import javadev.stringcollections.textreplacor.writer.ReplaceStringInAFile;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @apiNote Class that finds occurrences of a string in another string. can be loaded from a file or passed as a parameter.
 * Works only with Text Files (text/plain).
 * <p>
 * @since 1.0
 * @version 1.0
 * @author nurujjamanpollob
 */
public class FindOccurrencesInAString {

    private final String inputString;
    private final String searchString;
    private final File inputFilePath;
    /**
     * -- SETTER --
     * * @param includeTextWhereMatched if true, the text where the search string matched will be included in the result. If false empty string will be added in the {@link javadev.stringcollections.textreplacor.object.Line} object.
     */
    @Setter
    @Getter
    private boolean includeTextWhereMatched = false; // If true, the text where the search string matched will be included in the result.
    /**
     * -- GETTER --
     *
     * @return returns true if this instance is initialized with a file, false otherwise.
     */
    @Getter
    private final boolean isInitilizedWithFile;

    private boolean useLogging = false; // If true, logging will be used to log the search process. Default is false.

    /**

    /**
     * Constructor to initialize the input string and search string.
     * @param inputString the string in which to search
     * @param searchString the string to search for
     */
    public FindOccurrencesInAString(@NotNull String inputString, @NotNull String searchString) {
        this.inputString = inputString;
        this.searchString = searchString;
        this.inputFilePath = null; // No file path provided in this constructor
        this.isInitilizedWithFile = false; // Indicates that this instance is not initialized with a file
    }

    /**
     * Constructor to initialize the input string and search string from a file.
     * @param inputFilePath the path to the file containing the input string
     * @param searchString the string to search for
     */
    public FindOccurrencesInAString(@NotNull File inputFilePath, @NotNull String searchString) {
        // Here you would typically read the file content into inputString
        // For simplicity, we are not implementing file reading in this example.
        this.inputString = null;
        this.searchString = searchString;
        this.inputFilePath = inputFilePath;
        this.isInitilizedWithFile = true; // Indicates that this instance is initialized with a file
    }

    /**
     * Suppress default constructor for noninstantiability
     */
    private FindOccurrencesInAString() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    /**
     * Performs search on a buffer and return a list of {@link javadev.stringcollections.textreplacor.object.Line} object containing the search results(character start, end, line number, etc.). null returned if no occurrences found.
     * @param buffer the string to search within
     * @param searchString the string to search for
     * @param lineNumber the line number in the text. the line number will be incremented by 1 because array index starts from 0!
     * @return a {@link javadev.stringcollections.textreplacor.object.Line} array of found occurrences, or null if no occurrences are found.
     */
    public @Nullable Line[] findAndReturnOccurrence(@NotNull String buffer, @NotNull String searchString, int lineNumber) {
        // run loop and find occurrences of searchString in buffer
        if (searchString.isEmpty()) {

            return null; // Return null if input is invalid
        }

        Line[] occurrences = new Line[buffer.length()]; // Initialize an array to hold occurrences

        int occurrenceCount = 0; // Counter for occurrences found
        int index = buffer.indexOf(searchString); // Find the first occurrence of searchString
        while (index != -1) {

            Line occurrence = includeTextWhereMatched ? new Line(lineNumber, buffer, index, index + searchString.length() - 1): new Line(lineNumber, "", index, index + searchString.length() - 1);

            // log all object by toString() method
            logData(
                    "findAndReturnOccurrence()",
                    "Found occurrence: " + occurrence,
                    ReplaceStringInFiles.LogType.INFO
            );
            // Create a Line object for the found occurrence
            occurrences[occurrenceCount] = occurrence;
            occurrenceCount++; // Increment the count of occurrences

            // Find the next occurrence of searchString in buffer
            index = buffer.indexOf(searchString, index + searchString.length());
        }
        // If no occurrences were found, return null
        if (occurrenceCount == 0) {
            return null;
        }
        // Create a new array with the exact number of occurrences found
        Line[] result = new Line[occurrenceCount];
        System.arraycopy(occurrences, 0, result, 0, occurrenceCount); // Copy the found occurrences to the result array
        return result; // Return the array of found occurrences

    }

    /**
     * This method perform search on the file or string in line by line and return a {@link javadev.stringcollections.textreplacor.object.TextSearchResult} object containing the search results for each line.
     * If this class initialized with file, the file path included, otherwise file path will be empty in the {@link javadev.stringcollections.textreplacor.object.TextSearchResult} object.
     * @throws IOException if an I/O error occurs while reading the file.
     * @return a {@link javadev.stringcollections.textreplacor.object.TextSearchResult} object containing the search results for each line. Null returned if no occurrences found.
     */
    public @Nullable TextSearchResult findOccurrences() throws IOException {

        // create a ArrayList to hold the search results
        java.util.List<Line> searchResults = new ArrayList<>();

        // if this class initialized
        if (isInitilizedWithFile) {
            // log the start of the search if logging is enabled
            logData(
                    "findOccurrences()",
                    "Starting search in file: " + inputFilePath.getAbsolutePath() + " for string: " + searchString,
                    ReplaceStringInFiles.LogType.INFO
            );

            // read the file line by line
            try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(inputFilePath))) {
                String line;
                int lineNumber = 1; // Start line number from 1
                while ((line = reader.readLine()) != null) {
                    // find occurrences in the current line
                    Line[] occurrences = findAndReturnOccurrence(line, searchString, lineNumber);
                    if (occurrences != null) {

                        // log the occurrences if logging is enabled
                        logData(
                                "findOccurrences()",
                                "Found occurrences in line " + lineNumber + ": " + Arrays.toString(occurrences),
                                ReplaceStringInFiles.LogType.INFO
                        );

                        searchResults.addAll(Arrays.asList(occurrences)); // Add found occurrences to the list
                    }
                    lineNumber++; // Increment line number
                }
            }
        } else {

            // log the start of the search if logging is enabled
            logData(
                    "findOccurrences()",
                    "Starting search in string for: " + searchString,
                    ReplaceStringInFiles.LogType.INFO
            );
            // If initialized with a string, split it into lines and search each line
            String[] lines = inputString.split("\n");
            for (int i = 0; i < lines.length; i++) {
                Line[] occurrences = findAndReturnOccurrence(lines[i], searchString, i + 1); // Line numbers start from 1
                if (occurrences != null) {

                    // log the occurrences if logging is enabled
                    logData(
                            "findOccurrences()",
                            "Found occurrences in line " + (i + 1) + ": " + Arrays.toString(occurrences),
                            ReplaceStringInFiles.LogType.INFO
                    );

                    searchResults.addAll(Arrays.asList(occurrences)); // Add found occurrences to the list
                }
            }
        }
        // If no occurrences were found, return an empty TextSearchResult
        if (searchResults.isEmpty()) {

            logData(
                    "findOccurrences()",
                    "No occurrences found for search string: " + searchString + " in " + (isInitilizedWithFile ? inputFilePath.getAbsolutePath() : "the provided string"),
                    ReplaceStringInFiles.LogType.INFO
            );

            return null; // Return null if no occurrences were found
        }
        // Convert the list of search results to an array
        Line[] resultArray = searchResults.toArray(new Line[0]);

        // log processing completion if logging is enabled
        logData(
                "findOccurrences()",
                "Search completed. Total occurrences found: " + resultArray.length,
                ReplaceStringInFiles.LogType.INFO
        );
        // Return a TextSearchResult object containing the search results and the file path (if applicable)
        return new javadev.stringcollections.textreplacor.object.TextSearchResult(resultArray, inputFilePath != null ? inputFilePath : new File(""));

    }

    // logger method to log when useLogging is true
    public void logData(String methodName, String message, ReplaceStringInFiles.LogType logType) {
        if (useLogging) {
            ReplaceStringInAFile.logData(this.getClass(), methodName, message, logType);
        }
    }

}
