/*
 * My Project
 */

package com.ingsis.syntactic;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;
import com.ingsis.syntactic.parsers.ParserRegistry;
import com.ingsis.tokenstream.TokenStream;
import com.ingsis.visitors.Checkable;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public final class DefaultSyntacticParser implements SyntacticParser {
    private final TokenStream tokenStream;
    private final Queue<Checkable> checkableBuffer;
    private final ParserRegistry parserRegistry;

    public DefaultSyntacticParser(
            TokenStream tokenStream,
            Queue<Checkable> checkableBuffer,
            ParserRegistry parserRegistry) {
        this.tokenStream = tokenStream;
        this.checkableBuffer = new LinkedList<>(checkableBuffer);
        this.parserRegistry = parserRegistry;
    }

    public DefaultSyntacticParser(TokenStream tokenStream, ParserRegistry parserRegistry) {
        this(tokenStream, new LinkedList<>(), parserRegistry);
    }

    @Override
    public Result<? extends Node> parse() {
        return parserRegistry.parse(tokenStream);
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

        Checkable next = (Checkable) computeNext();

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

    private Node computeNext() {
        Node candidate = null;
        Result<? extends Node> parseResult = parse();
        if (parseResult.isCorrect()) {
            candidate = parseResult.result();
        }
        return candidate;
    }
}
