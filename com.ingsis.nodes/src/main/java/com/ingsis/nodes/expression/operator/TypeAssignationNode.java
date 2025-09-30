/*
 * My Project
 */

package com.ingsis.nodes.expression.operator;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.result.Result;
import com.ingsis.visitors.Visitor;

public record TypeAssignationNode(IdentifierNode identifierNode, TypeNode typeNode)
        implements Node {
    @Override
    public Result<String> acceptVisitor(Visitor visitor) {
        return visitor.visit(this);
    }
}
