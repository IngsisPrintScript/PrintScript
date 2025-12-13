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

    private static final int WINDOW_CHARS = 8192; // 8 KB decoded chars

    private long windowByteStart = 0; // byte offset of current window
    private int windowCharStart = 0; // char index of first char in window
    private CharBuffer charWindow;

    private final CharsetDecoder decoder;

    public MappedCharSource(Path path) {
        try {
            FileChannel ch = FileChannel.open(path, StandardOpenOption.READ);
            this.byteSize = ch.size();
            this.mmap = ch.map(FileChannel.MapMode.READ_ONLY, 0, byteSize);

            this.decoder =
                    StandardCharsets.UTF_8
                            .newDecoder()
                            .onMalformedInput(CodingErrorAction.REPORT)
                            .onUnmappableCharacter(CodingErrorAction.REPORT);

            loadWindow(0, 0);

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /** Decodes next window starting at given byteOffset and charOffset */
    private void loadWindow(long byteOffset, int charOffset) {
        mmap.position((int) byteOffset);

        ByteBuffer byteSlice = mmap.slice();
        CharBuffer out = CharBuffer.allocate(WINDOW_CHARS);

        decoder.reset();
        CoderResult r = decoder.decode(byteSlice, out, false);

        out.flip();

        this.charWindow = out;
        this.windowByteStart = byteOffset;
        this.windowCharStart = charOffset;
    }

    @Override
    public char charAt(int index) {
        // rewind window backward?
        if (index < windowCharStart) {
            // Reload from start (simple)
            loadWindow(0, 0);
        }

        // advance window until it contains index
        while (index >= windowCharStart + charWindow.length()) {
            // calculate how many chars we've consumed
            int consumedChars = charWindow.length();

            // approximate next byte offset by scanning consumed bytes
            long newByteStart = approximateNextByteOffset(windowByteStart, consumedChars);

            loadWindow(newByteStart, windowCharStart + consumedChars);
        }

        return charWindow.get(index - windowCharStart);
    }

    /**
     * Guess next byte offset by re-decoding the same window but tracking bytes. This ensures
     * correctness for UTF-8 without storing entire mapping.
     */
    private long approximateNextByteOffset(long startByte, int charsToSkip) {
        mmap.position((int) startByte);
        ByteBuffer slice = mmap.slice();

        CharsetDecoder dec = StandardCharsets.UTF_8.newDecoder();
        CharBuffer tmp = CharBuffer.allocate(charsToSkip);
        dec.decode(slice, tmp, false);

        return startByte + slice.position();
    }
}
