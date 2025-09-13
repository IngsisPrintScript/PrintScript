/*
 * My Project
 */

package com.ingsis.printscript.repositories;

import com.ingsis.printscript.peekableiterator.PeekableIterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public record CliRepository(Queue<Character> buffer) implements PeekableIterator<Character> {

    public CliRepository {
        buffer = new LinkedList<>(buffer);
    }

    public Queue<Character> buffer() {
        return new LinkedList<>(buffer);
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

    @Override
    public Character peek() {
        if (!hasNext()) throw new NoSuchElementException();
        return buffer.peek();
    }
}
