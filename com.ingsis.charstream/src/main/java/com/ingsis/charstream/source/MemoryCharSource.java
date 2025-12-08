/*
 * My Project
 */

package com.ingsis.charstream.source;

import java.nio.charset.StandardCharsets;

public final class MemoryCharSource implements CharSource {
    private final char[] buffer;

    public MemoryCharSource(byte[] bytes) {
        this.buffer = new String(bytes, StandardCharsets.UTF_8).toCharArray();
    }

    @Override
    public int length() {
        return buffer.length;
    }

    @Override
    public char charAt(int index) {
        return buffer[index];
    }
}
