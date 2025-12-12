/*
 * My Project
 */

package com.ingsis.utils.token.factories;

import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;
import java.util.List;

public interface TokenFactory {
    Token createKnownToken(
            TokenType type, String lexeme, List<Token> leadingTrivia, Integer line, Integer column);
}
