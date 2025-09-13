/*
 * My Project
 */

package com.ingsis.printscript.repositories;

import com.ingsis.printscript.peekableiterator.PeekableIterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public record CliRepository(Queue<Character> buffer) implements PeekableIterator<Character> {

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
