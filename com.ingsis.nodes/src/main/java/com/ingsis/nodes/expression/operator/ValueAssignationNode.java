/*
 * My Project
 */

package com.ingsis.nodes.expression.operator;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.operator.strategies.OperatorStrategy;
import com.ingsis.result.Result;
import com.ingsis.visitors.Visitor;

public record ValueAssignationNode(
        IdentifierNode identifierNode, ExpressionNode expressionNode, OperatorStrategy strategy)
        implements Node {
    @Override
    public Result<String> acceptVisitor(Visitor visitor) {
        return visitor.visit(this);
    }
}
