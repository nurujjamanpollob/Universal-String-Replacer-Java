package javadev.stringcollections.textreplacor.io;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nurujjamanpollob
 * @version 1.0.0
 * @apiNote Class that read directory, list all subdirectories and files.
 * @since 1.0.0
 */
@Getter
@SuppressWarnings("unused")
public class DirectoryReader {

    /**
     * Suppress default constructor for noninstantiability
     */
    private DirectoryReader() {
        throw new AssertionError("No " + this.getClass().getName() + " instances for you!");
    }

    private final String directoryPath;

    /**
     * Constructor
     *
     * @param directoryPath directory path to read
     */
    public DirectoryReader(String directoryPath) {
        this.directoryPath = directoryPath;
    }


    /**
     * This method used to filter files by file extensions
     *
     * @param files              files to filter
     * @param s                  file extensions to filter
     * @param includeDirectories include directories or not
     * @return filtered files
     */
    private File[] filterFiles(@NotNull File[] files, @NotNull String[] s, boolean includeDirectories) {

        // create a new array to hold filtered files
        File[] filteredFiles = new File[files.length];
        // create a counter to count filtered files
        int counter = 0;
        // iterate over files
        for (File file : files) {
            // if file is a directory and includeDirectories is false, skip this file
            if (file.isDirectory() && !includeDirectories) {
                continue;
            }
            // if file is a directory and includeDirectories is true, add this file to filteredFiles
            if (file.isDirectory() && includeDirectories) {
                filteredFiles[counter++] = file;
                continue;
            }
            // if file is not a directory, check if file extension is in s
            for (String value : s) {
                // if file extension is in s, add this file to filteredFiles
                if (file.getName().endsWith(value)) {
                    filteredFiles[counter++] = file;
                    break;
                }
            }
        }
        // create a new array to hold filtered files
        File[] filteredFiles2 = new File[counter];
        // copy filteredFiles to filteredFiles2
        System.arraycopy(filteredFiles, 0, filteredFiles2, 0, counter);
        return filteredFiles2;

    }

    /**
     * @return array of file
     * @apiNote This method used to list all files and directories from a directory recursively.
     * <p>
     * Consider we have a directory structure like this:
     * <pre>
     *     /dir1
     *     /subdir1
     *     /subdir2>file.txt
     *     /dir2>subdir3>subdir4>file2.txt
     *     </pre>
     * <p>
     * <pre>
     *             This method will return an array of file with the following order
     *             /dir1
     *             /subdir1
     *             /subdir2/file.txt
     *             /dir2/subdir3/subdir4/file2.txt
     *             </pre>
     * <p>
     * If you want to list only files with specific file extensions, use {@link #listAllFilesAndDirectories(Boolean, String...)} method
     */
    public File[] listAllFilesAndDirectories() {

        return listFiles(new File(getDirectoryPath()));
    }

    /**
     * @param fileExtensions     file extensions to list from root directory
     * @param includeDirectories include directories or not
     * @return array of file, filtered by file extensions
     * @apiNote This method used to list all files and directories from a directory recursively.
     * <p>
     * Consider we have a directory structure like this:
     * <pre>
     *         /dir1
     *         /subdir1
     *         /subdir2>file.txt
     *         /dir2>subdir3>subdir4>file2.txt
     *         </pre>
     * <p>
     * <pre>
     *                 This method will return an array of file with the following order
     *                 /dir1
     *                 /subdir1
     *                 /subdir2/file.txt
     *                 /dir2/subdir3/subdir4/file2.txt
     *                 </pre>
     * <p>
     * This method can be used to list only files with specific file extensions. For example, if you want to list only files with .txt extension, you can use this method like this:
     * <pre>
     *     listAllFilesAndDirectories(true, ".txt", ".docx", ".pdf");
     *     </pre>
     *
     * For example, if you want to list only files with .txt extension, you can use this method like this:
     * <pre>
     *     listAllFilesAndDirectories(true, ".txt");
     *     </pre>
     *
     *     In that case, this method will return only files with .txt extension, for example:
     *     <pre>
     *         /subdir2/file.txt
     *         /dir2/subdir3/subdir4/file2.txt
     *         </pre>
     *
     */
    public File[] listAllFilesAndDirectories(Boolean includeDirectories, String... fileExtensions) {

        File[] files = listAllFilesAndDirectories();

        if (files == null) {
            return null;
        }

        // if fileExtensions is null, return all files
        if (fileExtensions == null) {
            return files;
        }

        // if includeDirectories is false, filter only files
        return filterFiles(files, fileExtensions, includeDirectories);

    }

    /**
     * This method will list all the files from a directory recursively. No filtering will be applied.
     * @return List of a file
     */
    public List<File> listAllFiles() {

        File[] files = listAllFilesAndDirectories();

        if (files == null) {
            return new ArrayList<>();
        }

        // filter only files, use an inner process to filter files
        List<File> fileList = new ArrayList<>();

        for (File file : files) {
            if (!file.isDirectory()) {
                fileList.add(file);
            }
        }

        return fileList;

    }

    /**
     * This method used to list all files from a directory recursively
     *
     * @param dir directory to list files
     * @return array of file. For example, consider we have a directory structure like this:
     * <pre>
     *        /dir1
     *        /subdir1
     *        /subdir2>file.txt
     *        /dir2>subdir3>subdir4>file2.txt
     * </pre>
     * <p>
     * <pre>
     *        This method will return an array of file with the following order
     *        /dir1
     *        /subdir1
     *        /subdir2/file.txt
     *        /dir2/subdir3/subdir4/file2.txt
     *        </pre>
     */
    @Nullable
    private File[] listFiles(@NotNull File dir) {
        // list all files first
        File[] files = dir.listFiles();

        if (files == null) {
            return null;
        }
        // list all directories
        File[] dirs = dir.listFiles((dir1, name) -> new File(dir1, name).isDirectory());

        if (dirs == null) {
            return null;
        }
        // iterate over directories and append all files
        for (File subdir : dirs) {
            File[] subFiles = listFiles(subdir);

            if
            (subFiles == null) {
                continue;
            }
            files = append(files, subFiles);
        }
        return files;
    }

    // This method appends two file arrays
    private File[] append(File[] files, File[] subFiles) {

        // create a new array to hold all files
        File[] allFiles = new File[files.length + subFiles.length];
        // copy files to allFiles
        System.arraycopy(files, 0, allFiles, 0, files.length);
        // copy subFiles to allFiles
        System.arraycopy(subFiles, 0, allFiles, files.length, subFiles.length);
        return allFiles;
    }

}
