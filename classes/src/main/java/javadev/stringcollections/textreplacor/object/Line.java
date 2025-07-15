package javadev.stringcollections.textreplacor.object;

import org.jetbrains.annotations.NotNull;

/**
 * @author nurujjamanpollob
 * @apiNote Represents a line in the text with its number, content, and the start and end indices of a search result.
 */
public record Line(int lineNumber, String lineContent, int startIndex, int endIndex) {
    /**
     * Constructor to initialize a Line object.
     *
     * @param lineNumber  the line number in the text
     * @param lineContent the content of the line
     * @param startIndex  the starting index of the search result in the line
     * @param endIndex    the ending index of the search result in the line
     */
    public Line {
    }

    @Override
    public @NotNull String toString() {
        return "Line{" +
                "lineNumber=" + lineNumber +
                ", lineContent='" + lineContent + '\'' +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                '}';
    }
}
