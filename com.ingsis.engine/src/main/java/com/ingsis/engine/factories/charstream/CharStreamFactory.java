/*
 * My Project
 */

package com.ingsis.engine.factories.charstream;

import com.ingsis.utils.metachar.MetaChar;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import java.io.InputStream;

public interface CharStreamFactory {
    PeekableIterator<MetaChar> fromInputStream(InputStream in);
}
