/*
 * My Project
 */

package com.ingsis.utils.token;

import com.ingsis.utils.token.type.TokenType;

public record DefaultToken(TokenType type, String value, Integer line, Integer column)
        implements Token {

    public DefaultToken(Token token) {
        this(token.type(), token.value(), token.line(), token.column());
    }
}
