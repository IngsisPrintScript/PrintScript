/*
 * My Project
 */

package com.ingsis.charstream;

import com.ingsis.peekableiterator.PeekableIterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public final class InMemoryCharStream implements PeekableIterator<Character> {
    private final Queue<Character> buffer;

    public InMemoryCharStream(Queue<Character> buffer) {
        this.buffer = new LinkedList<>(buffer);
    }

    @Override
    public Character peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return buffer.peek();
    }

    @Override
    public boolean hasNext() {
        return !buffer.isEmpty();
    }

    @Override
    public Character next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return buffer.poll();
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
