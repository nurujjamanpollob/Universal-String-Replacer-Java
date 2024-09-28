package testpackage;

import javadev.stringcollections.textreplacor.ReplaceStringInFiles;
import javadev.stringcollections.textreplacor.exception.TextReplacerError;
import org.junit.jupiter.api.Test;

/**
 * Test class for ReplaceStringInFiles
 */
public class ReplaceStringInFilesTest {

    /**
     * Test method for ReplaceStringInFiles
     */
    @Test
    public void testReplaceStringInFiles() {

        // create 10 mb chunk byte size
        int chunkByteSize = 1024;

        ReplaceStringInFiles replaceStringInFiles = new ReplaceStringInFiles(
                "src/main/resources/test-files",
                "me",
                "I've Replaced Already!",
                chunkByteSize
        );

        try {
            replaceStringInFiles.replaceStringInFiles();
        } catch (TextReplacerError e) {

            // handle the exception
            e.printStackTrace();

        }
    }
}
