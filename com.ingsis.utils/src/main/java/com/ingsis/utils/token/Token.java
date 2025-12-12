/*
 * My Project
 */

package com.ingsis.utils.token;

import com.ingsis.utils.token.type.TokenType;
import java.util.List;

public interface Token {
    TokenType type();

    Integer line();

    Integer column();

    String value();

    List<Token> leadingTrivia();
}
