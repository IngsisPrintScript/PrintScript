/*
 * My Project
 */

package com.ingsis.parser.syntactic;

import com.ingsis.parser.syntactic.parsers.Parser;
import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokenstream.DefaultTokenStream;
import com.ingsis.utils.token.tokenstream.TokenStream;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public final class DefaultSyntacticParser implements SyntacticParser {
    private final PeekableIterator<Token> tokenIterator;
    private final Queue<Checkable> checkableBuffer;
    private final Parser<Node> parser;
    private TokenStream tokenStream;

    public DefaultSyntacticParser(
            PeekableIterator<Token> tokenIterator,
            Parser<Node> parser,
            Queue<Checkable> checkableBuffer) {
        this.tokenIterator = tokenIterator;
        this.checkableBuffer = new LinkedList<>(checkableBuffer);
        this.parser = parser;
        this.tokenStream = new DefaultTokenStream();
    }

    public DefaultSyntacticParser(PeekableIterator<Token> tokenIterator, Parser<Node> parser) {
        this(tokenIterator, parser, new LinkedList<>());
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

        Result<? extends Node> nextResult = computeNext();
        if (nextResult.isCorrect()) {
            checkableBuffer.add((Checkable) nextResult.result());
            System.out.println("NODE: " + nextResult.result());
        }

        return nextResult.isCorrect();
    }

    @Override
    public Checkable next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return checkableBuffer.poll();
    }

    private Result<? extends Node> computeNext() {
        Result<? extends Node> result = parse();
        if (result.isCorrect()) {
            tokenStream.cleanBuffer();
            return result;
        }
        while (tokenIterator.hasNext()) {
            tokenStream = tokenStream.addToken(tokenIterator.next());
            System.out.println("TOKEN STREAM: " + tokenStream.tokens());
            System.out.println("TOKEN START STREAM POINTER: " + tokenStream.pointer());
            result = parse();
            System.out.println("TOKEN END STREAM POINTER: " + tokenStream.pointer());
            if (result.isCorrect()) {
                tokenStream.cleanBuffer();
                return result;
            }
        }
        return result;
    }
}
