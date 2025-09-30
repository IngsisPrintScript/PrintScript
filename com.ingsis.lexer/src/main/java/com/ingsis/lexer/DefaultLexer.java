/*
 * My Project
 */

package com.ingsis.lexer;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.Result;
import com.ingsis.tokens.Token;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public final class DefaultLexer implements Lexer {
    private final PeekableIterator<Character> charIterator;
    private final Queue<Token> tokenBuffer;
    private final Tokenizer tokenizer;

    public DefaultLexer(
            PeekableIterator<Character> charIterator,
            Tokenizer tokenizer,
            Queue<Token> tokenBuffer) {
        this.charIterator = charIterator;
        this.tokenBuffer = new LinkedList<>(tokenBuffer);
        this.tokenizer = tokenizer;
    }

    public DefaultLexer(PeekableIterator<Character> charIterator, Tokenizer tokenizer) {
        this(charIterator, tokenizer, new LinkedList<>());
    }

    @Override
    public Result<Token> analyze(String input) {
        return tokenizer.tokenize(input);
    }

    @Override
    public Token peek() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return tokenBuffer.peek();
    }

    @Override
    public boolean hasNext() {
        if (!tokenBuffer.isEmpty()) {
            return true;
        }

        Token nextToken = computeNext();

        if (nextToken != null) {
            tokenBuffer.add(nextToken);
        }

        return nextToken != null;
    }

    @Override
    public Token next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return tokenBuffer.poll();
    }

    private Token computeNext() {
        Token cadidateToken = null;
        StringBuilder stringBuilder = new StringBuilder();
        while (charIterator.hasNext()) {
            Character nextChar = charIterator.peek();
            stringBuilder.append(nextChar);
            Result<Token> analyzeInputResult = this.analyze(stringBuilder.toString());
            if (analyzeInputResult.isCorrect()) {
                cadidateToken = analyzeInputResult.result();
            } else {
                if (cadidateToken != null) {
                    return cadidateToken;
                }
            }
            charIterator.next();
        }
        return cadidateToken;
    }
}
