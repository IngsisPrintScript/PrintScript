/*
 * My Project
 */

package com.ingsis.charstream.source;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class MappedCharSource implements CharSource {
    private final MappedByteBuffer mmap;
    private final long byteSize;

    private static final int WINDOW_CHARS = 8192;

    private long windowByteStart = 0;
    private int windowCharStart = 0;
    private int windowBytesConsumed = 0;

    private CharBuffer charWindow;

    private final CharsetDecoder decoder;

    public MappedCharSource(Path path) {
        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.READ)) {
            this.byteSize = ch.size();
            this.mmap = ch.map(FileChannel.MapMode.READ_ONLY, 0, byteSize);
            this.decoder = StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT);

            loadWindow(0, 0);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    private void loadWindow(long byteOffset, int charOffset) {
        if (byteOffset >= byteSize) {
            this.charWindow = CharBuffer.allocate(0);
            this.windowByteStart = byteOffset;
            this.windowCharStart = charOffset;
            this.windowBytesConsumed = 0;
            return;
        }

        mmap.position((int) byteOffset);
        ByteBuffer byteSlice = mmap.slice(); // hasta el final del mmap desde byteOffset
        CharBuffer out = CharBuffer.allocate(WINDOW_CHARS);

        decoder.reset();
        CoderResult r = decoder.decode(byteSlice, out, false);
        this.windowBytesConsumed = byteSlice.position();

        out.flip();
        this.charWindow = out;
        this.windowByteStart = byteOffset;
        this.windowCharStart = charOffset;
    }

    @Override
    public char charAt(int index) {
        if (index < 0) throw new IndexOutOfBoundsException("Negative index: " + index);

        if (index < windowCharStart) {
            loadWindow(0, 0);
        }
        while (index >= windowCharStart + charWindow.length()) {
            int consumedChars = charWindow.length();
            if (consumedChars == 0 || windowBytesConsumed == 0) {
                throw new IndexOutOfBoundsException("EOF");
            }
            long newByteStart = windowByteStart + windowBytesConsumed;
            int newCharStart = windowCharStart + consumedChars;
            if (newByteStart <= windowByteStart) {
                throw new IndexOutOfBoundsException("EOF");
            }

            loadWindow(newByteStart, newCharStart);
        }
        return charWindow.get(index - windowCharStart);
    }
}