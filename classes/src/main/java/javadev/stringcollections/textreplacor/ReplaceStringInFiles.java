package javadev.stringcollections.textreplacor;

import javadev.stringcollections.textreplacor.console.ColoredConsoleOutput;
import javadev.stringcollections.textreplacor.exception.TextReplacerError;
import javadev.stringcollections.textreplacor.filesquery.DirectoryReader;
import javadev.stringcollections.textreplacor.mimedetector.TextFileDetector;
import javadev.stringcollections.textreplacor.writer.ReplaceStringInAFile;
import librarycollections.nurujjamanpollob.mimedetector.MagicException;
import librarycollections.nurujjamanpollob.mimedetector.MagicMatchNotFoundException;
import librarycollections.nurujjamanpollob.mimedetector.MagicParseException;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * @author nurujjamanpollob
 * @apiNote
 * This class is used to replace a string in multiple files.
 * <p>
 * This class usages the {@link javadev.stringcollections.textreplacor.filesquery.DirectoryReader} class to read files from a directory.
 * <p>
 * The directory tree is traversed and files paths are collected in a list.
 * <p>
 * Then This class usages the {@link librarycollections.nurujjamanpollob.mimedetector.Magic} class to detect the mime type of the file.
 * <p>
 * If the mime type is text, then the file is read and the string is replaced using the
 * <p>
 * Empty and binary files are skipped. <br><br>
 *
 * If you use logging, then the log file will be created in this path: <b>current_working_directory/logs/universal-file-replacer-logs/file_replace.log</b>
 * <p>
 * By default, the buffer size is 1024. You can change it by passing the buffer size in the constructor, also logging is disabled by default.
 * <p>
 * If you want to use logging, use
 * @version 1.0
 * @since 1.0
 * @see javadev.stringcollections.textreplacor.filesquery.DirectoryReader
 * @see librarycollections.nurujjamanpollob.mimedetector.Magic
 * @see java.nio.file.Files
 * @see java.nio.file.Paths
 * @see java.nio.file.StandardOpenOption
 * @see java.nio.file.StandardCopyOption
 * @see java.nio.file.Path for more information
 *
 */

@SuppressWarnings("unused")
public class ReplaceStringInFiles {

    // array of ignoring file extensions
    private final String[] ignoreFileExtensions;

    // array of only operation with selected file extensions
    private final String[] onlyFileExtensions;

    // path of initial directory, where all text files will be replaced
    private final String initialDirectoryPath;

    // old string to replace
    private final String oldString;

    // new string to replace
    private final String newString;

    // buffer size to read/write file content
    private final int bufferSize;

    /**
     * -- SETTER --
     *  This method used to set the useLogging flag.
     *
     */
    // use logging flag
    @Setter
    private boolean useLogging = false;

    private final boolean proceedOnlyFilesWithExtensions;
    private final boolean useFiltering;

    /**
     * Suppress default constructor for noninstantiability
     */
    private ReplaceStringInFiles() {
        throw new AssertionError("Cannot instantiate this class with default constructor");
    }

    /**
     * Initialize the class with the ignoreFileExtensions, which is referred to ignore files that extension is ends with the given extensions.
     * @param ignoreFileExtensions array of ignoring file extensions
     * @param initialDirectoryPath path of initial directory, where all text files will be replaced
     * @param oldString old string to replace
     * @param newString new string to replace
     */
    public ReplaceStringInFiles(String[] ignoreFileExtensions, String initialDirectoryPath, String oldString, String newString) {
        this.ignoreFileExtensions = ignoreFileExtensions;
        this.onlyFileExtensions = null;
        this.initialDirectoryPath = initialDirectoryPath;
        this.oldString = oldString;
        this.newString = newString;
        this.bufferSize = -1;
        this.proceedOnlyFilesWithExtensions = false;
        this.useFiltering = true;
    }

    /**
     * Initialize the class with the onlyFileExtensions, which is referred to only operation with selected file extensions.
     * @param ignoreFileExtensions array of only operation with selected file extensions
     * @param initialDirectoryPath path of initial directory, where all text files will be replaced
     * @param oldString old string to replace
     * @param newString new string to replace
     * @param bufferSize buffer size to read/write file content
     */
    public ReplaceStringInFiles(String[] ignoreFileExtensions, String initialDirectoryPath, String oldString, String newString, int bufferSize) {
        this.ignoreFileExtensions = ignoreFileExtensions;
        this.onlyFileExtensions = null;
        this.initialDirectoryPath = initialDirectoryPath;
        this.oldString = oldString;
        this.newString = newString;
        this.bufferSize = bufferSize;
        this.proceedOnlyFilesWithExtensions = false;
        this.useFiltering = true;
    }

