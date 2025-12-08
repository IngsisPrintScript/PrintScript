/*
 * My Project
 */

package com.ingsis.charstream.source;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class MappedCharSource implements CharSource {
    private final CharBuffer buffer;

    public MappedCharSource(Path path) {
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.READ)) {
            MappedByteBuffer mbb = ch.map(FileChannel.MapMode.READ_ONLY, 0, ch.size());
            this.buffer = StandardCharsets.UTF_8.decode(mbb).asReadOnlyBuffer();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public int length() {
        return buffer.length();
    }

    @Override
    public char charAt(int index) {
        return buffer.get(index);
    }
}
