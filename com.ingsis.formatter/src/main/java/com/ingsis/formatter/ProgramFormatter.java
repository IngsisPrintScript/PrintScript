/*
 * My Project
 */

package com.ingsis.formatter;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.token.Token;

public class ProgramFormatter implements SafeIterator<String> {
  private final SafeIterator<Token> tokenStream;
  private final IterationResultFactory iterationResultFactory;

  public ProgramFormatter(
      SafeIterator<Token> tokenStream,
      IterationResultFactory iterationResultFactory) {
    this.tokenStream = tokenStream;
    this.iterationResultFactory = iterationResultFactory;
  }

  @Override
  public SafeIterationResult<String> next() {
    StringBuilder sb = new StringBuilder();
    SafeIterationResult<Token> iterationResult = tokenStream.next();
    Token token = iterationResult.iterationResult();
    switch (token.type()) {
      default:
        for (Token triviaToken : token.leadingTrivia()) {
          sb.append(triviaToken.value());
        }
        sb.append(token.value());
    }
    return iterationResultFactory.createCorrectResult(
        sb.toString(),
        new ProgramFormatter(
            iterationResult.nextIterator(),
            iterationResultFactory));
  }
}
