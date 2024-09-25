package testpackage;

import javadev.stringcollections.textreplacor.filesquery.DirectoryReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Test class for {@link javadev.stringcollections.textreplacor.writer.ReplaceStringInAFile}
 */
public class ReplaceStringInFileTest {

    @Test
    public void testReplaceStringInFile() throws IOException {

        String testFile = DirectoryReader.getBaseResourcePath() + "test-files/ReplaceStringInFilesTest";

        System.out.println("Test file: " + testFile);



    }
}
