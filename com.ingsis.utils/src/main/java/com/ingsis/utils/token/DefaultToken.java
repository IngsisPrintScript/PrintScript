/*
 * My Project
 */

package com.ingsis.utils.token;

import com.ingsis.utils.token.type.TokenType;
import java.util.List;

public record DefaultToken(
        TokenType type, String value, List<Token> leadingTrivia, Integer line, Integer column)
        implements Token {}
