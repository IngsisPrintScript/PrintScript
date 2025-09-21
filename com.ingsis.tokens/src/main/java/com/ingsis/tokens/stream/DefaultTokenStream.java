/*
 * My Project
 */

package com.ingsis.tokens.stream;

import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokens.DefaultToken;
import com.ingsis.tokens.Token;
import java.util.ArrayList;

public final class DefaultTokenStream implements TokenStream {
    private final ArrayList<Token> buffer;
    private Integer index;

    public DefaultTokenStream(ArrayList<Token> buffer) {
        this.buffer = new ArrayList<>(buffer);
        this.index = 0;
    }

    private void reset() {
        this.index = 0;
    }

    @Override
    public Result<String> addToken(Token token) {
        buffer.add(new DefaultToken(token));
        reset();
        return new CorrectResult<>("Token added and index reset.");
    }

    @Override
    public Result<Token> consume() {
        Token token = buffer.get(index++);
        return new CorrectResult<>(token);
    }

    @Override
    public Result<Token> consume(Token tokenTemplate) {
        Token token = buffer.get(index);
        if (!tokenTemplate.equals(token)) {
            return new IncorrectResult<>("Token does not match template.");
        }
        index++;
        return new CorrectResult<>(token);
    }

    @Override
    public Result<Integer> consumeAll(Token token) {
        Integer amount = 0;
        while (buffer.contains(token)) {
            buffer.remove(token);
            amount++;
        }
        reset();
        return new CorrectResult<>(amount);
    }
}
