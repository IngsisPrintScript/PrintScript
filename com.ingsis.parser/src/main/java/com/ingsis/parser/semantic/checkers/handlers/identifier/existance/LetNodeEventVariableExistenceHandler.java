/*
 * My Project
 */

package com.ingsis.parser.semantic.checkers.handlers.identifier.existance;

import com.ingsis.utils.runtime.Runtime;
import com.ingsis.utils.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.result.Result;
import com.ingsis.utils.result.factory.ResultFactory;
import com.ingsis.utils.rule.observer.handlers.NodeEventHandler;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class LetNodeEventVariableExistenceHandler
    implements NodeEventHandler<DeclarationKeywordNode> {
  private final Runtime runtime;
  private final ResultFactory resultFactory;

  public LetNodeEventVariableExistenceHandler(Runtime runtime, ResultFactory resultFactory) {
    this.runtime = runtime;
    this.resultFactory = resultFactory;
  }

  @Override
  public Result<String> handle(DeclarationKeywordNode node) {
    String identifier = node.identifierNode().name();
    if (runtime.getCurrentEnvironment().isVariableDeclared(identifier)) {
      return resultFactory.createIncorrectResult(
          String.format(
              "Trying to declare already declared variable: \"%s\" on line: %d and"
                  + " column: %d",
              identifier, node.line(), node.column()));
    }
    return new ExpressionNodeEventVariableExistenceHandler(runtime, resultFactory)
        .handle(node.expressionNode());
  }
}
