/*
 * My Project
 */

package com.ingsis.charstream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.metachar.MetaChar;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class FromFileCharStreamTest {
    private Path tempFile;
    private FromFileCharStream stream;

    @BeforeEach
    void setUp() throws Exception {
        tempFile = Files.createTempFile("fromfilestream-test", ".txt");
        Files.writeString(tempFile, "a\nb");
        stream = new FromFileCharStream(tempFile);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (stream != null) {
            stream.close();
        }
        if (tempFile != null) {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void readsAllCharsInOrderWithCorrectMeta() {
        List<MetaChar> seen = new ArrayList<>();
        while (stream.hasNext()) {
            seen.add(stream.next());
        }
        assertEquals(3, seen.size());
        assertEquals(Character.valueOf('a'), seen.get(0).character());
        assertTrue(seen.get(0).line() >= 0);
        assertTrue(seen.get(0).column() >= 0);

        assertEquals(Character.valueOf('\n'), seen.get(1).character());
        assertEquals(
                seen.get(0).line(),
                seen.get(1).line(),
                "newline should be on same line as previous char");
        assertEquals(
                Integer.valueOf(seen.get(0).column() + 1),
                seen.get(1).column(),
                "column should increment for next char");

        assertEquals(Character.valueOf('b'), seen.get(2).character());
        assertEquals(
                Integer.valueOf(seen.get(0).line() + 1),
                seen.get(2).line(),
                "line should increment after newline");
        assertEquals(
                Integer.valueOf(0),
                seen.get(2).column(),
                "column should be reset after newline (implementation uses 0)");
    }

    @Test
    void peekDoesNotConsumeAndHasNextWorks() {
        MetaChar p = stream.peek();
        assertEquals(Character.valueOf('a'), p.character());
        assertTrue(stream.hasNext());
        MetaChar n = stream.next();
        assertEquals(p, n);
    }

    @Test
    void afterDrainingHasNextFalseAndPeekNextThrow() {
        while (stream.hasNext()) {
            stream.next();
        }

        assertFalse(stream.hasNext());
        assertThrows(NoSuchElementException.class, () -> stream.peek());
        assertThrows(NoSuchElementException.class, () -> stream.next());
    }
}
