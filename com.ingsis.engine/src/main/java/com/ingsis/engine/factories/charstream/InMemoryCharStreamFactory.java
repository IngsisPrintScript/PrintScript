/*
 * My Project
 */

package com.ingsis.engine.factories.charstream;

import com.ingsis.charstream.StreamCharStream;
import com.ingsis.utils.metachar.MetaChar;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import java.io.InputStream;

public final class InMemoryCharStreamFactory implements CharStreamFactory {
    @Override
    public PeekableIterator<MetaChar> fromInputStream(InputStream in) {
        return new StreamCharStream(in);
    }
}
