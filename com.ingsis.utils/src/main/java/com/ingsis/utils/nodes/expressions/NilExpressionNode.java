/*
 * My Project
 */

package com.ingsis.utils.nodes.expressions;

import com.ingsis.utils.evalstate.EvalState;
import com.ingsis.utils.evalstate.env.semantic.SemanticEnvironment;
import com.ingsis.utils.nodes.visitors.CheckResult;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.InterpretResult;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.type.types.Types;
import com.ingsis.utils.value.Value;
import java.util.List;

public record NilExpressionNode() implements ExpressionNode {

    @Override
    public CheckResult acceptChecker(Checker checker, SemanticEnvironment env) {
        return checker.check(this, env);
    }

    @Override
    public InterpretResult acceptInterpreter(Interpreter interpreter, EvalState evalState) {
        return interpreter.interpret(this, evalState);
    }

    @Override
    public List<ExpressionNode> children() {
        return List.of();
    }

    @Override
    public String symbol() {
        return Types.NIL.name();
    }

    @Override
    public Integer line() {
        throw new UnsupportedOperationException(
                "Nil expression node has no line where it was built from.");
    }

    @Override
    public Integer column() {
        throw new UnsupportedOperationException(
                "Nil expression node has no line where it was built from.");
    }

    @Override
    public InterpretResult solve(EvalState evalState) {
        return new InterpretResult.CORRECT(evalState, Value.UnitValue.INSTANCE);
    }
}
