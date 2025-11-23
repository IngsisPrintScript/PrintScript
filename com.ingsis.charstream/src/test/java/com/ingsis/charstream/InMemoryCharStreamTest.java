/*
 * My Project
 */

package com.ingsis.charstream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.metachar.MetaChar;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class InMemoryCharStreamTest {
    private InMemoryCharStream stream;

    @BeforeEach
    void setUp() {
        stream = new InMemoryCharStream(new LinkedList<>());
    }

    private static Queue<Character> charsFromString(String s) {
        Queue<Character> q = new LinkedList<>();
        for (char c : s.toCharArray()) {
            q.add(c);
        }
        return q;
    }

    @Test
    void peekDoesNotConsume() {
        stream = new InMemoryCharStream(charsFromString("ab"));

        MetaChar peeked = stream.peek();

        assertEquals(Character.valueOf('a'), peeked.character());
        assertEquals(Integer.valueOf(1), peeked.line());
        assertEquals(Integer.valueOf(1), peeked.column());

        assertTrue(stream.hasNext());

        MetaChar next = stream.next();
        assertEquals(peeked, next);
    }

    @Test
    void nextUpdatesLineAndColumnAroundNewline() {
        stream = new InMemoryCharStream(charsFromString("a\nb"));

        MetaChar first = stream.next();
        assertEquals(Character.valueOf('a'), first.character());
        assertTrue(first.line() >= 0);
        assertTrue(first.column() >= 0);

        MetaChar second = stream.next();
        assertEquals(Character.valueOf('\n'), second.character());
        assertEquals(
                first.line(), second.line(), "newline should be on same line as previous char");
        assertEquals(
                Integer.valueOf(first.column() + 1),
                second.column(),
                "column should increment for next char");

        MetaChar third = stream.next();
        assertEquals(Character.valueOf('b'), third.character());
        assertEquals(
                Integer.valueOf(first.line() + 1),
                third.line(),
                "line should increment after newline");
        assertEquals(
                Integer.valueOf(0),
                third.column(),
                "column should be reset after newline (implementation uses 0)");
    }

    @Test
    void emptyStreamHasNoNextAndThrowsOnPeekOrNext() {
        assertFalse(stream.hasNext());

        assertThrows(NoSuchElementException.class, () -> stream.peek());
        assertThrows(NoSuchElementException.class, () -> stream.next());
    }

    @Test
    void addCharAndAddCharsAppendData() {
        stream.addChar('x');
        stream.addChars(charsFromString("yz"));

        MetaChar m1 = stream.next();
        MetaChar m2 = stream.next();
        MetaChar m3 = stream.next();

        assertEquals(Character.valueOf('x'), m1.character());
        assertEquals(Character.valueOf('y'), m2.character());
        assertEquals(Character.valueOf('z'), m3.character());
    }
}
