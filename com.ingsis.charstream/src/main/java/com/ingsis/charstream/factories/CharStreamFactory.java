/*
 * My Project
 */

package com.ingsis.charstream.factories;

import com.ingsis.peekableiterator.PeekableIterator;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public interface CharStreamFactory {
    PeekableIterator<Character> inMemoryCharIterator(Queue<Character> buffer);

    PeekableIterator<Character> fromFileCharIterator(Path path) throws IOException;
}
