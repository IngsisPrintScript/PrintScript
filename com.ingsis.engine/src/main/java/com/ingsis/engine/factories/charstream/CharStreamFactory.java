/*
 * My Project
 */

package com.ingsis.engine.factories.charstream;

import com.ingsis.metachar.MetaChar;
import com.ingsis.peekableiterator.PeekableIterator;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public interface CharStreamFactory {
    PeekableIterator<MetaChar> inMemoryCharIterator(Queue<Character> buffer);

    PeekableIterator<MetaChar> fromFileCharIterator(Path path) throws IOException;
}
