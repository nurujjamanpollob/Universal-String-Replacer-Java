package testpackage;

import javadev.stringcollections.textreplacor.console.ColoredConsoleOutput;
import javadev.stringcollections.textreplacor.object.Line;
import javadev.stringcollections.textreplacor.object.TextSearchResult;
import javadev.stringcollections.textreplacor.search.FindOccurrencesInAString;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * Test class for {@link FindOccurrencesInAString}
 */
public class FindOccurrenceInAStringTest {

    /**
     * Test method for {@link FindOccurrencesInAString#findOccurrences()}
     * There is total 5 of word test in the string.
     * line numbers: 3,4,5
     * indexes: line 3 {3-6}, line 4 {3-6, 8-11}, line 5 {3-6, 18-21}
     */
    @Test
    public void testFindOccurrences() {
        String testFile = "src/main/resources/test-files/string-occurrence-test-5-occurrences";

        // create a new instance of FindOccurrencesInAString
        FindOccurrencesInAString findOccurrencesInAString = new FindOccurrencesInAString(new File(testFile), "test");
        try {
            // find occurrences in the string
            TextSearchResult resultObj = findOccurrencesInAString.findOccurrences();

            assert resultObj != null;

            // print the occurrences
            Line[] lines = resultObj.lines();

            // assert the object sizes, should be 5
            assert lines.length == 5 : "Expected 5 lines, but found " + lines.length;

            // check first line object
            Line firstLine = lines[0];

            // check line number should be 3 and start index should be 3 and end index should be 6
            assert firstLine.lineNumber() == 3 : "Expected line number 3, but found " + firstLine.lineNumber();
            assert firstLine.startIndex() == 3 : "Expected start index 3, but found " + firstLine.startIndex();
            assert firstLine.endIndex() == 6 : "Expected end index 6, but found " + firstLine.endIndex();
            // check second line object
            Line secondLine = lines[1];
            // check line number should be 4 and start index should be 3 and end index should be 6
            assert secondLine.lineNumber() == 4 : "Expected line number 4, but found " + secondLine.lineNumber();
            assert secondLine.startIndex() == 3 : "Expected start index 3, but found " + secondLine.startIndex();
            assert secondLine.endIndex() == 6 : "Expected end index 6, but found " + secondLine.endIndex();
            // check third
            Line secondLine1 = lines[2];
            // check line number should be 4 and start index should be 8 and end index should be 11
            assert secondLine1.lineNumber() == 4 : "Expected line number 4, but found " + secondLine1.lineNumber();
            assert secondLine1.startIndex() == 8 : "Expected start index 8, but found " + secondLine1.startIndex();
            assert secondLine1.endIndex() == 11 : "Expected end index 11, but found " + secondLine1.endIndex();
            // check for forth line object should be line number 5 and start index should be 3 and end index should be 6
            Line thirdLine = lines[3];
            assert thirdLine.lineNumber() == 5 : "Expected line number 5, but found " + thirdLine.lineNumber();
            assert thirdLine.startIndex() == 3 : "Expected start index 3, but found " + thirdLine.startIndex();
            assert thirdLine.endIndex() == 6 : "Expected end index 6, but found " + thirdLine.endIndex();
            // check for fifth line object should be line number 5 and start index should be 18 and end index should be 21
            Line thirdLine1 = lines[4];
            assert thirdLine1.lineNumber() == 5 : "Expected line number 5, but found " + thirdLine1.lineNumber();
            assert thirdLine1.startIndex() == 18 : "Expected start index 18, but found " + thirdLine1.startIndex();
            assert thirdLine1.endIndex() == 21 : "Expected end index 21, but found " + thirdLine1.endIndex();

            // show the result in console
            for (Line line : lines) {
                // print the line number
                ColoredConsoleOutput.printBlueText(line.toString());
            }

            // show find occurrence test is successful
            ColoredConsoleOutput.printGreenText("Find Occurrence Test is Successful!");

        } catch (Exception e) {
            // handle the exception
            e.printStackTrace();
        }
    }

    /**
     * test with empty file
     */
    @Test
    public void testFindOccurrencesInEmptyFile() {
        String testFile = "src/main/resources/test-files/string-occurrence-test-empty";

        // create a new instance of FindOccurrencesInAString
        FindOccurrencesInAString findOccurrencesInAString = new FindOccurrencesInAString(new File(testFile), "test");
        try {
            // find occurrences in the string
            TextSearchResult resultObj = findOccurrencesInAString.findOccurrences();

            // assert the object is null
            assert resultObj == null : "Expected null result, but found " + resultObj;

            // show find occurrence test is successful
            ColoredConsoleOutput.printGreenText("Find Occurrence Test with Empty File is Successful!");

        } catch (Exception e) {
            // handle the exception
            e.printStackTrace();
        }
    }

    /**
     * test with valid file but no occurrences
     */
    @Test
    public void testFindOccurrencesInFileWithNoOccurrences() {
        String testFile = "src/main/resources/test-files/string-occurrence-test-contains-no-occurrence";

        // create a new instance of FindOccurrencesInAString
        FindOccurrencesInAString findOccurrencesInAString = new FindOccurrencesInAString(new File(testFile), "test");
        try {
            // find occurrences in the string
            TextSearchResult resultObj = findOccurrencesInAString.findOccurrences();

            // assert the object is null
            assert resultObj == null : "Expected null result, but found " + resultObj;

            // show find occurrence test is successful
            ColoredConsoleOutput.printGreenText("Find Occurrence Test with No Occurrences is Successful!");

        } catch (Exception e) {
            // handle the exception
            e.printStackTrace();
        }
    }


}
