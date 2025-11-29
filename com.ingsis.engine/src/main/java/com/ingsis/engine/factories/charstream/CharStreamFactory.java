/*
 * My Project
 */

package com.ingsis.engine.factories.charstream;

import com.ingsis.utils.metachar.MetaChar;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface CharStreamFactory {

    PeekableIterator<MetaChar> fromInputStream(InputStream in) throws IOException;

    PeekableIterator<MetaChar> fromFile(Path path) throws IOException;

    PeekableIterator<MetaChar> fromString(CharSequence input) throws IOException;
}
