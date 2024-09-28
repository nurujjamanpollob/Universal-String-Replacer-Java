package javadev.stringcollections.textreplacor.reader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileEncodingDetector {

    public static String getFileEncoding(File file) throws IOException {
        // get the file encoding
        FileReader fileReader = new FileReader(file);

        String encoding = fileReader.getEncoding();

        fileReader.close();

        return encoding;
    }


}
