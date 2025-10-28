/*
 * My Project
 */

package com.ingsis.lexer.tokenizers.literal;

import com.ingsis.lexer.tokenizers.Tokenizer;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.tokens.Token;
import com.ingsis.tokens.factories.TokenFactory;
import com.ingsis.types.Types;

public final class BooleanLiteralTokenizer implements Tokenizer {
  private final String regEx;
  private final TokenFactory tokenFactory;

  public BooleanLiteralTokenizer(TokenFactory tokenFactory) {
    this.regEx = Types.BOOLEAN.regEx();
    this.tokenFactory = tokenFactory;
  }

  private Boolean canTokenize(String input) {
    return input.matches(regEx);
  }

  @Override
  public Result<Token> tokenize(String input) {
    if (!canTokenize(input)) {
      return new IncorrectResult<>("Input is not a valid boolean: " + input);
    }
    return new CorrectResult<>(tokenFactory.createLiteralToken(input));
  }
}
