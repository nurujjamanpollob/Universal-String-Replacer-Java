package javadev.stringcollections.textreplacor.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class CopyFilesAndDirs {

    /**
     * Used to copy a directory from source to destination. It will copy all the files and directories from source to destination.
     */
    public static void copyDirectory(File source, File destination) throws IOException {
        FileUtils.copyDirectory(source, destination);
    }

    /**
     * Used to flat copy file
     */
    public static void copyFile(File source, File destination) throws IOException {
        FileUtils.copyFile(source, destination);
    }

}
