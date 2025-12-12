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

public class PrefixAwarePatternTokenizer implements Tokenizer {
  private final TokenType type;
  private final TokenCategory category;
  private final TokenFactory tokenFactory;

  public PrefixAwarePatternTokenizer(
      TokenType type, TokenCategory category, TokenFactory tokenFactory) {
    this.type = type;
    this.tokenFactory = tokenFactory;
    this.category = category;
  }

  private Boolean matchesPattern(String input) {
    return input.matches(type.pattern());
  }

  private Boolean matchesPrefixPattern(String input) {
    return input.matches(type.prefixPattern());
  }

  @Override
  public ProcessResult<Token> tokenize(String input, List<Token> trailingTrivia, Integer line, Integer column) {
    if (matchesPattern(input)) {
      Token token = tokenFactory.createKnownToken(type, input, trailingTrivia, line, column);
      return ProcessResult.COMPLETE(token, category.priority());

    } else if (matchesPrefixPattern(input)) {
      return ProcessResult.PREFIX(category.priority());
    }
    return ProcessResult.INVALID();
  }
}
