package com.ingsis.utils.nodes.expressions.strategies;

import java.math.BigDecimal;
import java.util.List;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;

public class BinarySubtraction implements ExpressionStrategy {

  @Override
  public Result<Object> solve(List<ExpressionNode> arguments) {
    if (arguments.size() != 2) {
      return new IncorrectResult<>(
          "Operator '-' requires exactly 2 arguments, got " + arguments.size());
    }

    Result<BigDecimal> left = toBigDecimal(arguments.get(0));
    Result<BigDecimal> right = toBigDecimal(arguments.get(1));

    if (!left.isCorrect())
      return new IncorrectResult<>(left);
    if (!right.isCorrect())
      return new IncorrectResult<>(right);

    return new CorrectResult<>(left.result().subtract(right.result()));
  }

  private Result<BigDecimal> toBigDecimal(ExpressionNode node) {
    Result<Object> res = node.solve();
    if (!res.isCorrect())
      return new IncorrectResult<>(res);
    return convert(res.result());
  }

  private Result<BigDecimal> convert(Object value) {
    if (value instanceof BigDecimal bd)
      return new CorrectResult<>(bd);
    if (value instanceof Number n)
      return new CorrectResult<>(new BigDecimal(n.toString()));
    if (value instanceof Boolean b)
      return new CorrectResult<>(b ? BigDecimal.ONE : BigDecimal.ZERO);
    if (value instanceof Character c)
      return new CorrectResult<>(BigDecimal.valueOf(c));

    return new IncorrectResult<>(
        "Operator '-' cannot be applied to " + value.getClass().getSimpleName());
  }
}
