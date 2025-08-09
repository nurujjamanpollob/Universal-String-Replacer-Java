package testpackage;

import javadev.stringcollections.textreplacor.console.ColoredConsoleOutput;
import javadev.stringcollections.textreplacor.io.TextSearchResultSaver;
import javadev.stringcollections.textreplacor.io.json.JSONObjectUtility;
import javadev.stringcollections.textreplacor.object.Line;
import javadev.stringcollections.textreplacor.object.TextSearchResult;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for {@link javadev.stringcollections.textreplacor.io.TextSearchResultSaver} class.
 *
 */
public class TextSearchResultSaverTest {

    /**
     * Test {@link TextSearchResultSaver#getJsonStructure(List, String, String)}
     */
    @Test
    public void testGetJsonStructure() {
        // Create a sample list of TextSearchResult objects
        List<TextSearchResult> results = new ArrayList<>();
        // Create a sample TextSearchResult with some lines
        // Create two sample lines
        Line line1 = new Line(10, "Example line content", 5, 12);
        Line line2 = new Line(3, "Another match", 0, 6);
        TextSearchResult result1 = new TextSearchResult(new Line[] {line1}, new File("C:/projects/my-root-dir/src/File1.txt"));
        TextSearchResult result2 = new TextSearchResult(new Line[] {line2}, new File("C:/projects/my-root-dir/src/File2.txt"));

        List<TextSearchResult> results1 = new ArrayList<>();
        results1.add(result1);
        results1.add(result2);
        // Call the method to get the JSON structure
        String jsonStructure = TextSearchResultSaver.getJsonStructure(results1, "C:/projects/my-root-dir", "test");

        // Assert that the JSON structure is not null or empty
        assert !jsonStructure.isEmpty() : "Expected non-empty JSON structure";

        // Optionally, print the JSON structure for manual verification
        ColoredConsoleOutput.printGreenText(JSONObjectUtility.formatJson(jsonStructure));
    }

    /**
     * Test {@link TextSearchResultSaver#getJsonStructure(List, String, String)}
     */
    @Test
    public void testSaveToFile() {
        // Create a sample list of TextSearchResult objects
        List<TextSearchResult> results = new ArrayList<>();
        // Create a sample TextSearchResult with some lines
        // Create two sample lines
        Line line1 = new Line(10, "Example line content", 5, 12);
        Line line2 = new Line(3, "Another match", 0, 6);
        TextSearchResult result1 = new TextSearchResult(new Line[] {line1}, new File("C:/projects/my-root-dir/src/File1.txt"));
        TextSearchResult result2 = new TextSearchResult(new Line[] {line2}, new File("C:/projects/my-root-dir/src/File2.txt"));

        results.add(result1);
        results.add(result2);

        // Call the method to save the results to a file
        TextSearchResultSaver saver = new TextSearchResultSaver();
        String saveLocation = "src/main/resources/test-files/search-results.json";
        String rootDirectory = "C:/projects/my-root-dir";
        try {
            saver.saveResultsToJsonFile(results, saveLocation, rootDirectory, "test");
            ColoredConsoleOutput.printGreenText("Results saved successfully to " + saveLocation);
        } catch (Exception e) {
            ColoredConsoleOutput.printRedText("Failed to save results: " + e.getMessage());
            assert false : "Expected successful saving of results, but got exception: " + e.getMessage();
        }
    }
}
