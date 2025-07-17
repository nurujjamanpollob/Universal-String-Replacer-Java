package testpackage;

import javadev.stringcollections.textreplacor.io.json.JSONObjectUtility;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JSONObjectUtilityTest {

    @Test
    void testNullInput() {
        assertNull(JSONObjectUtility.escapeSpecialCharacters(null));
    }

    @Test
    void testEmptyString() {
        assertEquals("", JSONObjectUtility.escapeSpecialCharacters(""));
    }

    @Test
    void testNoSpecialCharacters() {
        assertEquals("Hello World 123", JSONObjectUtility.escapeSpecialCharacters("Hello World 123"));
    }

    @Test
    void testBackslash() {
        assertEquals("\\\\", JSONObjectUtility.escapeSpecialCharacters("\\"));
    }

    @Test
    void testDoubleQuote() {
        assertEquals("\\\"", JSONObjectUtility.escapeSpecialCharacters("\""));
    }

    @Test
    void testBackspace() {
        assertEquals("\\b", JSONObjectUtility.escapeSpecialCharacters("\b"));
    }

    @Test
    void testFormFeed() {
        assertEquals("\\f", JSONObjectUtility.escapeSpecialCharacters("\f"));
    }

    @Test
    void testNewline() {
        assertEquals("\\n", JSONObjectUtility.escapeSpecialCharacters("\n"));
    }

    @Test
    void testCarriageReturn() {
        assertEquals("\\r", JSONObjectUtility.escapeSpecialCharacters("\r"));
    }

    @Test
    void testTab() {
        assertEquals("\\t", JSONObjectUtility.escapeSpecialCharacters("\t"));
    }

    @Test
    void testAllControlCharacters() {
        StringBuilder input = new StringBuilder();
        StringBuilder expected = new StringBuilder();
        for (char c = 0; c < 0x20; c++) {
            input.append(c);
            switch (c) {
                case '\\': expected.append("\\\\"); break;
                case '"': expected.append("\\\""); break;
                case '\b': expected.append("\\b"); break;
                case '\f': expected.append("\\f"); break;
                case '\n': expected.append("\\n"); break;
                case '\r': expected.append("\\r"); break;
                case '\t': expected.append("\\t"); break;
                default: expected.append(String.format("\\u%04x", (int) c));
            }
        }
        assertEquals(expected.toString(), JSONObjectUtility.escapeSpecialCharacters(input.toString()));
    }

    @Test
    void testUnicodeCharacters() {
        String input = "こんにちは世界🌍";
        assertEquals(input, JSONObjectUtility.escapeSpecialCharacters(input));
    }

    @Test
    void testPrintableAndNonPrintableMix() {
        String input = "A\u0003B";
        String expected = "A\\u0003B";
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }

    @Test
    void testConsecutiveSpecialCharacters() {
        String input = "\n\n\t\t";
        String expected = "\\n\\n\\t\\t";
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }

    @Test
    void testMixedSpecialCharacters() {
        String input = "A\"B\\C\nD\tE\bF\fG\rH";
        String expected = "A\\\"B\\\\C\\nD\\tE\\bF\\fG\\rH";
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }

    @Test
    void testStringWithAllEscapableCharacters() {
        String input = "\"\\\b\f\n\r\t";
        String expected = "\\\"\\\\\\b\\f\\n\\r\\t";
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }

    @Test
    void testStringWithUnicodeCharacters() {
        String input = "Hello \uD83D\uDE00 World"; // Unicode smiley face
        String expected = "Hello \uD83D\uDE00 World"; // Should remain unchanged
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }
    @Test
    void testStringWithMixedContent() {
        String input = "Text with special chars: \n\t\"\\ and unicode \u00A9";
        String expected = "Text with special chars: \\n\\t\\\"\\\\ and unicode \u00A9"; // Unicode © should remain unchanged
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }
    @Test
    void testStringWithOnlySpecialCharacters() {
        String input = "\\\"\\b\\f\\n\\r\\t";
        String expected = "\\\\\\\"\\\\b\\\\f\\\\n\\\\r\\\\t"; // All characters should be escaped
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }
    @Test
    void testStringWithLongUnicode() {
        String input = "Long unicode: \uD83D\uDE00\uD83D\uDE01\uD83D\uDE02"; // Multiple unicode characters
        String expected = "Long unicode: \uD83D\uDE00\uD83D\uDE01\uD83D\uDE02"; // Should remain unchanged
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }
    @Test
    void testStringWithMultipleBackslashes() {
        String input = "Multiple backslashes: \\\\\\";
        String expected = "Multiple backslashes: \\\\\\\\\\\\"; // Each backslash should be escaped
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }
    @Test
    void testStringWithMixedControlCharacters() {
        String input = "Control chars: \b\f\n\r\t\\\"";
        String expected = "Control chars: \\b\\f\\n\\r\\t\\\\\\\""; // All control characters should be escaped
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }
    @Test
    void testStringWithNoControlCharacters() {
        String input = "Just a normal string with no control characters.";
        String expected = "Just a normal string with no control characters."; // Should remain unchanged
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }
    @Test
    void testStringWithOnlyControlCharacters() {
        String input = "\b\f\n\r\t";
        String expected = "\\b\\f\\n\\r\\t"; // All control characters should be escaped
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }
    @Test
    void testStringWithSpecialCharactersAndUnicode() {
        String input = "Special chars: \b\f\n\r\t and unicode: \u00A9";
        String expected = "Special chars: \\b\\f\\n\\r\\t and unicode: \u00A9"; // Unicode © should remain unchanged
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }
    @Test
    void testStringWithEscapedCharacters() {
        String input = "Escaped characters: \\\"\\b\\f\\n\\r\\t";
        String expected = "Escaped characters: \\\\\\\"\\\\b\\\\f\\\\n\\\\r\\\\t"; // All characters should be escaped
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }
    @Test
    void testStringWithComplexUnicode() {
        String input = "Complex unicode: \uD83D\uDE00\uD83D\uDE01\uD83D\uDE02 and text";
        String expected = "Complex unicode: \uD83D\uDE00\uD83D\uDE01\uD83D\uDE02 and text"; // Should remain unchanged
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }
    @Test
    void testDoubleQuotesAndBackslashes() {
        String input = "\"Hello \\ World\"";
        String expected = "\\\"Hello \\\\ World\\\""; // Double quotes and backslashes should be escaped
        assertEquals(expected, JSONObjectUtility.escapeSpecialCharacters(input));
    }
}