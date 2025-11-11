/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.literal;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.types.Types;

public final class BooleanLiteralTokenizer implements Tokenizer {
  private final String regEx;
  private final TokenFactory tokenFactory;
  private final ResultFactory resultFactory;

  public BooleanLiteralTokenizer(TokenFactory tokenFactory, ResultFactory resultFactory) {
    this.regEx = Types.BOOLEAN.regEx();
    this.tokenFactory = tokenFactory;
    this.resultFactory = resultFactory;
  }

  private Boolean canTokenize(String input) {
    return input.matches(regEx);
  }

  @Override
  public Result<Token> tokenize(String input, Integer line, Integer column) {
    if (!canTokenize(input)) {
      return resultFactory.createIncorrectResult(String.format(
          "Unknown token on line:%d and column:%d", line, column));
    }
    return resultFactory.createCorrectResult(tokenFactory.createLiteralToken(input, line, column));
  }
}
