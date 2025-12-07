/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions.strategies;

import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.result.CorrectResult;
import com.ingsis.utils.result.IncorrectResult;
import com.ingsis.utils.result.Result;
import java.math.BigDecimal;
import java.util.List;

public class BinaryAddition implements ExpressionStrategy {

    @Override
    public Result<Object> solve(List<ExpressionNode> arguments) {
        if (arguments.size() != 2) {
            return new IncorrectResult<>(
                    "Operator '+' requires exactly 2 arguments, got " + arguments.size());
        }

        Result<Object> leftRes = arguments.get(0).solve();
        Result<Object> rightRes = arguments.get(1).solve();

        if (!leftRes.isCorrect()) return leftRes;
        if (!rightRes.isCorrect()) return rightRes;

        Object left = leftRes.result();
        Object right = rightRes.result();

        if (left instanceof String || right instanceof String) {
            return new CorrectResult<>(String.valueOf(left) + String.valueOf(right));
        }

        BigDecimal bdLeft = toBigDecimal(left);
        BigDecimal bdRight = toBigDecimal(right);

        if (bdLeft == null || bdRight == null) {
            return new CorrectResult<>(String.valueOf(left) + String.valueOf(right));
        }

        return new CorrectResult<>(bdLeft.add(bdRight));
    }

    private static BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        if (value instanceof BigDecimal bd) {
            return bd;
        }
        if (value instanceof Number n) {
            return new BigDecimal(n.toString());
        }
        if (value instanceof Boolean b) {
            return b ? BigDecimal.ONE : BigDecimal.ZERO;
        }
        if (value instanceof Character c) {
            return new BigDecimal(c.charValue());
        }
        return null;
    }
}
