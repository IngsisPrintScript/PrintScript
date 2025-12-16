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
import com.ingsis.utils.value.Value;
import java.util.List;

public record StringLiteralNode(String value, Integer line, Integer column) implements LiteralNode {

    @Override
    public CheckResult acceptChecker(Checker checker, SemanticEnvironment env) {
        return checker.check(this, env);
    }

    @Override
    public List<ExpressionNode> children() {
        return List.of();
    }

    @Override
    public String symbol() {
        return value();
    }

    @Override
    public InterpretResult acceptInterpreter(Interpreter interpreter, EvalState evalState) {
        return interpreter.interpret(this, evalState);
    }

    @Override
    public InterpretResult solve(EvalState evalState) {
        return new InterpretResult.CORRECT(evalState, new Value.StringValue(value()));
    }
}
