package javadev.stringcollections.textreplacor.io;






import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/**
 * Class that contains file delete related methods.
 * <p>
 * This class is used to delete a file from disk.
 */
public class FileDelete {

    /**
     * Delete a file after a given time
     * @param filePath file path
     * @param deleteAfterTime delete after time
     * @param timeUnit time unit
     * @throws IOException if file not found or deleting the file is failed
     */
    public static void deleteFileAfterTime(String filePath, long deleteAfterTime, TimeUnit timeUnit) throws IOException {
        java.io.File file = new java.io.File(filePath);

        String unitName = timeUnit == TimeUnit.DAYS ? "days" : timeUnit == TimeUnit.HOURS ? "hours" : timeUnit == TimeUnit.MINUTES ? "minutes" : timeUnit == TimeUnit.SECONDS ? "seconds" : timeUnit == TimeUnit.MILLISECONDS ? "milliseconds" : "unknown";

        if (file.exists()) {
            java.util.concurrent.Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                try {
                    java.nio.file.Files.deleteIfExists(file.toPath());
                } catch (IOException ignored) {

                }
            }, deleteAfterTime, timeUnit);
        } else {
            throw new IOException("File not found");
        }

    }

    /**
     * This method used to wipe a directory with all files and subdirectories
     *
     * @param directoryPath directory path
     * @throws IOException if an error occurs
     * @since 1.0.0
     */
    public static void wipeDirectory(String directoryPath) throws IOException {

        if (directoryPath == null) {
            throw new IOException("Directory path is null");
        }

        java.io.File directory = new java.io.File(directoryPath);

        if (!directory.exists()) {
            throw new IOException("Directory does not exists at " + directoryPath);
        }

        if (!directory.isDirectory()) {
            throw new IOException("Path is not a directory at " + directoryPath);
        }

        java.io.File[] files = new DirectoryReader(directoryPath).listAllFilesAndDirectories();

        // we need to sort by the longest path first, to avoid io errors
        java.util.Arrays.sort(files, (f1, f2) -> f2.getAbsolutePath().length() - f1.getAbsolutePath().length());

       // Delete files First
        for (java.io.File file : files) {
            if (file.isFile()) {
                java.nio.file.Files.deleteIfExists(file.toPath());
            }
        }

        // Delete directories
        for (java.io.File file : files) {
            if (file.isDirectory()) {
                java.nio.file.Files.deleteIfExists(file.toPath());
            }
        }

        // Clean the directory
        java.nio.file.Files.deleteIfExists(Path.of(directoryPath));



    }

    /**
     * This method used to wipe a directory with all files and subdirectories, with a exclusion list
     * @param directoryPath directory path
     * @param exclusionList exclusion list
     * @throws IOException if an error occurs
     */
    public static void wipeDirectory(String directoryPath, String... exclusionList) throws IOException {

        if (directoryPath == null) {
            throw new IOException("Directory path is null");
        }

        java.io.File directory = new java.io.File(directoryPath);

        if (!directory.exists()) {
            throw new IOException("Directory does not exists at " + directoryPath);
        }

        if (!directory.isDirectory()) {
            throw new IOException("Path is not a directory at " + directoryPath);
        }

        java.io.File[] files = new DirectoryReader(directoryPath).listAllFilesAndDirectories();

        // we need to sort by the longest path first, to avoid io errors
        java.util.Arrays.sort(files, (f1, f2) -> f2.getAbsolutePath().length() - f1.getAbsolutePath().length());

        // Delete files First
        for (java.io.File file : files) {
            if (file.isFile()) {
                boolean isExcluded = false;
                for (String exclusion : exclusionList) {
                    if (file.getAbsolutePath().contains(exclusion)) {
                        isExcluded = true;
                        break;
                    }
                }
                if (!isExcluded) {
                    java.nio.file.Files.deleteIfExists(file.toPath());
                }
            }
        }

        // Delete directories
        for (java.io.File file : files) {
            if (file.isDirectory()) {
                boolean isExcluded = false;
                for (String exclusion : exclusionList) {
                    if (file.getAbsolutePath().contains(exclusion)) {
                        isExcluded = true;
                        break;
                    }
                }
                if (!isExcluded) {
                    java.nio.file.Files.deleteIfExists(file.toPath());
                }
            }
        }

        // Clean the directory
        java.nio.file.Files.deleteIfExists(Path.of(directoryPath));
    }
}
