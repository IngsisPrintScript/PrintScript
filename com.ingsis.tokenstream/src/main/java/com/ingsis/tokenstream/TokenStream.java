/*
 * My Project
 */

package com.ingsis.tokenstream;

import com.ingsis.peekableiterator.PeekableIterator;
import com.ingsis.result.Result;
import com.ingsis.tokens.Token;

public interface TokenStream extends PeekableIterator<Token> {
    boolean match(Token tokenTemplate);

    Result<Token> consume();

    Result<Token> consume(Token token);

    Result<Integer> consumeAll(Token token);
}
