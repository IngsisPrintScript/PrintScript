package com.ingsis.charstream;

import com.ingsis.peekableiterator.PeekableIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public final class FromFileCharStream implements PeekableIterator<Character> {
    private final BufferedReader reader;
    private final Queue<Character> buffer;
    private boolean endOfFile;

    public FromFileCharStream(Path path) throws IOException {
        reader = Files.newBufferedReader(path);
        buffer = new LinkedList<>();
        endOfFile = false;
    }

    private void fillBuffer() {
        if (!endOfFile && buffer.isEmpty()) {
            try {
                int ch = reader.read();
                if (ch == -1) {
                    endOfFile = true;
                    reader.close();
                } else {
                    buffer.add((char) ch);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public Character peek() {
        fillBuffer();
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return buffer.peek();
    }

    @Override
    public boolean hasNext() {
        fillBuffer();
        return !buffer.isEmpty();
    }

    @Override
    public Character next() {
        fillBuffer();
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return buffer.poll();
    }
}
