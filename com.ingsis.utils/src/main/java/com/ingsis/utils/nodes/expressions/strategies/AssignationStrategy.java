/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions.strategies;

import com.ingsis.utils.evalstate.EvalState;
import com.ingsis.utils.nodes.expressions.ExpressionNode;
import com.ingsis.utils.nodes.visitors.InterpretResult;
import com.ingsis.utils.value.Value;
import java.util.List;

public class AssignationStrategy implements ExpressionStrategy {

    @Override
    public InterpretResult solve(List<ExpressionNode> arguments, EvalState evalState) {
        String identifier = arguments.get(0).symbol();
        InterpretResult solveExpression = arguments.get(1).solve(evalState);
        return switch (solveExpression) {
            case InterpretResult.INCORRECT I -> I;
            case InterpretResult.CORRECT C ->
                    new InterpretResult.CORRECT(
                            evalState.withEnv(C.evalState().env().update(identifier, C.value())),
                            Value.UnitValue.INSTANCE);
        };
    }
}
