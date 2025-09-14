/*
 * My Project
 */

package com.ingsis.printscript.repositories;

import com.ingsis.printscript.peekableiterator.PeekableIterator;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public final class CliRepository implements PeekableIterator<Character> {
    private final Queue<Character> buffer;

    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public CliRepository(Queue<Character> buffer) {
        this.buffer = buffer;
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
