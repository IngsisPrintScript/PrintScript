/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.expression.operator; /*
                                                           * My Project
                                                           */

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.type.TypeNode;
import com.ingsis.utils.nodes.visitors.Visitor;
import com.ingsis.utils.result.Result;

public record TypeAssignationNode(
        IdentifierNode identifierNode, TypeNode typeNode, Integer line, Integer column)
        implements Node {
    @Override
    public Result<String> acceptVisitor(Visitor visitor) {
        return visitor.visit(this);
    }
}
