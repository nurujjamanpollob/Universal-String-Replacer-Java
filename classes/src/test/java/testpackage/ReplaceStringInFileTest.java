package testpackage;

import javadev.stringcollections.textreplacor.exception.TextReplacerError;
import javadev.stringcollections.textreplacor.filesquery.DirectoryReader;
import javadev.stringcollections.textreplacor.writer.ReplaceStringInAFile;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;

/**
 * Test class for {@link javadev.stringcollections.textreplacor.writer.ReplaceStringInAFile}
 */
public class ReplaceStringInFileTest {

    @Test
    public void testReplaceStringInFile() throws IOException {

        String testFile = "src/main/resources/test-files/ReplaceStringInFilesTest";


        // check if the file exists
        if (DirectoryReader.fileExists(testFile)) {

            // create a file object
            File file = new File(testFile);

            // create a new instance of ReplaceStringInAFile
            // and replace the string
            ReplaceStringInAFile replaceStringInAFile = new ReplaceStringInAFile(file, "me", "I've Replaced Already!");
            String replacedFilePath = null;
            try {
                replacedFilePath = replaceStringInAFile.replaceString();
            } catch (TextReplacerError e) {
                // handle the exception
                e.printStackTrace();
            }

            System.out.println("Replaced File Path: " + replacedFilePath);

            // print the replaced file content, use String Reader
            // to read the file content
            BufferedReader bufferedReader = new BufferedReader(new StringReader(replacedFilePath));

            // read the file content
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            // close the bufferedReader
            bufferedReader.close();


        } else {
            System.out.println("File does not exist!");
        }



    }
}
