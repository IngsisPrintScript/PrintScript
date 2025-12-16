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
import java.util.List;

public record OperatorNode(
        OperatorType operatorType, List<ExpressionNode> children, Integer line, Integer column)
        implements ExpressionNode {

    @Override
    public CheckResult acceptChecker(Checker checker, SemanticEnvironment env) {
        return checker.check(this, env);
    }

    @Override
    public InterpretResult acceptInterpreter(Interpreter interpreter, EvalState evalState) {
        return interpreter.interpret(this, evalState);
    }

    @Override
    public String symbol() {
        return operatorType.symbol();
    }

    @Override
    public InterpretResult solve(EvalState evalState) {
        return operatorType().strategy().solve(children, evalState);
    }
}
