/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.binary;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.Result;
import com.ingsis.runtime.Runtime;
import com.ingsis.visitors.Interpreter;
import java.util.List;

public final class AssignationSolutionStrategy implements ExpressionSolutionStrategy {
  private final ExpressionSolutionStrategy nextStrategy;
  private final Runtime RUNTIME;

  public AssignationSolutionStrategy(Runtime runtime, ExpressionSolutionStrategy nextStrategy) {
    this.nextStrategy = nextStrategy;
    this.RUNTIME = runtime;
  }

  private boolean canSolve(ExpressionNode expressionNode) {
    if (expressionNode.isTerminalNode()) {
      return false;
    }
    String symbol = expressionNode.symbol();
    return symbol.equals("=");
  }

  @Override
  public Result<Object> solve(Interpreter interpreter, ExpressionNode expressionNode) {
    if (!canSolve(expressionNode)) {
      return nextStrategy.solve(interpreter, expressionNode);
    }
    List<ExpressionNode> children = expressionNode.children();
    IdentifierNode identifierNode = (IdentifierNode) children.get(0);
    ExpressionNode varNewExpression = (ExpressionNode) children.get(1);

    Result<Object> interpretChild = interpreter.interpret(varNewExpression);
    if (!interpretChild.isCorrect()) {
      return interpretChild;
    }
    RUNTIME.getCurrentEnvironment().updateVariable(identifierNode.name(), interpretChild.result());
    return new CorrectResult<>(interpretChild.result());
  }
}
