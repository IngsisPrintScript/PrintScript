/*
 * My Project
 */

package com.ingsis.utils.token.tokenstream; /*
                                             * My Project
                                             */

import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.token.tokens.Token;
import com.ingsis.utils.token.tokens.factories.DefaultTokensFactory;
import java.util.ArrayList;
import java.util.List;

public final class DefaultTokenStream implements TokenStream {
    private final PeekableIterator<Token> tokens;
    private final List<Token> tokenBuffer;
    private final Token SPACE_TOKEN_TEMPLATE;
    private final ResultFactory RESULT_FACTORY;
    private Integer pointer;

    public DefaultTokenStream(PeekableIterator<Token> tokenStream, ResultFactory resultFactory) {
        this.tokens = tokenStream;
        this.SPACE_TOKEN_TEMPLATE =
                new DefaultTokensFactory().createSpaceSeparatorToken("", null, null);
        this.tokenBuffer = new ArrayList<>();
        this.pointer = 0;
        this.RESULT_FACTORY = resultFactory;
    }

    @Override
    public Result<Token> consume() {
        consumeAll(SPACE_TOKEN_TEMPLATE);
        if (pointer < tokenBuffer.size()) {
            return new CorrectResult<>(tokenBuffer.get(pointer++));
        }
        if (tokens.hasNext()) {
            return new CorrectResult<>(tokens.next());
        }
        return new IncorrectResult<>("No more tokens.");
    }

    @Override
    public Result<Token> consume(Token tokenTemplate) {
        consumeAll(SPACE_TOKEN_TEMPLATE);
        if (!baseMatch(tokenTemplate)) {
            if (!this.hasNext()) {
                return RESULT_FACTORY.createIncorrectResult("Uncomplete tokens sequence");
            }
            Token peekToken = this.peek();
            return RESULT_FACTORY.createIncorrectResult(
                    String.format(
                            "Unexpected token on line: %d and column: %d, original:%s expected:%s",
                            peekToken.line(),
                            peekToken.column(),
                            peekToken.toString(),
                            tokenTemplate.toString()));
        }
        return consume();
    }

    @Override
    public Result<Integer> consumeAll(Token token) {
        int count = 0;
        while (pointer < tokenBuffer.size()) {
            while (baseMatch(token) || baseMatch(SPACE_TOKEN_TEMPLATE)) {
                pointer++;
                count++;
            }
            return new CorrectResult<>(count);
        }
        while (baseMatch(token) || baseMatch(SPACE_TOKEN_TEMPLATE)) {
            tokens.next();
            count++;
        }
        return new CorrectResult<>(count);
    }

    @Override
    public boolean match(Token tokenTemplate) {
        consumeAll(SPACE_TOKEN_TEMPLATE);
        return baseMatch(tokenTemplate);
    }

    private boolean baseMatch(Token tokenTemplate) {
        if (pointer < tokenBuffer.size()) {
            return tokenBuffer.get(pointer).equals(tokenTemplate);
        }
        return tokens.hasNext() && tokens.peek().equals(tokenTemplate);
    }

    @Override
    public Token peek() {
        consumeAll(SPACE_TOKEN_TEMPLATE);
        if (pointer < tokenBuffer.size()) {
            return tokenBuffer.get(pointer);
        }
        return tokens.peek();
    }

    @Override
    public boolean hasNext() {
        return tokens.hasNext() || pointer < tokenBuffer.size();
    }

    @Override
    public Token next() {
        if (pointer < tokenBuffer.size()) {
            return tokenBuffer.get(pointer++);
        }
        return tokens.next();
    }

    @Override
    public Token peek(int offset) {
        consumeAll(SPACE_TOKEN_TEMPLATE);
        while (offset + pointer >= tokenBuffer.size()) {
            if (!tokens.hasNext()) {
                return null;
            }
            consumeAll(SPACE_TOKEN_TEMPLATE);
            tokenBuffer.add(tokens.next());
        }
        return tokenBuffer.get(pointer + offset);
    }

    @Override
    public void cleanBuffer() {
        this.pointer = 0;
        this.tokenBuffer.clear();
    }
}
