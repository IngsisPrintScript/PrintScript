/*
 * My Project
 */

package com.ingsis.tokenstream;

import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokens.Token;

public final class DefaultTokenStream implements TokenStream {
    private final PeekableIterator<Token> tokens;

    public DefaultTokenStream(PeekableIterator<Token> tokenStream) {
        this.tokens = tokenStream;
    }

    @Override
    public Result<Token> consume() {
        if (tokens.hasNext()) return new CorrectResult<>(tokens.next());
        return new IncorrectResult<>("No more tokens.");
    }

    @Override
    public Result<Token> consume(Token tokenTemplate) {
        if (!match(tokenTemplate)) {
            return new IncorrectResult<>("Token does not match template.");
        }
        Token nextToken = tokens.next();
        return new CorrectResult<>(nextToken);
    }

    @Override
    public Result<Integer> consumeAll(Token token) {
        int count = 0;
        while (match(token)) {
            tokens.next();
            count++;
        }
        return new CorrectResult<>(count);
    }

    private boolean match(Token tokenTemplate) {
        return tokens.hasNext() && tokens.peek().equals(tokenTemplate);
    }

    @Override
    public Token peek() {
        return tokens.peek();
    }

    @Override
    public boolean hasNext() {
        return tokens.hasNext();
    }

    @Override
    public Token next() {
        return tokens.next();
    }
}
