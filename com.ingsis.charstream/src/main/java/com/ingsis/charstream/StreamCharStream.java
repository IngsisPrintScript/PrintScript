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
    private Character nextChar;
    private int currentLine = 1;
    private int currentColumn = 1;
    private boolean closed = false;

    public StreamCharStream(InputStream in) throws IOException {
        this.in = in;
        advance();
    }

    private void advance() throws IOException {
        if (closed) {
            nextChar = null;
            return;
        }
        int b = in.read();
        if (b == -1) {
            nextChar = null;
            in.close();
            closed = true;
        } else {
            nextChar = (char) b;
        }
    }

    @Override
    public MetaChar peek() {
        if (!hasNext()) throw new NoSuchElementException();
        return new MetaChar(nextChar, currentLine, currentColumn);
    }

    @Override
    public boolean hasNext() {
        return nextChar != null;
    }

    @Override
    public MetaChar next() {
        if (!hasNext()) throw new NoSuchElementException();
        MetaChar result = new MetaChar(nextChar, currentLine, currentColumn);

        if (nextChar == '\n') {
            currentLine++;
            currentColumn = 1;
        } else {
            currentColumn++;
        }

        try {
            advance();
        } catch (IOException e) {
            throw new RuntimeException("Error reading from InputStream", e);
        }

        return result;
    }
}
