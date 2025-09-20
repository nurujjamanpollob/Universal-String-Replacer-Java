//package javadev.stringcollections.textreplacor.io;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author nurujjamanpollob
// * @version 1.0.0
// * @apiNote Class that read directory, list all subdirectories and files.
// * @since 1.0.0
// */
//@Getter
//@SuppressWarnings("unused")
//public class DirectoryReader {
//
//    /**
//     * Suppress default constructor for noninstantiability
//     */
//    private DirectoryReader() {
//        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
//    }
//
//    private final String directoryPath;
//    /**
//     * -- GETTER --
//     *  Get cehck read write permission flag
//     * <p>
//     *
//     * -- SETTER --
//     *  Set check read write permission
//     *
//     */
//    @Setter
//    private boolean checkReadWrite = false;
//
//    /**
//     * Constructor
//     *
//     * @param directoryPath directory path to read
//     */
//    public DirectoryReader(String directoryPath) {
//        this.directoryPath = directoryPath;
//    }
//
//
//    /**
//     * This method used to filter files by file extensions.
//     * Important note: if checkReadWrite is true,
//     * this method will also check if the file or folder(depending on includeDirectories) is readable and writable,
//     * and final result will be affected by that.
//     *
//     * @param files              files to filter
//     * @param s                  file extensions to filter
//     * @param includeDirectories include directories or not
//     * @return filtered files
//     */
//    private File[] filterFiles(@NotNull File[] files, @NotNull String[] s, boolean includeDirectories) {
//
//        // create a new array to hold filtered files
//        File[] filteredFiles = new File[files.length];
//        // create a counter to count filtered files
//        int counter = 0;
//        // iterate over files
//        for (File file : files) {
//
//            // check read write permission if checkReadWrite is true
//            if (checkReadWrite && !isFileReadableAndWritable(file)) {
//                continue;
//            }
//
//
//            // if file is a directory and includeDirectories is false, skip this file
//            if (file.isDirectory() && !includeDirectories) {
//                continue;
//            }
//            // if file is a directory and includeDirectories is true, add this file to filteredFiles
//            if (file.isDirectory() && includeDirectories) {
//                filteredFiles[counter++] = file;
//                continue;
//            }
//            // if file is not a directory, check if file extension is in s
//            for (String value : s) {
//
//                // if file extension is in s, add this file to filteredFiles
//                if (file.getName().endsWith(value)) {
//                    filteredFiles[counter++] = file;
//                    break;
//                }
//            }
//        }
//        // create a new array to hold filtered files
//        File[] filteredFiles2 = new File[counter];
//        // copy filteredFiles to filteredFiles2
//        System.arraycopy(filteredFiles, 0, filteredFiles2, 0, counter);
//        return filteredFiles2;
//
//    }
//
//    /**
//     * Returns true if the file or directory is readable and writable
//     * @param file file to check
//     *             @return true if the file or directory is readable and writable
//     */
//    public boolean isFileReadableAndWritable(File file) {
//        return file.canRead() && file.canWrite();
//    }
//
//    /**
//     * @return array of file
//     * @apiNote This method used to list all files and directories from a directory recursively.
//     * <p>
//     * Consider we have a directory structure like this:
//     * <pre>
//     *     /dir1
//     *     /subdir1
//     *     /subdir2>file.txt
//     *     /dir2>subdir3>subdir4>file2.txt
//     *     </pre>
//     * <p>
//     * <pre>
//     *             This method will return an array of file with the following order
//     *             /dir1
//     *             /subdir1
//     *             /subdir2/file.txt
//     *             /dir2/subdir3/subdir4/file2.txt
//     *             </pre>
//     * <p>
//     * If you want to list only files with specific file extensions, use {@link #listAllFilesAndDirectories(Boolean, String...)} method
//     */
//    public File[] listAllFilesAndDirectories() {
//
//        return listFiles(new File(getDirectoryPath()));
//    }
//
//    /**
//     * @param fileExtensions     file extensions to list from root directory
//     * @param includeDirectories include directories or not
//     * @return array of file, filtered by file extensions
//     * @apiNote This method used to list all files and directories from a directory recursively.
//     * <p>
//     * Consider we have a directory structure like this:
//     * <pre>
//     *         /dir1
//     *         /subdir1
//     *         /subdir2>file.txt
//     *         /dir2>subdir3>subdir4>file2.txt
//     *         </pre>
//     * <p>
//     * <pre>
//     *                 This method will return an array of file with the following order
//     *                 /dir1
//     *                 /subdir1
//     *                 /subdir2/file.txt
//     *                 /dir2/subdir3/subdir4/file2.txt
//     *                 </pre>
//     * <p>
//     * This method can be used to list only files with specific file extensions. For example, if you want to list only files with .txt extension, you can use this method like this:
//     * <pre>
//     *     listAllFilesAndDirectories(true, ".txt", ".docx", ".pdf");
//     *     </pre>
//     *
//     * For example, if you want to list only files with .txt extension, you can use this method like this:
//     * <pre>
//     *     listAllFilesAndDirectories(true, ".txt");
//     *     </pre>
//     *
//     *     In that case, this method will return only files with .txt extension, for example:
//     *     <pre>
//     *         /subdir2/file.txt
//     *         /dir2/subdir3/subdir4/file2.txt
//     *         </pre>
//     *
//     */
//    public File[] listAllFilesAndDirectories(Boolean includeDirectories, String... fileExtensions) {
//
//        File[] files = listAllFilesAndDirectories();
//
//        if (files == null) {
//            return null;
//        }
//
//        // if fileExtensions is null, return all files
//        if (fileExtensions == null) {
//            return files;
//        }
//
//        // if includeDirectories is false, filter only files
//        return filterFiles(files, fileExtensions, includeDirectories);
//
//    }
//
//    /**
//     * This method will list all the files from a directory recursively. No filtering will be applied.
//     * @return List of a file
//     */
//    public List<File> listAllFiles() {
//
//        File[] files = listAllFilesAndDirectories();
//
//        if (files == null) {
//            return new ArrayList<>();
//        }
//
//        // filter only files, use an inner process to filter files
//        List<File> fileList = new ArrayList<>();
//
//        for (File file : files) {
//            if (!file.isDirectory()) {
//                fileList.add(file);
//            }
//        }
//
//        return fileList;
//
//    }
//
//    /**
//     * This method used to list all files from a directory recursively
//     *
//     * @param dir directory to list files
//     * @return array of file. For example, consider we have a directory structure like this:
//     * <pre>
//     *        /dir1
//     *        /subdir1
//     *        /subdir2>file.txt
//     *        /dir2>subdir3>subdir4>file2.txt
//     * </pre>
//     * <p>
//     * <pre>
//     *        This method will return an array of file with the following order
//     *        /dir1
//     *        /subdir1
//     *        /subdir2/file.txt
//     *        /dir2/subdir3/subdir4/file2.txt
//     *        </pre>
//     */
//    @Nullable
//    private File[] listFiles(@NotNull File dir) {
//        // list all files first
//        File[] files = dir.listFiles();
//
//        if (files == null) {
//            return null;
//        }
//        // list all directories
//        File[] dirs = dir.listFiles((dir1, name) -> new File(dir1, name).isDirectory());
//
//        if (dirs == null) {
//            return null;
//        }
//        // iterate over directories and append all files
//        for (File subdir : dirs) {
//            File[] subFiles = listFiles(subdir);
//
//            if (subFiles == null) {
//                continue;
//            }
//            files = append(files, subFiles);
//        }
//
//        // check every file if it is readable and writable, if checkReadWrite is true
//        if (checkReadWrite) {
//            List<File> readableWritableFiles = new ArrayList<>();
//            for (File file : files) {
//                if (isFileReadableAndWritable(file)) {
//                    readableWritableFiles.add(file);
//                }
//            }
//            files = readableWritableFiles.toArray(new File[0]);
//        }
//
//        return files;
//    }
//
//    // This method appends two file arrays
//    private File[] append(File[] files, File[] subFiles) {
//
//        // create a new array to hold all files
//        File[] allFiles = new File[files.length + subFiles.length];
//        // copy files to allFiles
//        System.arraycopy(files, 0, allFiles, 0, files.length);
//        // copy subFiles to allFiles
//        System.arraycopy(subFiles, 0, allFiles, files.length, subFiles.length);
//
//
//
//        return allFiles;
//    }
//
//}

