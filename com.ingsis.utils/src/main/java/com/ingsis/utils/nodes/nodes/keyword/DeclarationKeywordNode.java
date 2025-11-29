/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.keyword; /*
                                               * My Project
                                               */

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.utils.nodes.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.utils.nodes.visitors.Checkable;
import com.ingsis.utils.nodes.visitors.Checker;
import com.ingsis.utils.nodes.visitors.Interpretable;
import com.ingsis.utils.nodes.visitors.Interpreter;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.Result;

public record DeclarationKeywordNode(
        TypeAssignationNode typeAssignationNode,
        ValueAssignationNode valueAssignationNode,
        Boolean isMutable,
        Integer line,
        Integer column)
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
