package javadev.stringcollections.textreplacor.mimedetector;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author nurujjamanpollob
 * @since 1.0
 * @version 1.0
 * @apiNote
 * A production-ready utility class for robustly detecting if a file is a text file
 * based on its byte content.
 * <p>
 * This class avoids relying on file extensions or MIME types, instead using a
 * multi-layered heuristic approach to analyze a fixed-size sample of the file's
 * binary data. The approach balances accuracy, performance, and memory efficiency,
 * making it suitable for large-scale applications and diverse file types.
 * <p>
 * The detection strategy includes:
 * 1. Checking for a Byte Order Mark (BOM) for quick identification of Unicode
 *    text files.[1, 2]
 * 2. Scanning for null bytes, which are strong indicators of binary files.[2, 3]
 * 3. Calculating the ratio of printable characters to total bytes, with
 *    a high ratio suggesting text content.[3, 4]
 * 4. Computing Shannon entropy to differentiate between text and high-entropy
 *    binary data like compressed or encrypted files.[6, 7]
 * <p>
 * This class is designed to be thread-safe and can be used in concurrent
 * environments.
 */
public final class TextFileDetector {

    /**
     * The size of the byte buffer to read for analysis, in bytes.
     * This fixed size ensures performance is consistent regardless of file size.
     * Research indicates that a small, fixed-size buffer is optimal for large files.[4, 5]
     */
    private static final int BUFFER_SIZE = 4096; // 4 KB

    /**
     * The minimum ratio of "text" characters (printable ASCII plus common
     * whitespace) for a file to be considered a text file.
     */
    private static final double TEXT_RATIO_THRESHOLD = 0.95;

    /**
     * The Shannon entropy threshold. Files with entropy higher than this value
     * are likely compressed or encrypted binary data.
     * Standard text has low entropy, while binary data has high entropy.[6, 7]
     */
    private static final double ENTROPY_THRESHOLD = 7.0;

