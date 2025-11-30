/*
 * My Project
 */

package com.ingsis.interpreter;

import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.peekableiterator.PeekableIterator;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class DefaultProgramInterpreter implements ProgramInterpreter {
  private final PeekableIterator<Interpretable> interpretableStream;
  private final Interpreter interpreter;

  public DefaultProgramInterpreter(
      PeekableIterator<Interpretable> interpretableStream, Interpreter interpreter) {
    this.interpretableStream = interpretableStream;
    this.interpreter = interpreter;
  }

  @Override
  public Result<String> interpret() {
    Result<String> interpretResult = new IncorrectResult<>("That's not a valid PrintScript expression!");
    while (interpretableStream.hasNext()) {
      Interpretable interpretable = interpretableStream.next();
      interpretResult = interpretable.acceptInterpreter(interpreter);
      if (!interpretResult.isCorrect()) {
        return interpretResult;
      }
    }
    return interpretResult;
  }
}
