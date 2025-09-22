/*
 * My Project
 */

package com.ingsis.nodes.operator;

import com.ingsis.nodes.identifier.IdentifierNode;
import com.ingsis.nodes.operator.strategies.OperatorStrategy;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.Result;
import com.ingsis.visitors.Checker;
import com.ingsis.visitors.Interpreter;
import com.ingsis.visitors.Visitor;

public record TypeAssignationOperatorNode(
        IdentifierNode identifierNode, TypeNode typeNode, OperatorStrategy strategy)
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

    @Override
    public Result<Object> execute() {
        return strategy().execute(identifierNode(), typeNode());
    }
}
