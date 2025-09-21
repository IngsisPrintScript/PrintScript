/*
 * My Project
 */

package com.ingsis.tokens.stream;

import com.ingsis.result.Result;
import com.ingsis.tokens.Token;

public interface TokenStream {

    Result<String> addToken(Token token);

    Result<Token> consume();

    Result<Token> consume(Token token);

    Result<Integer> consumeAll(Token token);
}
