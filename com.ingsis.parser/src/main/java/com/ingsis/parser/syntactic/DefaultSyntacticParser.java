/*
 * My Project
 */

package com.ingsis.syntactic;

import com.ingsis.nodes.Node;
import com.ingsis.result.Result;
import com.ingsis.syntactic.parsers.Parser;
import com.ingsis.tokenstream.TokenStream;
import com.ingsis.visitors.Checkable;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public final class DefaultSyntacticParser implements SyntacticParser {
    private final TokenStream tokenStream;
    private final Queue<Checkable> checkableBuffer;
    private final Parser<Node> parser;

    public DefaultSyntacticParser(
            TokenStream tokenStream, Parser<Node> parser, Queue<Checkable> checkableBuffer) {
        this.tokenStream = tokenStream;
        this.checkableBuffer = new LinkedList<>(checkableBuffer);
        this.parser = parser;
    }

    public DefaultSyntacticParser(TokenStream tokenStream, Parser<Node> parser) {
        this(tokenStream, parser, new LinkedList<>());
    }

    @Override
    public Result<? extends Node> parse() {
        return parser.parse(tokenStream);
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

        Node next = computeNext();
        if (next != null) {
            checkableBuffer.add((Checkable) next);
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
        Result<? extends Node> parseResult = parse();
        if (parseResult.isCorrect()) {
            tokenStream.cleanBuffer();
            return parseResult.result();
        }
        return null;
    }
}
