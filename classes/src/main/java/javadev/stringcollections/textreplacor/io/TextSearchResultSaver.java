package javadev.stringcollections.textreplacor.io;

import javadev.stringcollections.textreplacor.io.json.JSONObjectUtility;
import javadev.stringcollections.textreplacor.object.Line;
import javadev.stringcollections.textreplacor.object.TextSearchResult;

import java.io.IOException;
import java.util.List;

/**
 * Class that saves the result of text search operations as JSON file. Result format:
 * <pre>
 *     <code> {
 *   "rootDirectory": "C:/projects/my-root-dir",
 *   "results": [
 *     {
 *       "file": "C:/projects/my-root-dir/src/File1.txt",
 *       "lines": [
 *         {
 *           "lineNumber": 10,
 *           "lineContent": "Example line content",
 *           "startIndex": 5,
 *           "endIndex": 12
 *         }
 *       ]
 *     },
 *     {
 *       "file": "C:/projects/my-root-dir/src/File2.txt",
 *       "lines": [
 *         {
 *           "lineNumber": 3,
 *           "lineContent": "Another match",
 *           "startIndex": 0,
 *           "endIndex": 6
 *         }
 *       ]
 *     }
 *   ]
 * }
 *
 * This JSON structure contains a root directory and an array of results, where each result includes the file path and an array of lines with their respective details.
 * @author nurujjamanpollob
 * @version 1.0
 * @apiNote Class that saves the result of text search operations as JSON file.
 * @since 1.0
 *
 * </code>
 */
public class TextSearchResultSaver {

    /**
     * Saves the search results to a JSON file.
     * Accept a list of TextSearchResult objects and a root directory path.
     * JSON Format:
     * <pre>
     *     <code> {
     *     {
     *   "rootDirectory": "C:/projects/my-root-dir",
     *   "results": [
     *     {
     *       "file": "C:/projects/my-root-dir/src/File1.txt",
     *       "lines": [
     *         {
     *           "lineNumber": 10,
     *           "lineContent": "Example line content",
     *           "startIndex": 5,
     *           "endIndex": 12
     *         }
     *       ]
     *     },
     *     {
     *       "file": "C:/projects/my-root-dir/src/File2.txt",
     *       "lines": [
     *         {
     *           "lineNumber": 3,
     *           "lineContent": "Another match",
     *           "startIndex": 0,
     *           "endIndex": 6
     *         }
     *       ]
     *     }
     *   ]
     * }
     *      </code>
     * @param results the list of TextSearchResult objects to save
     * @param rootDirectory the root directory path to be included in the JSON file.
     * @param saveLocation the directory where the JSON file will be saved.
     *                     The paths will be created if they do not exist.
     * @param searchPhrase the search phrase used to find the results, which will be included in the JSON file.
     * @throws java.io.IOException if an I/O error occurs while saving the file.
     */
    public void saveResultsToJsonFile(List<TextSearchResult> results, String saveLocation, String rootDirectory, String searchPhrase) throws IOException {
        // check if the path is existing or not
        if (results == null || results.isEmpty() || saveLocation == null || saveLocation.isEmpty()) {
            throw new IOException("Results or save location cannot be null or empty. Please provide valid inputs.");
        }
        // Create the directory if it does not exist
        PathResolver.resolvePathIfNotExists(saveLocation);

        // Now we can create the JSON structure and save it to a file
        String jsonStructure = JSONObjectUtility.formatJson(getJsonStructure(results, rootDirectory, searchPhrase));

        // check if file is exists, so abandon the operation
        if (PathResolver.isPathExists(saveLocation)) {
            throw new IOException("File already exists at the specified location: " + saveLocation +
                    ". Please provide a different file name or location.");
        }

        // now save the JSON structure to a file
        ByteWriter.writeToPath(jsonStructure, saveLocation);
    }

    /**
     * Convert a list of {@link TextSearchResult} object to JSON compatible String Return the JSON Structure of the search results
     */
    public static String getJsonStructure(List<TextSearchResult> results, String rootDirectory, String searchPhrase) {

        // Null and empty checks
        if (results == null || results.isEmpty() || rootDirectory == null || rootDirectory.isEmpty()) {
            throw new IllegalArgumentException("Results or root directory cannot be null or empty.");
        }

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");
        jsonBuilder.append("\"rootDirectory\": \"").append(JSONObjectUtility.escapeSpecialCharacters(rootDirectory)).append("\",\n");
        jsonBuilder.append("\"searchPhrase\": \"").append(JSONObjectUtility.escapeSpecialCharacters(searchPhrase)).append("\",\n");
        jsonBuilder.append("\"results\": [\n");

        for (int i = 0; i < results.size(); i++) {
            TextSearchResult result = results.get(i);
            jsonBuilder.append("{\n");
            jsonBuilder.append("\"file\": \"").append(JSONObjectUtility.escapeSpecialCharacters(result.file().getAbsolutePath())).append("\",\n");
            jsonBuilder.append("\"lines\": [\n");

            // get the lines from the result
            Line[] lines = result.lines();

            for (int j = 0; j < lines.length; j++) {
                Line line = lines[j];
                jsonBuilder.append("{\n");
                jsonBuilder.append("\"lineNumber\": ").append(line.lineNumber()).append(",\n");
                jsonBuilder.append("\"lineContent\": \"").append(JSONObjectUtility.escapeSpecialCharacters(line.lineContent())).append("\",\n");
                jsonBuilder.append("\"startIndex\": ").append(line.startIndex()).append(",\n");
                jsonBuilder.append("\"endIndex\": ").append(line.endIndex()).append("\n");
                jsonBuilder.append("}");

                if (j < lines.length - 1) {
                    jsonBuilder.append(",\n");
                } else {
                    jsonBuilder.append("\n");
                }
            }
            jsonBuilder.append("]\n");
            jsonBuilder.append("}");
            if (i < results.size() - 1) {
                jsonBuilder.append(",\n");
            } else {
                jsonBuilder.append("\n");
            }

        }

        jsonBuilder.append("]\n");
        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

}