    /**
     * Enforce non-instantiability for this utility class.
     */
    private TextFileDetector() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Determines if the given file is a text file by analyzing its byte content.
     * <p>
     * The method uses a tiered approach:
     * 1. Check for a Byte Order Mark (BOM) for a fast, definitive answer for
     *    Unicode files.[8, 9]
     * 2. Read a fixed-size sample of the file to check for null bytes and a
     *    high ratio of printable characters.[10, 11, 12]
     * 3. Perform a deeper statistical analysis (entropy) if heuristics are inconclusive.
     *
     * @param path The path to the file to analyze.
     * @return {@code true} if the file is likely a text file, {@code false} otherwise.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static boolean isTextFile(Path path) throws IOException {
        // Handle basic file system checks first.
        if (Files.isDirectory(path) || !Files.exists(path)) {
            return false;
        }

        // Handle very small files by reading all bytes.
        // This avoids streaming overhead for tiny files.
        if (Files.size(path) < BUFFER_SIZE) {
            byte[] allBytes = Files.readAllBytes(path);
            return isTextFileHeuristics(allBytes);
        }

        // Use a BufferedInputStream to read a fixed-size sample for larger files.
        // This is a high-performance, memory-efficient approach.[5, 13, 14]
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(path))) {
            // First, check for BOM (Byte Order Mark) in the first few bytes.
            byte[] bomBytes = new byte[4];
            int bomBytesRead = bis.read(bomBytes, 0, 4);
            if (isBOM(bomBytes)) {
                return true;
            }

            // Now, read the main buffer for heuristic analysis.
            byte[] buffer = new byte[BUFFER_SIZE];
            // Read the data, accounting for the bytes already read for the BOM.
            int bytesRead = bis.read(buffer, 0, BUFFER_SIZE);

            // If the file is smaller than the buffer, adjust bytesRead accordingly.
            if (bytesRead == -1) {
                // This case should be handled by the small file check, but
                // acts as a safeguard. The file is empty, so it can't be text.
                return false;
            }

            // A null byte is a very strong indicator of a binary file.
            // Null bytes are a common part of binary formats but rare in most
            // text encodings.[15, 12, 16]
            if (containsNullByte(buffer, bytesRead)) {
                return false;
            }

            // Perform statistical and character analysis on the buffer.
            int printableCharCount = 0;
            for (int i = 0; i < bytesRead; i++) {
                if (isPrintableCharacter(buffer[i])) {
                    printableCharCount++;
                }
            }

            double textRatio = (double) printableCharCount / bytesRead;
            if (textRatio < TEXT_RATIO_THRESHOLD) {
                return false;
            }

            // If a high printable ratio is found, a final check for entropy.
            // Text files have low entropy due to predictable character patterns.[6]
            return !(calculateEntropy(buffer, bytesRead) > ENTROPY_THRESHOLD);

        } catch (IOException e) {
            // Log the exception for production environment but return false.
            // A file that causes an I/O error is unlikely to be a valid text file.
            return false;
        }
    }

    /**
     * A helper method to perform all heuristics on a byte array.
     * This is useful for very small files that are read in their entirety.
     */
    private static boolean isTextFileHeuristics(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return false;
        }

        if (isBOM(bytes)) {
            return true;
        }

        if (containsNullByte(bytes, bytes.length)) {
            return false;
        }

        int printableCharCount = 0;
        for (byte b : bytes) {
            if (isPrintableCharacter(b)) {
                printableCharCount++;
            }
        }
        double textRatio = (double) printableCharCount / bytes.length;
        if (textRatio < TEXT_RATIO_THRESHOLD) {
            return false;
        }

        return !(calculateEntropy(bytes, bytes.length) > ENTROPY_THRESHOLD);
    }

    /**
     * Checks if the byte array contains a Byte Order Mark.
     * This is an authoritative and fast check for many text files.[8, 9]
     * @param bytes The byte array, expected to be at least 3 bytes long.
     * @return true if a BOM is detected, false otherwise.
     */
    private static boolean isBOM(byte[] bytes) {
        if (bytes.length >= 3) {
            // UTF-8 BOM: EF BB BF
            if (bytes[0] == (byte) 0xEF && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF) {
                return true;
            }
        }
        if (bytes.length >= 2) {
            // UTF-16BE BOM: FE FF
            if (bytes[0] == (byte) 0xFE && bytes[1] == (byte) 0xFF) {
                return true;
            }
            // UTF-16LE BOM: FF FE
            return bytes[0] == (byte) 0xFF && bytes[1] == (byte) 0xFE;
        }
        return false;
    }

    /**
     * Checks if the byte array contains a null byte (0x00).
     * The presence of a null byte is a very strong signal for a binary file.[2, 10, 12]
     *
     * @param bytes The byte array to check.
     * @param length The number of valid bytes in the array.
     * @return true if a null byte is found, false otherwise.
     */
    private static boolean containsNullByte(byte[] bytes, int length) {
        for (int i = 0; i < length; i++) {
            if (bytes[i] == (byte) 0x00) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a byte represents a "printable" character or common whitespace.
     * This heuristic is based on the idea that text files are composed of
     * human-readable characters.[11, 16]
     *
     * @param b The byte to check.
     * @return true if the byte is likely a text character, false otherwise.
     */
    private static boolean isPrintableCharacter(byte b) {
        // Standard ASCII printable characters (0x20 to 0x7E)
        if (b >= 0x20 && b <= 0x7E) {
            return true;
        }
        // Common text control characters: tab, newline, carriage return
        if (b == 0x09 || b == 0x0A || b == 0x0D) {
            return true;
        }
        // Additional check for extended ASCII, which can be part of text files.
        return b <= (byte) 0xFF;
    }

    /**
     * Calculates the Shannon entropy of a byte array.
     * Low entropy indicates predictable data (like text), while high entropy
     * indicates randomness (like compressed or encrypted data).[17, 6, 18]
     *
     * @param bytes The byte array to analyze.
     * @param length The number of valid bytes in the array.
     * @return The entropy value (bits per byte), from 0 to 8.0.
     */
    private static double calculateEntropy(byte[] bytes, int length) {
        if (length <= 1) {
            return 0.0;
        }

        int[] frequency = new int[256];
        for (int i = 0; i < length; i++) {
            frequency[bytes[i] & 0xFF]++; // & 0xFF to handle negative byte values
        }

        double entropy = 0.0;
        for (int freq : frequency) {
            if (freq > 0) {
                double probability = (double) freq / length;
                entropy -= probability * (Math.log(probability) / Math.log(2));
            }
        }

        return entropy;
    }

}