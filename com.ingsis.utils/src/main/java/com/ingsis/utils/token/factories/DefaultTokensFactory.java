/*
 * My Project
 */

package com.ingsis.utils.token.factories;

import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.DefaultToken;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.type.TokenType;

public final class DefaultTokensFactory implements TokenFactory {
  @Override
  public Result<Token> createToken(String value, Integer line, Integer column) {
    Result<TokenType> getTypeResult = TokenType.fromString(value);
    if (!getTypeResult.isCorrect()) {
      return new IncorrectResult<>("There is no token type for input: " + value);
    }
    return new CorrectResult<Token>(
        new DefaultToken(
            getTypeResult.result(),
            value,
            line,
            column));
  }
}
