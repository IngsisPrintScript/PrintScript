/*
 * My Project
 */

package com.ingsis.utils.token.tokenstream; /*
                                             * My Project
                                             */

import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.tokens.Token;

public interface TokenStream extends PeekableIterator<Token> {
    boolean match(Token tokenTemplate);

    Result<Token> consume();

    Result<Token> consume(Token token);

    Result<Integer> consumeAll(Token token);

    Token peek(int offset);

    void cleanBuffer();
}
