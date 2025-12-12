/*
 * My Project
 */

package com.ingsis.charstream.factories;

import com.ingsis.charstream.CharStream;
import com.ingsis.charstream.LoggerCharStream;
import com.ingsis.charstream.source.CharSource;
import com.ingsis.charstream.source.MappedCharSource;
import com.ingsis.charstream.source.MemoryCharSource;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.metachar.MetaChar;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class CharStreamFactory implements SafeIteratorFactory<MetaChar> {
    private final IterationResultFactory iterationResultFactory;
    private static final int SMALL_THRESHOLD = 1024 * 200;

    public CharStreamFactory(IterationResultFactory iterationResultFactory) {
        this.iterationResultFactory = iterationResultFactory;
    }

    @Override
    public SafeIterator<MetaChar> fromInputStream(InputStream in) {
        try {
            byte[] bytes = in.readAllBytes();
            CharSource source;
            if (bytes.length < SMALL_THRESHOLD) {
                source = new MemoryCharSource(bytes);
            } else {
                Path tmp = Files.createTempFile("charstream", ".tmp");
                Files.write(tmp, bytes);
                source = new MappedCharSource(tmp);
            }

            return new CharStream(source, iterationResultFactory);

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public SafeIterator<MetaChar> fromInputStreamLogger(InputStream in, String debugPath) {
        try {
            return new LoggerCharStream(fromInputStream(in), debugPath);
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }
}
