/*
 * My Project
 */

package com.ingsis.formatter;

import com.ingsis.utils.iterator.safe.SafeIterator;
import com.ingsis.utils.iterator.safe.result.SafeIterationResult;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import java.io.StringWriter;

public class ProgramFormatter implements SafeIterator<String> {
  private final SafeIterator<Interpretable> checkableStream;
  private final Checker checker;
  private final StringWriter writer;

  public ProgramFormatter(
      SafeIterator<Interpretable> checkableStream,
      Checker eventsChecker,
      StringWriter writer) {
    this.checkableStream = checkableStream;
    this.checker = eventsChecker;
    this.writer = writer;
  }

  @Override
  public SafeIterationResult<String> next() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'next'");
  }
}
