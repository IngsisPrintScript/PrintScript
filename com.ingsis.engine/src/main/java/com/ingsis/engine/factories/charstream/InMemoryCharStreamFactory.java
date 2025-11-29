/*
 * My Project
 */

package com.ingsis.engine.factories.charstream;

import com.ingsis.charstream.StreamCharStream;
import com.ingsis.utils.metachar.MetaChar;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public final class InMemoryCharStreamFactory implements CharStreamFactory {

    @Override
    public PeekableIterator<MetaChar> fromInputStream(InputStream in) throws IOException {
        return new StreamCharStream(in);
    }

    @Override
    public PeekableIterator<MetaChar> fromFile(Path path) throws IOException {
        return new StreamCharStream(new FileInputStream(path.toFile()));
    }

    @Override
    public PeekableIterator<MetaChar> fromString(CharSequence input) throws IOException {
        byte[] bytes = input.toString().getBytes();
        return new StreamCharStream(new ByteArrayInputStream(bytes));
    }
}
