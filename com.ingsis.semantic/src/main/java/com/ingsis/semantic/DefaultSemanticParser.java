/*
 * My Project
 */

package com.ingsis.semantic;

import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Checkable;
import com.ingsis.visitors.Interpretable;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public final class DefaultSemanticParser implements SemanticParser {
    private final PeekableIterator<Checkable> checkableStream;
    private final Queue<Interpretable> interpretableBuffer;

    public DefaultSemanticParser(
            PeekableIterator<Checkable> checkableStream, Queue<Interpretable> interpretableBuffer) {
        this.checkableStream = checkableStream;
        this.interpretableBuffer = new LinkedList<>(interpretableBuffer);
    }

    public DefaultSemanticParser(PeekableIterator<Checkable> checkableStream) {
        this(checkableStream, new LinkedList<>());
    }

    @Override
    public Result<Interpretable> parse() {
        return new IncorrectResult<>("Not implemented yet.");
    }

    @Override
    public Interpretable peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return interpretableBuffer.peek();
    }

    @Override
    public boolean hasNext() {
        if (!interpretableBuffer.isEmpty()) {
            return true;
        }

        Interpretable next = computeNext();

        if (next != null) {
            interpretableBuffer.add(next);
        }

        return next != null;
    }

    @Override
    public Interpretable next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return interpretableBuffer.poll();
    }

    private Interpretable computeNext() {
        Interpretable candidate = null;
        if (checkableStream.hasNext()) {
            Result<Interpretable> parseResult = parse();
            if (parseResult.isCorrect()) {
                candidate = parseResult.result();
            }
        }
        return candidate;
    }
}
