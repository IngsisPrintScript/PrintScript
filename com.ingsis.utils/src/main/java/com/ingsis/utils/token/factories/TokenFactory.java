/*
 * My Project
 */

package com.ingsis.utils.token.factories;

import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;

public interface TokenFactory {
    Result<Token> createToken(String keyword, Integer line, Integer column);

    Token createKnownToken(TokenType type, String lexeme, Integer line, Integer column);
}
