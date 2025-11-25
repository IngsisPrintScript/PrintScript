/*
 * My Project
 */

package com.ingsis.engine.factories.charstream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ingsis.metachar.MetaChar;
import com.ingsis.peekableiterator.PeekableIterator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultCharStreamFactoryTest {
    private DefaultCharStreamFactory factory;

    @BeforeEach
    void setup() {
        factory = new DefaultCharStreamFactory();
    }

    @Test
    void inMemoryCharIteratorReturnsIterator() {
        Queue<Character> buffer = new ArrayDeque<>();
        buffer.add('a');
        PeekableIterator<MetaChar> it = factory.inMemoryCharIterator(buffer);
        assertNotNull(it);
        assertTrue(it.hasNext());
        MetaChar mc = it.next();
        assertEquals(Character.valueOf('a'), mc.character());
    }

    @Test
    void fromFileCharIteratorReadsFile() throws IOException {
        Path tmp = Files.createTempFile("ps-test", ".txt");
        Files.writeString(tmp, "z");
        PeekableIterator<MetaChar> it = factory.fromFileCharIterator(tmp);
        assertNotNull(it);
        assertTrue(it.hasNext());
        MetaChar mc = it.next();
        assertEquals(Character.valueOf('z'), mc.character());
        Files.deleteIfExists(tmp);
    }
}
