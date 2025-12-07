/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions.strategies;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class BinaryDivision implements ExpressionStrategy {

    private static final int SCALE = 20;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    @Override
    public Result<Object> solve(List<ExpressionNode> arguments) {
        if (arguments.size() != 2) {
            return new IncorrectResult<>(
                    "Operator '/' requires exactly 2 arguments, got " + arguments.size());
        }

        Result<BigDecimal> left = toBigDecimal(arguments.get(0));
        Result<BigDecimal> right = toBigDecimal(arguments.get(1));

        if (!left.isCorrect()) return new IncorrectResult<>(left);
        if (!right.isCorrect()) return new IncorrectResult<>(right);

        BigDecimal divisor = right.result();
        if (divisor.signum() == 0) {
            return new IncorrectResult<>("Division by zero");
        }

        return new CorrectResult<>(
                left.result().divide(divisor, SCALE, ROUNDING).stripTrailingZeros());
    }

    private Result<BigDecimal> toBigDecimal(ExpressionNode node) {
        Result<Object> res = node.solve();
        if (!res.isCorrect()) return new IncorrectResult<>(res.error());
        return convert(res.result());
    }

    private Result<BigDecimal> convert(Object value) {
        if (value instanceof BigDecimal bd) return new CorrectResult<>(bd);
        if (value instanceof Number n) return new CorrectResult<>(new BigDecimal(n.toString()));
        if (value instanceof Boolean b)
            return new CorrectResult<>(b ? BigDecimal.ONE : BigDecimal.ZERO);
        if (value instanceof Character c) return new CorrectResult<>(BigDecimal.valueOf(c));

        return new IncorrectResult<>(
                "Operator '/' cannot be applied to " + value.getClass().getSimpleName());
    }
}
