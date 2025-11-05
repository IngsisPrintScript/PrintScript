/*
 * My Project
 */

package com.ingsis.nodes.keyword;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.result.Result;
import com.ingsis.visitors.Checkable;
import com.ingsis.visitors.Checker;
import com.ingsis.visitors.Interpretable;
import com.ingsis.visitors.Interpreter;
import com.ingsis.visitors.Visitor;

public record DeclarationKeywordNode(
        TypeAssignationNode typeAssignationNode,
        ValueAssignationNode valueAssignationNode,
        Boolean isMutable)
        implements Node, Checkable, Interpretable {

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
