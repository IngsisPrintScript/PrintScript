/*
 * My Project
 */

package com.ingsis.engine.factories.charstream;

import com.ingsis.charstream.FromFileCharStream;
import com.ingsis.charstream.InMemoryCharStream;
import com.ingsis.metachar.MetaChar;
import com.ingsis.peekableiterator.PeekableIterator;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Queue;

public final class DefaultCharStreamFactory implements CharStreamFactory {
    @Override
    public PeekableIterator<MetaChar> inMemoryCharIterator(Queue<Character> buffer) {
        return new InMemoryCharStream(buffer);
    }

    @Override
    public PeekableIterator<MetaChar> fromFileCharIterator(Path path) throws IOException {
        return new FromFileCharStream(path);
    }
}
