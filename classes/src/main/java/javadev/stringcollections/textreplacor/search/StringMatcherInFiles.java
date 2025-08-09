package javadev.stringcollections.textreplacor.search;

import javadev.stringcollections.textreplacor.ReplaceStringInFiles;
import javadev.stringcollections.textreplacor.filesquery.DirectoryReader;
import javadev.stringcollections.textreplacor.object.TextSearchResult;
import javadev.stringcollections.textreplacor.writer.ReplaceStringInAFile;
import librarycollections.nurujjamanpollob.mimedetector.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nurujjamanpollob
 * @apiNote Matches a String occurrences in a directory of files. It accepts a directory and a String to search.
 * It will search the String in all the files in the directory and return the occurrences as a list of {@link javadev.stringcollections.textreplacor.object.TextSearchResult} objects.
 *
 * The text file with 'text/plain' MIME type is only used for searching. This class doesn't handle binary files or other types of files, and detect text file by MIME type, not by its extension.
 */
public class StringMatcherInFiles {

    /**
     * -- GETTER --
     *  Returns the directory path where the search will be performed.
     *
     * @return the directory path as a String
     */
    @Getter
    private final String directoryPath;
    /**
     * -- GETTER --
     *  Returns the String that will be searched in the files.
     *
     * @return the search String
     */
    @Getter
    private final String searchString;
    @Setter
    private boolean includeTextWhereMatched = false; // If true, the text where the search string matched will be included in the result// .
    @Setter
    @Getter
    private boolean useLogging = false; // If true, the class will log data using ReplaceStringInAFile.logData() method
    @Setter
    @Getter
    private boolean skipLineCollection = false; // If true, the class will not collect line information in the result. Default is false.

    /**
     * Constructor to initialize a StringMatcherInFiles object.
     *
     * @param directoryPath the path of the directory to search in
     * @param searchString  the String to search for in the files
     */
    public StringMatcherInFiles(String directoryPath, String searchString) {
        this.directoryPath = directoryPath;
        this.searchString = searchString;
    }

    /**
     * Construct a StringMatcherInFiles object with a default search string. It access a file object instance
     * and uses its absolute path as the directory path.
     * @param file the File object representing the directory to search in
     * @param searchString the String to search for in the files
     */
    public StringMatcherInFiles(File file, String searchString) {
        this.directoryPath = file.getAbsolutePath();
        this.searchString = searchString;
    }


    /**
     * Starts the search for the String in the files of the directory.
     * This method will read all files in the directory and search for the String.
     * It will return a list of {@link javadev.stringcollections.textreplacor.object.TextSearchResult} objects containing the search results.
     *
     * @return a list of TextSearchResult objects containing the search results. Null if not match found!
     * @throws IOException if an I/O error occurs while reading the files
     */
    public @Nullable List<TextSearchResult> search() throws IOException {

        // basic test: if user passed null or empty path or a file path? throw an exception
        validateArguments();

        List<TextSearchResult> textSearchResults = new ArrayList<>();

        DirectoryReader directoryReader = new DirectoryReader(directoryPath);

        List<File> files = directoryReader.listAllFiles();

        // log
        logData("search", "Searching for '" + searchString + "' in directory: " + directoryPath, ReplaceStringInFiles.LogType.INFO);
        // show how many files found
        logData("search", "Found " + files.size() + " files in directory: " + directoryPath, ReplaceStringInFiles.LogType.INFO);

        for (File file : files) {
            if (isTextFile(file)) {
                // log
                logData("search", "Searching in file: " + file.getAbsolutePath(), ReplaceStringInFiles.LogType.INFO);

                FindOccurrencesInAString findOccurrencesInAString = new FindOccurrencesInAString(file, searchString);
                findOccurrencesInAString.setIncludeTextWhereMatched(includeTextWhereMatched);
                findOccurrencesInAString.setSkipLineCollection(skipLineCollection);

                TextSearchResult textSearchResult = findOccurrencesInAString.findOccurrences();

                if(!skipLineCollection) {
                    // if object is not null and result is not empty, add to results
                    if (textSearchResult != null && textSearchResult.lines().length > 0) {
                        // log
                        logData("search", "Found " + textSearchResult.lines().length + " occurrences in file: " + file.getAbsolutePath(), ReplaceStringInFiles.LogType.INFO);
                        // log the text search result by toString() method
                        logData("search", "TextSearchResult: " + textSearchResult, ReplaceStringInFiles.LogType.INFO);
                        textSearchResults.add(textSearchResult);
                    }
                } else {
                    // if skipLineCollection is true, we only care about the file and not the lines
                    if (textSearchResult != null) {
                        // log
                        logData("search", "Found occurrences in file: " + file.getAbsolutePath(), ReplaceStringInFiles.LogType.INFO);
                        textSearchResults.add(textSearchResult);
                    }
                }

            }
        }
        // if no results found, return null
        if (textSearchResults.isEmpty()) {
            logData("search", "No occurrences found for '" + searchString + "' in directory: " + directoryPath, ReplaceStringInFiles.LogType.INFO);
            return null;
        }

        return textSearchResults;
    }

    /**
     * Returns true if a file is a text file based on its MIME type.
     */
    public static boolean isTextFile(java.io.File file) {

        // check if file size is not zero and object not null
        if (file == null || !file.exists() || file.length() == 0) {
            return false; // Not a valid file or empty file
        }

        MagicMatch match;
        try {
            match = Magic.getMagicMatch(file, true, false);
        } catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {
            // log error
            ReplaceStringInAFile.logData(StringMatcherInFiles.class, "isTextFile", "Error detecting MIME type for file: " + file.getAbsolutePath(), ReplaceStringInFiles.LogType.ERROR);
            e.printStackTrace();
            return false;
        }

        return match.getMimeType().contains("text");
    }

    // this method validate passed arguments for error and throw IOException if any error found
    private void validateArguments() throws IOException {
        if (directoryPath == null || directoryPath.isEmpty()) {
            // log
            logData("validateArguments", "Directory path is null or empty", ReplaceStringInFiles.LogType.ERROR);
            throw new IOException("Directory path is null or empty");
        }
        if (!DirectoryReader.fileExists(directoryPath)) {
            // log
            logData("validateArguments", "Directory does not exist at " + directoryPath, ReplaceStringInFiles.LogType.ERROR);
            throw new IOException("Directory does not exist at " + directoryPath);
        }
        if (!new File(directoryPath).isDirectory()) {
            // log
            logData("validateArguments", "Path is not a directory at " + directoryPath, ReplaceStringInFiles.LogType.ERROR);
            throw new IOException("Path is not a directory at " + directoryPath);
        }
        if (searchString == null || searchString.isEmpty()) {
            // log
            logData("validateArguments", "Search string is null or empty", ReplaceStringInFiles.LogType.ERROR);
            throw new IOException("Search string is null or empty");
        }
    }

    public boolean isIncludeTextWhereMatched() {
        return includeTextWhereMatched;
    }

    // logger method to log when useLogging is true
    public void logData(String methodName, String message, ReplaceStringInFiles.LogType logType) {
        if (useLogging) {
            ReplaceStringInAFile.logData(this.getClass(), methodName, message, logType);
        }
    }

}
