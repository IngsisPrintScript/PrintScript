/*
 * My Project
 */

package com.ingsis.lexer.tokenizers;

import java.util.List;

import com.ingsis.lexer.tokenizers.categories.TokenCategory;
import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.TokenFactory;
import com.ingsis.utils.token.type.TokenType;

public class PatternMatchTokenizer implements Tokenizer {
  private final TokenType type;
  private final TokenCategory category;
  private final TokenFactory tokenFactory;

  public PatternMatchTokenizer(
      TokenType type, TokenCategory category, TokenFactory tokenFactory) {
    this.type = type;
    this.category = category;
    this.tokenFactory = tokenFactory;
  }

  private Boolean canTokenize(String input) {
    return input.matches(type.pattern());
  }

  @Override
  public ProcessResult<Token> tokenize(String input, List<Token> trailingTrivia, Integer line, Integer column) {
    if (!canTokenize(input)) {
      return ProcessResult.INVALID();
    }
    Token token = tokenFactory.createKnownToken(type, input, trailingTrivia, line, column);
    return ProcessResult.COMPLETE(token, category.priority());
  }
}
