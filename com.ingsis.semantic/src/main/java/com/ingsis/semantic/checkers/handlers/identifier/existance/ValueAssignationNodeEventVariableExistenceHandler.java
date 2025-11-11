/*
 * My Project
 */

package com.ingsis.semantic.checkers.handlers.identifier.existance;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.result.Result;
import com.ingsis.result.factory.ResultFactory;
import com.ingsis.runtime.Runtime;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("EI_EXPOSE_REP2")
public final class ValueAssignationNodeEventVariableExistenceHandler
    implements NodeEventHandler<ValueAssignationNode> {
  private final Runtime runtime;
  private final ResultFactory resultFactory;

  public ValueAssignationNodeEventVariableExistenceHandler(Runtime runtime, ResultFactory resultFactory) {
    this.runtime = runtime;
    this.resultFactory = resultFactory;
  }

  @Override
  public Result<String> handle(ValueAssignationNode node) {
    IdentifierNode identifierNode = node.identifierNode();

    if (!runtime.getCurrentEnvironment().isVariableDeclared(identifierNode.name())) {
      return resultFactory.createIncorrectResult(String.format(
          "Trying to declare an already declare identifier: %s on line: %d and column: %d",
          identifierNode.name(),
          identifierNode.line(),
          identifierNode.column()));
    }

    ExpressionNode expressionNode = node.expressionNode();
    Result<String> checkExpressionVariables = new ExpressionNodeEventVariableExistenceHandler(runtime, resultFactory)
        .handle(expressionNode);

    if (!checkExpressionVariables.isCorrect()) {
      return resultFactory.cloneIncorrectResult(checkExpressionVariables);
    }

    return resultFactory.createCorrectResult("Check passed.");
  }
}
