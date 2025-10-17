/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.identifier.existance;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Collection;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class ExpressionNodeEventVariableExistenceHandler
    implements NodeEventHandler<ExpressionNode> {
  private final Runtime runtime;

  public ExpressionNodeEventVariableExistenceHandler(Runtime runtime) {
    this.runtime = runtime;
  }

  @Override
  public Result<String> handle(ExpressionNode node) {
    Collection<ExpressionNode> children = node.children();

    if (node instanceof IdentifierNode(String name)) {
      if (!runtime.getCurrentEnvironment().isVariableInitialized(name)) {
        return new IncorrectResult<>("The variable " + name + " is not initialized.");
      }
      return new CorrectResult<>("The variable " + name + " is initialized.");
    }

    for (ExpressionNode child : children) {
      Result<String> result = handle(child);
      if (!result.isCorrect()) {
        return result;
      }
    }

    return new CorrectResult<>("All used variables are initialized");
  }
}