    /**
     * Initialize the class with the proceedOnlyFilesWithExtensions, which is referred to only operation with selected file extensions.
     * @param onlyFileExtensions array of only operation with selected file extensions
     * @param initialDirectoryPath path of initial directory, where all text files will be replaced
     * @param oldString old string to replace
     * @param newString new string to replace
     */
    public ReplaceStringInFiles(String initialDirectoryPath, String oldString, String newString, String[] onlyFileExtensions) {
        this.ignoreFileExtensions = null;
        this.onlyFileExtensions = onlyFileExtensions;
        this.initialDirectoryPath = initialDirectoryPath;
        this.oldString = oldString;
        this.newString = newString;
        this.bufferSize = -1;
        this.proceedOnlyFilesWithExtensions = true;
        this.useFiltering = true;
    }

    /**
     * Initialize the class with the proceedOnlyFilesWithExtensions, which is referred to only operation with selected file extensions.
     * @param onlyFileExtensions array of only operation with selected file extensions
     * @param initialDirectoryPath path of initial directory, where all text files will be replaced
     * @param oldString old string to replace
     * @param newString new string to replace
     * @param bufferSize buffer size to read/write file content
     */
    public ReplaceStringInFiles(String initialDirectoryPath, String oldString, String newString, String[] onlyFileExtensions, int bufferSize) {
        this.ignoreFileExtensions = null;
        this.onlyFileExtensions = onlyFileExtensions;
        this.initialDirectoryPath = initialDirectoryPath;
        this.oldString = oldString;
        this.newString = newString;
        this.bufferSize = bufferSize;
        this.proceedOnlyFilesWithExtensions = true;
        this.useFiltering = true;
    }

    /**
     * Constructor with no filtering. This option will not use any filtering.
     * @param initialDirectoryPath path of initial directory, where all text files will be replaced
     * @param oldString old string to replace
     * @param newString new string to replace
     */
    public ReplaceStringInFiles(String initialDirectoryPath, String oldString, String newString) {
        this.ignoreFileExtensions = null;
        this.onlyFileExtensions = null;
        this.initialDirectoryPath = initialDirectoryPath;
        this.oldString = oldString;
        this.newString = newString;
        this.bufferSize = -1;
        this.proceedOnlyFilesWithExtensions = false;
        this.useFiltering = false;
    }

    /**
     * Constructor with no filtering. This option will not use any filtering.
     * @param initialDirectoryPath path of initial directory, where all text files will be replaced
     * @param oldString old string to replace
     * @param newString new string to replace
     * @param bufferSize buffer size to read/write file content
     */
    public ReplaceStringInFiles(String initialDirectoryPath, String oldString, String newString, int bufferSize) {
        this.ignoreFileExtensions = null;
        this.onlyFileExtensions = null;
        this.initialDirectoryPath = initialDirectoryPath;
        this.oldString = oldString;
        this.newString = newString;
        this.bufferSize = bufferSize;
        this.proceedOnlyFilesWithExtensions = false;
        this.useFiltering = false;
    }

    /**
     * This method used to replace the string in the files.
     */
    public void replaceStringInFiles() throws TextReplacerError {

        // test if the initial directory path is null or empty
        if (initialDirectoryPath == null || initialDirectoryPath.isEmpty()) {

            // show log that then an initial directory path is null or empty
            logMessage("Initial directory path is null or empty", LogType.ERROR);
            // throw an error
            throw new TextReplacerError("Initial directory path is null or empty");
        }

        // get the directory reader
        DirectoryReader directoryReader = new DirectoryReader(initialDirectoryPath);

        // get the files
        List<File> files = directoryReader.listAllFiles();

        // show log that the process is started
        logMessage(
                "File replacing process form this directory %s has been started. Now processing %s size of files".formatted(initialDirectoryPath, files.size()),
                LogType.INFO);

        // loop through the files
        for (File file : files) {
           processFile(file);
        }

        // show log that the process is completed
        logMessage(
                ("File replacing process form this directory %s has been completed. " +
                        "Processing completed for %s size of files. Some files may be ignored due to provided settings and not being a text file.").formatted(initialDirectoryPath, files.size()),
                LogType.INFO);



    }


