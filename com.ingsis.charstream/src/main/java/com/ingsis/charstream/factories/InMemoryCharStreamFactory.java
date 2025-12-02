/*
 * My Project
 */

package com.ingsis.charstream.factories;

import com.ingsis.charstream.StreamCharStream;
import com.ingsis.utils.metachar.MetaChar;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.peekableiterator.factories.PeekableIteratorFactory;
import java.io.InputStream;

public final class InMemoryCharStreamFactory implements PeekableIteratorFactory<MetaChar> {
    @Override
    public PeekableIterator<MetaChar> fromInputStream(InputStream in) {
        return new StreamCharStream(in);
    }
}
