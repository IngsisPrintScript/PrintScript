/*
 * My Project
 */

package com.ingsis.nodes.operator;

import com.ingsis.result.Result;
import com.ingsis.visitors.Checker;
import com.ingsis.visitors.Interpreter;
import com.ingsis.visitors.Visitor;

public record BinaryOperatorNode(String symbol, OperatorNode left, OperatorNode right)
        implements OperatorNode {
    @Override
    public Result<String> acceptChecker(Checker checker) {
        return checker.check(this);
    }

    @Override
    public Result<String> acceptInterpreter(Interpreter interpreter) {
        return interpreter.interpret(this);
    }

    @Override
    public Result<String> acceptVisitor(Visitor visitor) {
        return visitor.visit(this);
    }
}
