package com.ingsis.lexer.tokenizers;

import com.ingsis.utils.process.result.ProcessResult;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.token.Token;
import com.ingsis.utils.token.factories.TokenFactory;

public class ExactMatchTokenizer implements Tokenizer {
  private final String template;
  private final TokenFactory tokenFactory;
  private final Integer priority;

  public ExactMatchTokenizer(String template, TokenFactory tokenFactory, Integer priority) {
    this.template = template;
    this.tokenFactory = tokenFactory;
    this.priority = priority;
  }

  private Boolean canTokenize(String input) {
    return input.equals(template);
  }

  @Override
  public ProcessResult<Token> tokenize(String input, Integer line, Integer column) {
    if (!canTokenize(input)) {
      return ProcessResult.INVALID();
    }
    Result<Token> createTokenResult = tokenFactory.createToken(input, line, column);
    if (!createTokenResult.isCorrect()) {
      return ProcessResult.INVALID();
    }
    return ProcessResult.COMPLETE(createTokenResult.result(), priority);
  }

}
