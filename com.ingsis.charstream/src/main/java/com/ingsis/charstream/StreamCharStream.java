/*
 * My Project
 */

package com.ingsis.charstream;

import com.ingsis.utils.metachar.MetaChar;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

public final class StreamCharStream implements PeekableIterator<MetaChar> {
    private final InputStream in;
    private Character nextChar = null;
    private int currentLine = 1;
    private int currentColumn = 1;
    private boolean closed = false;

    public StreamCharStream(InputStream in) {
        this.in = in;
    }

    private void readNextChar() {
        if (closed) {
            nextChar = null;
            return;
        }

        try {
            int b = in.read();
            if (b == -1) {
                nextChar = null;
                closed = true;
                in.close();
            } else {
                nextChar = (char) b;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading input", e);
        }
    }

    @Override
    public boolean hasNext() {
        if (!closed && nextChar == null) {
            readNextChar();
        }
        return nextChar != null;
    }

    @Override
    public MetaChar peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return new MetaChar(nextChar, currentLine, currentColumn);
    }

    @Override
    public MetaChar next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        MetaChar result = new MetaChar(nextChar, currentLine, currentColumn);

        if (nextChar == '\n') {
            currentLine++;
            currentColumn = 1;
        } else {
            currentColumn++;
        }

        readNextChar();
        return result;
    }
}
