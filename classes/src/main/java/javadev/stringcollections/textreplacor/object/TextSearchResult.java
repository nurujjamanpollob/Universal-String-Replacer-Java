package javadev.stringcollections.textreplacor.object;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author nurujjamanpollob
 * @version 1.0
 * @apiNote Class that represents the result of a text search operation. Contain search result for a single file. For each line information see {@link Line}
 * @since 1.0
 */
public record TextSearchResult(Line[] lines, File file) {

    /**
     * Constructor to initialize a TextSearchResult object.
     *
     * @param lines an array of Line objects representing the search results
     * @param file  the file in which the search was performed
     */
    public TextSearchResult {
    }

    /**
     * Returns the lines containing search results.
     *
     * @return an array of Line objects, each representing a line with search results
     */
    @Override
    public Line[] lines() {
        return lines;
    }

    /**
     * Returns the file in which the search was performed.
     *
     * @return the File object representing the file
     */
    @Override
    public File file() {
        return file;
    }

    @Override
    public @NotNull String toString() {
        StringBuilder sb = new StringBuilder("TextSearchResult{");
        sb.append("file=").append(file.getAbsolutePath()).append(", lines=[");
        for (Line line : lines) {
            // if not last line, append a comma
            sb.append(line.toString());
            if (line != lines[lines.length - 1]) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }
}