    /**
     * This method used to determine if the file should be processed or not.
     * <p>
     * This method is designed with a future extension in mind, in case if we need to add more conditions to process the file.
     */
    public void processFile(File file) throws TextReplacerError {


        // if filtering is disabled, return true
        if (!useFiltering) {

            // execute if the file is valid and text mime type
            if (isFileValid(file) && isTextFile(file)) {
                // replace the string in the file
                ReplaceStringInAFile replaceStringInAFile = new ReplaceStringInAFile(file, oldString, newString, bufferSize);
                replaceStringInAFile.setUseLogger(useLogging);
                replaceStringInAFile.replaceString();
            }
        } else {


            if (isFileExtensionInIgnoreList(file)) {
                // Do Log
                logMessage("File %s is ignored due to provided settings".formatted(file.getAbsolutePath()), LogType.INFO);

                // Show log that the file is ignored
                ColoredConsoleOutput.printBlueText("File %s is ignored due to provided settings".formatted(file.getAbsolutePath()));

                return;
            }

            // check if file is in process list only, otherwise detect if text file and process
            if (isFileExtensionInProcessOnlyList(file)) {
                // replace the string in the file
                ReplaceStringInAFile replaceStringInAFile = new ReplaceStringInAFile(file, oldString, newString, bufferSize);
                replaceStringInAFile.setUseLogger(useLogging);
                replaceStringInAFile.replaceString();
            } else {
                // if the file is valid and text mime type
                if (isFileValid(file) && isTextFile(file)) {
                    // replace the string in the file
                    ReplaceStringInAFile replaceStringInAFile = new ReplaceStringInAFile(file, oldString, newString, bufferSize);
                    replaceStringInAFile.setUseLogger(useLogging);
                    replaceStringInAFile.replaceString();

                    // Do Log
                    logMessage("File %s is processed".formatted(file.getAbsolutePath()), LogType.INFO);
                } else {
                    // Do Log
                    logMessage("File %s is ignored due to provided settings and not being a text file".formatted(file.getAbsolutePath()), LogType.INFO);
                }
            }
        }

    }

    // this method tests it if the file extension is in the ignore list
    private boolean isFileExtensionInIgnoreList(File file) {


        if (!useFiltering) {
            return false;
        }

        // first test processWithOnlyFilesWithExtensions is true
        if (ignoreFileExtensions == null || proceedOnlyFilesWithExtensions) {
            return false;
        }

        // get the file name
        String fileName = file.getName();

        // loop through the ignored file extensions
        for (String ignoreFileExtension : ignoreFileExtensions) {

            // if the file name ends with the ignore file extension without case sensitivity
            if (fileName.toLowerCase().endsWith(ignoreFileExtension.toLowerCase())) {
                return true;
            }
        }

        return false; // return false
    }

    // this method used to test if the file extension is in the process only list
    private boolean isFileExtensionInProcessOnlyList(File file) {

        // if filtering is disabled, return false
        if (!useFiltering) {
            return false;
        }

        // first test processWithOnlyFilesWithExtensions is true
        if (onlyFileExtensions == null || !proceedOnlyFilesWithExtensions) {
            return false;
        }

        // get the file name
        String fileName = file.getName();

        // loop through the ignored file extensions
        for (String onlyFileExtension : onlyFileExtensions) {
            // if the file name ends with the ignore file extension, file extension check without case sensitivity
            if (fileName.toLowerCase().endsWith(onlyFileExtension.toLowerCase())) {
                return isTextFile(file);
            }
        }

        return false; // return false
    }

    /**
     * Returns true if a file is a text file based on its MIME type.
     */
    public static boolean isTextFile(java.io.File file) {

        // check if file size is not zero and object not null
        if (file == null || !file.exists() || file.length() == 0) {
            return false; // Not a valid file or empty file
        }

        // analyze the file to get its MIME type
        try {
            return TextFileDetector.isTextFile(file.toPath());
        } catch (IOException e) {

            // log the error
            System.err.println("Error detecting MIME type for file " + file.getAbsolutePath() + ": " + e.getMessage());
            return  false;
        }
    }

    public enum LogType {
        INFO, WARN, ERROR, DEBUG;

        /**
         * Get the log type string
         */
        public String getLogTypeString() {
            return
                    this == INFO ? "INFO" :
                            this == WARN ? "WARN" :
                                    this == ERROR ? "ERROR" :
                                            this == DEBUG ? "DEBUG" :
                                                    "EXCEPTION";
        }

    }


    // tests if a file is not null, not a directory, and not empty file
    private boolean isFileValid(File file) {
        return file != null && file.isFile() && file.length() > 0;
    }

    /**
     * This method used to log the message
     *
     * @param message message
     * @param logType log type
     */
    private void logMessage(String message, LogType logType) {
        // if logging is disabled, return
        if (!useLogging) {
            return;
        }

        ReplaceStringInAFile.logData(ReplaceStringInFiles.class, "replaceStringInFiles()", message, logType);


    }



}
