/*
 * My Project
 */

package com.ingsis.formatter.factories;

import com.ingsis.formatter.ProgramFormatter;
import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.factories.SafeIteratorFactory;
import com.ingsis.utils.iterator.safe.result.IterationResultFactory;
import com.ingsis.utils.token.Token;

import java.io.InputStream;

public class DefaultFormatterFactory implements SafeIteratorFactory<String> {
  private final SafeIteratorFactory<Token> tokenIteratorFactory;
  private final IterationResultFactory iterationResultFactory;

  public DefaultFormatterFactory(SafeIteratorFactory<Token> tokenIteratorFactory,
      IterationResultFactory iterationResultFactory) {
    this.tokenIteratorFactory = tokenIteratorFactory;
    this.iterationResultFactory = iterationResultFactory;
  }

  @Override
  public SafeIterator<String> fromInputStream(InputStream in) {
    return new ProgramFormatter(
        tokenIteratorFactory.fromInputStream(in),
        iterationResultFactory);
  }

  @Override
  public SafeIterator<String> fromInputStreamLogger(InputStream in, String debugPath) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'fromInputStreamLogger'");
  }

}
