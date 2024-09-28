package javadev.stringcollections.textreplacor.writer;

import javadev.stringcollections.textreplacor.ReplaceStringInFiles;
import javadev.stringcollections.textreplacor.exception.TextReplacerError;
import javadev.stringcollections.textreplacor.generator.RandomGenerator;
import javadev.stringcollections.textreplacor.logutility.Logger;
import librarycollections.nurujjamanpollob.mimedetector.*;
import lombok.Getter;
import lombok.Setter;

import java.io.*;

/**
 * @apiNote used to replace a string in a file.
 * <p>
 * Before the IO operation, it will check the file mime type. If the file is not a text type, it will throw exception<br>
 * <p>
 * Important Note: By default, it will use a buffer size of 1024 to read/write the file content.
 * So if the oldString size is greater than 1024, it will not replace the string. <br>
 * <p>
 * In order to make it work, use this constructor: {@link ReplaceStringInAFile#ReplaceStringInAFile(File, String, String, int)} <br><br>
 * For more information, see {@link javadev.stringcollections.textreplacor.ReplaceStringInFiles}
 * @since 1.0
 * @version 1.0
 * @see javadev.stringcollections.textreplacor.ReplaceStringInFiles
 * @author nurujjamanpollob
 */
public class ReplaceStringInAFile {

    private final File file;
    private final String oldString;
    private final String newString;
    private final int bufferSize;
    @Setter
    @Getter
    boolean useLogger = false;

    /**
     * Constructor to replace a string in a file without any buffer size
     * @param file file to replace string
     * @param oldString old string to replace
     * @param newString new string to replace
     */
    public ReplaceStringInAFile(File file, String oldString, String newString) {
        this.file = file;
        this.oldString = oldString;
        this.newString = newString;
        this.bufferSize = -1;
    }

    /**
     * Constructor to replace a string in a file with a buffer size
     * @param file file to replace string
     * @param oldString old string to replace
     * @param newString new string to replace
     * @param bufferSize buffer size to read file
     */
    public ReplaceStringInAFile(File file, String oldString, String newString, int bufferSize) {
        this.file = file;
        this.oldString = oldString;
        this.newString = newString;
        this.bufferSize = bufferSize;
    }

    /**
     * Suppress default constructor for noninstantiability
     */
    private ReplaceStringInAFile() {
        throw new AssertionError("Cannot instantiate this class with default constructor");
    }

    /**
     * Method that replace a string in a file. <br> <br>
     * Important Note: By default, it will use a buffer size of 1024 to read/write the file content.
     * So if the oldString size is greater than 1024, it will not replace the string. <br>
     * <p>
     * In order to make it work, use this constructor: {@link ReplaceStringInAFile#ReplaceStringInAFile(File, String, String, int)}
     * @throws TextReplacerError if any error occurs. such as file not exists, a file is not a text file, IO error occurs
     * @return the path of modified file
     */
    public String replaceString() throws TextReplacerError {

        // if file is null, throw an error
        if (file == null) {

            // show log that a file is null
            logMessage(
                    ReplaceStringInAFile.class,
                    "replaceString()",
                    "Cannot replace String, file is null!",
                    ReplaceStringInFiles.LogType.ERROR);

            throw new TextReplacerError("Cannot replace String, file is null!");
        }
        // test files existence
        if (!file.exists() || !file.isFile() || file.length() == 0) {

            // show log that a file is invalid or not exists
            logMessage(
                    ReplaceStringInAFile.class,
                    "replaceString()",
                    "Cannot replace String, this file is invalid or not exists! " + file.getAbsolutePath(),
                    ReplaceStringInFiles.LogType.ERROR);

            throw new TextReplacerError("Cannot replace String, this file is invalid or not exists! " + file.getAbsolutePath());
        }

        // read a file mime type
        // if a mime type is not a text, skip this file
        try {
            MagicMatch match = Magic.getMagicMatch(file, true, false);
            boolean isText = match.getMimeType().contains("text");
            if (isText) {

                // read and replace file content on the fly
               return readAndReplaceFileContent(file, oldString, newString, bufferSize);

            } else {
                String message = "Cannot replace String, this file is not a text file!. The file path is: " + file.getAbsolutePath() + " The mime type is: " + match.getMimeType();

                // show log that a file is not a text file
                logMessage(
                        ReplaceStringInAFile.class,
                        "replaceString()",
                        message,
                        ReplaceStringInFiles.LogType.ERROR);

                throw new TextReplacerError(message);
            }


        } catch (MagicParseException | MagicMatchNotFoundException | MagicException icException) {
            // if any error occurs, skip this file
        } catch (IOException e) {
            throw new TextReplacerError("IO Error Occurred while replacing string in file: " + file.getAbsolutePath() + " The error is: " + e.getMessage());
        }

        // show log that a file is not replaced
        logMessage(
                ReplaceStringInAFile.class,
                "replaceString()",
                "Cannot replace String, the file path is: " + file.getAbsolutePath(),
                ReplaceStringInFiles.LogType.ERROR);

        // throw an error if any error occurs
        throw new TextReplacerError("Cannot replace String, the file path is: " + file.getAbsolutePath());
    }

