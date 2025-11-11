/*
 * My Project
 */

package com.ingsis.charstream;

import com.ingsis.metachar.MetaChar;
import com.ingsis.peekableiterator.PeekableIterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public final class InMemoryCharStream implements PeekableIterator<MetaChar> {
    private final Queue<Character> buffer;
    private Integer currentLine;
    private Integer currentColumn;

    public InMemoryCharStream(Queue<Character> buffer) {
        this.buffer = new LinkedList<>(buffer);
        this.currentLine = 1;
        this.currentColumn = 1;
    }

    @Override
    public MetaChar peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return new MetaChar(buffer.peek(), currentLine, currentColumn);
    }

    @Override
    public boolean hasNext() {
        return !buffer.isEmpty();
    }

    @Override
    public MetaChar next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        MetaChar result = new MetaChar(buffer.poll(), currentLine, currentColumn);
        if (result.character() == '\n') {
            currentColumn = 0;
            currentLine++;
        } else {
            currentColumn++;
        }
        return result;
    }

    public void addChars(Iterable<Character> chars) {
        for (Character character : chars) {
            buffer.add(character);
        }
    }

    public void addChar(Character character) {
        buffer.add(character);
    }
}
