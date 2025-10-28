/*
 * My Project
 */

package com.ingsis.interpreter.visitor.expression.strategies.binary;

import com.ingsis.interpreter.visitor.expression.strategies.ExpressionSolutionStrategy;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.result.CorrectResult;
import com.ingsis.result.IncorrectResult;
import com.ingsis.result.Result;
import com.ingsis.visitors.Interpreter;
import java.util.List;

public final class SubstractionSolutionStrategy implements ExpressionSolutionStrategy {
  private final ExpressionSolutionStrategy nextStrategy;

  public SubstractionSolutionStrategy(ExpressionSolutionStrategy nextStrategy) {
    this.nextStrategy = nextStrategy;
  }

  private boolean canSolve(ExpressionNode expressionNode) {
    if (expressionNode.isTerminalNode()) {
      return false;
    }
    String symbol = expressionNode.symbol();
    return symbol.equals("-");
  }

  private Result<Object> substractByNewObject(Object old, Object delta) {
    try {
      double oldVal = Double.parseDouble(old.toString());
      double deltaVal = Double.parseDouble(delta.toString());
      return new CorrectResult<>(oldVal - deltaVal);
    } catch (NumberFormatException e) {
      return new IncorrectResult<>(
          "Cannot substract non-numeric values: " + old + " and " + delta);

    }
  }

  @Override
  public Result<Object> solve(Interpreter interpreter, ExpressionNode expressionNode) {
    if (!canSolve(expressionNode)) {
      return nextStrategy.solve(interpreter, expressionNode);
    }
    List<ExpressionNode> children = expressionNode.children();
    Object result = null;
    for (ExpressionNode child : children) {
      Result<Object> solveChildExpression = interpreter.interpret(child);
      if (!solveChildExpression.isCorrect()) {
        return new IncorrectResult<>(solveChildExpression);
      }
      Object childResult = solveChildExpression.result();
      if (result == null) {
        result = childResult;
        continue;
      }
      Result<Object> divideResult = substractByNewObject(result, childResult);
      if (!divideResult.isCorrect()) {
        return new IncorrectResult<>(divideResult);
      }
      result = divideResult.result();
    }
    return new CorrectResult<>(result);
  }
}