    /**
     * Method that read and replace file content.
     *
     * @param file file to read and replace content
     * @param oldString old string to replace
     * @param newString new string to replace
     * @param bufferSize buffer size to read a file
     */
    private String readAndReplaceFileContent(File file, String oldString, String newString, int bufferSize) throws IOException {

        // create an empty string file content, get an original file path and add a new extension, using random generation

        String randomExtension = RandomGenerator.generateRandomStringOnlyAlphabetsAndNumbers(10);

        File tmpFile = new File(file.getAbsolutePath() + "." +randomExtension);

        // show log that a tmp file is created
        logMessage(
                ReplaceStringInAFile.class,
                "readAndReplaceFileContent(File file, String oldString, String newString, int bufferSize)",
                "A input file is being read and replaced. The file path is: " + file.getAbsolutePath() +
                " meanwhile, A tmp file is created at this location to create the replaced content: "
                        + tmpFile.getAbsolutePath()
                        + " The old string is: " + oldString
                        + " The new string is: " + newString,
                ReplaceStringInFiles.LogType.INFO);

        BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile));

        // now read in chunks and replace the string if any match found
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            // if buffer size is -1
            if (bufferSize == -1) {
                bufferSize = getBufferSize(oldString);
            }
            char[] buffer = new char[bufferSize];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                String chunk = new String(buffer, 0, read);
                chunk = chunk.replace(oldString, newString);

                // write the chunk to the writer
                writer.write(chunk);
            }
        }

        // close the writer
        writer.close();

        // delete the original file
        boolean deleteResult = file.delete();


        // rename the tmp file to an original file
        boolean renameResult = tmpFile.renameTo(file);

        // if any error occurs, throw an error
        if (!deleteResult) {

            // show log that a file is not deleted
            logMessage(
                    ReplaceStringInAFile.class,
                    "readAndReplaceFileContent(File file, String oldString, String newString, int bufferSize)",
                    "Cannot delete the original file: " + file.getAbsolutePath(),
                    ReplaceStringInFiles.LogType.ERROR);

            throw new IOException("Cannot delete the original file: " + file.getAbsolutePath());
        }

        if (!renameResult) {

            // show log that a file is not renamed
            logMessage(
                    ReplaceStringInAFile.class,
                    "readAndReplaceFileContent(File file, String oldString, String newString, int bufferSize)",
                    "Cannot rename the tmp file to original file: " + file.getAbsolutePath(),
                    ReplaceStringInFiles.LogType.ERROR);

            throw new IOException("Cannot rename the tmp file to original file: " + file.getAbsolutePath());
        }

        // show log that a file is replaced with new content
        logMessage(
                ReplaceStringInAFile.class,
                "readAndReplaceFileContent(File file, String oldString, String newString, int bufferSize)",
                "A file is replaced with new content. The old string is: " + oldString + " The new string is: " + newString,
                ReplaceStringInFiles.LogType.INFO);


        return file.getAbsolutePath();


    }

    /**
     * This method used to log the message
     * @param clazz class
     * @param methodName method name
     * @param message message
     * @param logType log type
     */
    public void logMessage(Class<?> clazz, String methodName, String message, ReplaceStringInFiles.LogType logType) {
        // if logging is disabled, return
        if (!isUseLogger()) {
            return;
        }

        logData(clazz, methodName, message, logType);


    }

    public static void logData(Class<?> clazz, String methodName, String message, ReplaceStringInFiles.LogType logType) {
        switch (logType) {
            case INFO:
                Logger.logInfo(clazz, methodName, message);
                break;
            case WARN:
                Logger.logWarning(clazz, methodName, message);
                break;
            case ERROR:
                Logger.logError(clazz, methodName, message);
                break;
            case DEBUG:
                Logger.logDebug(clazz, methodName, message);
                break;
            default:
                Logger.logCustom(clazz, methodName, message);
                break;
        }
    }

    /**
     * Get the buffer size according to the old string size
     * @param oldString old string
     *                  @return buffer size
     */
    public static int getBufferSize(String oldString) {
        return Math.max(oldString.length() * 2, 1024);
    }


}
