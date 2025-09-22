/*
 * My Project
 */

package com.ingsis.syntactic;

import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokenstream.TokenStream;
import com.ingsis.visitors.Checkable;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public final class DefaultSyntacticParser implements SyntacticParser {
    private final TokenStream tokenStream;
    private final Queue<Checkable> checkableBuffer;

    public DefaultSyntacticParser(TokenStream tokenStream, Queue<Checkable> checkableBuffer) {
        this.tokenStream = tokenStream;
        this.checkableBuffer = new LinkedList<>(checkableBuffer);
    }

    public DefaultSyntacticParser(TokenStream tokenStream) {
        this(tokenStream, new LinkedList<>());
    }

    @Override
    public Result<Checkable> parse() {
        return new IncorrectResult<>("Not implemented yet.");
    }

    @Override
    public Checkable peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return checkableBuffer.peek();
    }

    @Override
    public boolean hasNext() {
        if (!checkableBuffer.isEmpty()) {
            return true;
        }

        Checkable next = computeNext();

        if (next != null) {
            checkableBuffer.add(next);
        }

        return next != null;
    }

    @Override
    public Checkable next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return checkableBuffer.poll();
    }

    private Checkable computeNext() {
        Checkable candidate = null;
        Result<Checkable> parseResult = parse();
        if (parseResult.isCorrect()) {
            candidate = parseResult.result();
        }
        return candidate;
    }
}
