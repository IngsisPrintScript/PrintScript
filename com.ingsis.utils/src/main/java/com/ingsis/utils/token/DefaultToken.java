/*
 * My Project
 */

package com.ingsis.utils.token;

import java.util.List;

import com.ingsis.utils.token.type.TokenType;

public record DefaultToken(
    TokenType type,
    String value,
    List<Token> leadingTrivia,
    Integer line,
    Integer column) implements Token {
}
