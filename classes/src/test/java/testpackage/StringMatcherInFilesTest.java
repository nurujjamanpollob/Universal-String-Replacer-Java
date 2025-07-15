package testpackage;

import javadev.stringcollections.textreplacor.console.ColoredConsoleOutput;
import javadev.stringcollections.textreplacor.object.TextSearchResult;
import javadev.stringcollections.textreplacor.search.StringMatcherInFiles;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * Test class for {@link javadev.stringcollections.textreplacor.search.StringMatcherInFiles} class.
 */
public class StringMatcherInFilesTest {

    /**
     * test find 'test' in files
     */
    @Test
    public void testFindStringInFiles() throws IOException {
        // create a new instance of StringMatcherInFiles
        StringMatcherInFiles stringMatcherInFiles = new StringMatcherInFiles(
                "src/main/resources/test-files",
                "test"
        );

        List<TextSearchResult> results = stringMatcherInFiles.search();

        // assert that results are not null
        assert results != null : "Expected results to be not null, but found null";
        // assert that results size is greater than 0
        assert results.size() > 0 : "Expected results size to be greater than 0, but found " + results.size();
        // print the results
        for (TextSearchResult result : results) {
            ColoredConsoleOutput.printBlueText("File: " + result.file());
            for (var line : result.lines()) {
                ColoredConsoleOutput.printGreenText("Line " + line.lineNumber() + ": " + line.lineContent() +
                        " (Start: " + line.startIndex() + ", End: " + line.endIndex() + ")");
            }
        }
    }

    /**
     * test invalid file path, this also covers test for empty file path. Please make sure that the path is invalid
     */
    @Test
    public void testFindStringInFilesWithInvalidPath() {
        // create a new instance of StringMatcherInFiles with invalid path
        StringMatcherInFiles stringMatcherInFiles = new StringMatcherInFiles(
                "src/main/resources/invalid-path",
                "test"
        );

        try {
            // search for the string in files
            List<TextSearchResult> results = stringMatcherInFiles.search();
            // assert that results are null
            assert results == null : "Expected results to be null, but found " + results;
        } catch (IOException e) {
            // handle the exception
            e.printStackTrace();
        }
    }
}
