/*
 * My Project
 */

package com.ingsis.utils.token.factories;

import java.util.List;

import com.ingsis.utils.token.DefaultToken;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;

public final class DefaultTokensFactory implements TokenFactory {
  @Override
  public Token createKnownToken(TokenType type, String lexeme, List<Token> leadingTrivia, Integer line,
      Integer column) {
    return new DefaultToken(type, lexeme, leadingTrivia, line, column);
  }
}
