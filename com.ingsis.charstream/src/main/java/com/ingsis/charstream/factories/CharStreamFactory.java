/*
 * My Project
 */

package com.ingsis.charstream.factories;

import com.ingsis.charstream.CharStream;
import com.ingsis.charstream.LoggerCharStream;
import com.ingsis.charstream.source.MappedCharSource;
import com.ingsis.charstream.source.MemoryCharSource;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.metachar.MetaChar;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
            Path tmp = Files.createTempFile("charstream", ".tmp");

            long total = 0;
            try (OutputStream out = Files.newOutputStream(tmp)) {
                byte[] buffer = new byte[8192];
                int n;
                while ((n = in.read(buffer)) != -1) {
                    out.write(buffer, 0, n);
                    total += n;

                    // If small enough, just continue writing; we can decide later.
                }
            }

            if (total < SMALL_THRESHOLD) {
                // load small file into memory
                byte[] bytes = Files.readAllBytes(tmp);
                Files.delete(tmp);
                return new CharStream(new MemoryCharSource(bytes), iterationResultFactory);
            } else {
                // use mmap directly
                return new CharStream(new MappedCharSource(tmp), iterationResultFactory);
            }

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public SafeIterator<MetaChar> fromInputStreamLogger(InputStream in, String debugPath) {
        try {
            return new LoggerCharStream(fromInputStream(in), debugPath, iterationResultFactory);
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }
}
