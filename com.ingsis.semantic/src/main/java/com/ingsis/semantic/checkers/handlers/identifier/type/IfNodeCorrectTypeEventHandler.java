/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.identifier.type;

import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import com.ingsis.typer.expression.DefaultExpressionTypeGetter;
import com.ingsis.types.Types;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class IfNodeCorrectTypeEventHandler implements NodeEventHandler<IfKeywordNode> {
  private final Runtime runtime;

  public IfNodeCorrectTypeEventHandler(Runtime runtime) {
    this.runtime = runtime;
  }

  @Override
  public Result<String> handle(IfKeywordNode node) {
    Types expectedType = Types.BOOLEAN;
    Types actualType = new DefaultExpressionTypeGetter(runtime).getType(node.condition());
    if (!expectedType.equals(actualType)) {
      return new IncorrectResult<>(
          "Condition evaluation type should be: "
              + expectedType
              + " but was: "
              + actualType);
    }
    return new CorrectResult<>("Condition evaluation type is correct.");
  }
}
