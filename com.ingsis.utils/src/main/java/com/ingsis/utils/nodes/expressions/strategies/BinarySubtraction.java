/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions.strategies;

import com.ingsis.utils.evalstate.EvalState;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.visitors.InterpretResult;
import com.ingsis.utils.value.Value;
import java.math.BigDecimal;
import java.util.List;

public class BinarySubtraction implements ExpressionStrategy {

    @Override
    public InterpretResult solve(List<ExpressionNode> arguments, EvalState evalState) {
        if (arguments.size() != 2) {
            return new InterpretResult.INCORRECT(
                    evalState,
                    "Operator '-' requires exactly 2 arguments, got " + arguments.size());
        }

        // Evaluate left argument
        Value left = Value.UnitValue.INSTANCE;
        InterpretResult leftRes = arguments.get(0).solve(evalState);
        switch (leftRes) {
            case InterpretResult.CORRECT C -> {
                evalState = C.evalState();
                left = C.value();
            }
            case InterpretResult.INCORRECT I -> {
                return I;
            }
        }

        // Evaluate right argument
        Value right = Value.UnitValue.INSTANCE;
        InterpretResult rightRes = arguments.get(1).solve(evalState);
        switch (rightRes) {
            case InterpretResult.CORRECT C -> {
                evalState = C.evalState();
                right = C.value();
            }
            case InterpretResult.INCORRECT I -> {
                return I;
            }
        }

        // Perform subtraction
        return switch (left) {
            case Value.IntValue LI ->
                    switch (right) {
                        case Value.IntValue RI ->
                                new InterpretResult.CORRECT(
                                        evalState, new Value.IntValue(LI.v().subtract(RI.v())));
                        default ->
                                new InterpretResult.INCORRECT(
                                        evalState, "Subtraction requires numeric types");
                    };
            case Value.BooleanValue LB ->
                    switch (right) {
                        case Value.BooleanValue RB -> {
                            BigDecimal leftNum = LB.v() ? BigDecimal.ONE : BigDecimal.ZERO;
                            BigDecimal rightNum = RB.v() ? BigDecimal.ONE : BigDecimal.ZERO;
                            yield new InterpretResult.CORRECT(
                                    evalState, new Value.IntValue(leftNum.subtract(rightNum)));
                        }
                        default ->
                                new InterpretResult.INCORRECT(
                                        evalState, "Subtraction requires numeric types");
                    };
            default ->
                    new InterpretResult.INCORRECT(evalState, "Subtraction requires numeric types");
        };
    }
}