package javadev.stringcollections.textreplacor.io;

import javadev.stringcollections.textreplacor.console.ColoredConsoleOutput;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author nurujjamanpollob
 * @version 1.1.0
 * @apiNote Class that reads a directory, listing all subdirectories and files recursively.
 * @since 1.0.0
 */
@Getter
@SuppressWarnings("unused")
public class DirectoryReader {

    private final String directoryPath;
    @Setter
    private boolean checkReadWrite = false;

    /**
     * Constructor
     *
     * @param directoryPath directory path to read
     */
    public DirectoryReader(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    /**
     * Lists all files and directories from the base directory recursively.
     *
     * @return An array of `File` objects. Returns an empty array if the directory is empty or does not exist.
     */
    public File[] listAllFilesAndDirectories() {
        List<File> allFiles = new ArrayList<>();
        collectFilesRecursively(new File(directoryPath), allFiles, null, true);
        return allFiles.toArray(new File[0]);
    }

    /**
     * Lists all files and optionally directories from the base directory recursively, filtering by file extensions.
     *
     * @param includeDirectories `true` to include directories in the result, `false` otherwise.
     * @param fileExtensions     A varargs array of file extensions to include (e.g., ".txt", ".java"). If null or empty, all files are considered.
     * @return A filtered array of `File` objects.
     */
    public File[] listAllFilesAndDirectories(Boolean includeDirectories, String... fileExtensions) {
        List<File> allFiles = new ArrayList<>();
        List<String> extensions = (fileExtensions != null) ? Arrays.asList(fileExtensions) : null;
        collectFilesRecursively(new File(directoryPath), allFiles, extensions, includeDirectories);
        return allFiles.toArray(new File[0]);
    }

    /**
     * Lists all files (not directories) from the base directory recursively.
     *
     * @return A `List` of `File` objects.
     */
    public List<File> listAllFiles() {
        List<File> fileList = new ArrayList<>();
        collectFilesRecursively(new File(directoryPath), fileList, null, false);
        return fileList;
    }

    /**
     * Recursively traverses directories and collects files based on specified criteria.
     *
     * @param directory          The directory to start traversal from.
     * @param collectedFiles     The list to add collected files to.
     * @param extensions         A list of file extensions to filter by. If null, all files are matched.
     * @param includeDirectories If true, directories are added to the list.
     */
    private void collectFilesRecursively(@NotNull File directory, @NotNull List<File> collectedFiles,
                                         @Nullable List<String> extensions, boolean includeDirectories) {

        if (!directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (checkReadWrite && (!file.canRead() || !file.canWrite())) {
                continue;
            }

            if (file.isDirectory()) {
                if (includeDirectories) {
                    collectedFiles.add(file);
                }
                collectFilesRecursively(file, collectedFiles, extensions, includeDirectories);
            } else { // It's a file
                if (extensions == null || extensions.isEmpty() || matchesExtension(file.getName(), extensions)) {
                    collectedFiles.add(file);
                }
            }
        }
    }

    /**
     * Checks if a file name ends with any of the specified extensions.
     *
     * @param fileName   The name of the file.
     * @param extensions The list of extensions to check against.
     * @return `true` if the file name matches an extension, `false` otherwise.
     */
    private boolean matchesExtension(@NotNull String fileName, @NotNull List<String> extensions) {
        for (String extension : extensions) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method taht used to list all files and directories from a directory recursively, while ignoring a list of directories will be ignored.
     * For example this directory structure:
     * <pre>
     *     /dir1
     *     /subdir1
     *     /.git
     *     /.git/config
     *     /subdir2>file.txt
     *     /dir2>subdir3>subdir4>file2.txt
     * </pre>
     * <p>
     * If we ignore .git directory, then the result will be:
     * <pre>
     *     /dir1
     *     /subdir1
     *     /subdir2>file.txt
     *     /dir2>subdir3>subdir4>file2.txt
     * </pre>
     * <p>
     * <p>
     * Note: This method does not filter files by extensions. If you want to filter files by extensions, use {@link #listAllFilesAndDirectories(Boolean, String...)} method.
     * @param directoriesToIgnore List of directory names to ignore (e.g., ".git", "node_modules").
     * @return An array of `File` objects, excluding files in the ignored directories.
     */
    public List<File> listAllFilesAndDirectoriesIgnoring(List<String> directoriesToIgnore) {
        List<File> allFiles = new ArrayList<>();
        collectFilesRecursivelyIgnoring(new File(directoryPath), allFiles, directoriesToIgnore);
        return allFiles;
    }

    /**
     * Recursively traverses directories and collects files, ignoring specified directory names.
     *
     * @param directory           The directory to start traversal from.
     * @param collectedFiles      The list to add collected files to.
     * @param directoriesToIgnore A list of directory names to ignore.
     */
    /**
     * Recursively traverses directories and collects files, ignoring specified directory names.
     *
     * @param directory           The directory to start traversal from.
     * @param collectedFiles      The list to add collected files to.
     * @param directoriesToIgnore A list of directory names to ignore.
     */
    private void collectFilesRecursivelyIgnoring(@NotNull File directory, @NotNull List<File> collectedFiles, @Nullable List<String> directoriesToIgnore) {
        if (!directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (checkReadWrite && (!file.canRead() || !file.canWrite())) {
                continue;
            }

            if (file.isDirectory()) {
                // If the directory is in the ignore list, skip it entirely.
                if (directoriesToIgnore != null && directoriesToIgnore.contains(file.getName())) {
                    continue;
                }
                // Add the directory and recurse into it.
                collectedFiles.add(file);
                collectFilesRecursivelyIgnoring(file, collectedFiles, directoriesToIgnore);
            } else {
                // It's a file, so add it to the list.
                collectedFiles.add(file);
            }
        }
    }
}
