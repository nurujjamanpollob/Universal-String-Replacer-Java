package javadev.stringcollections.textreplacor.reader;

import java.io.File;
import java.io.IOException;

/**
 * @apiNote Class that calculate the memory size of a string.
 * which either load from the files or passed as a parameter.
 * <p>
 * To effectively calculate the memory size of a string,
 * if the string is loaded from a file, we read the text file encoding.
 * <p>
 *
 * @since 1.0
 * @version 1.0
 * @author nurujjamanpollob
 */
public class StringMemorySizeCalculator {

    /**
     * This method used to calculate memory size of a single character, based on a file reference
     * @param file file path
     * @return memory size of the 1 character
     */
    public static long calculateMemorySizeFromFile(File file) throws IOException {
        // get the original file encoding
        String fileEncoding = FileEncodingDetector.getFileEncoding(file);

        // now create a dummy string with the file encoding
        String dummyString = new String("a".getBytes(), fileEncoding);

        // return the memory size of the string
        return dummyString.length();

    }



}
