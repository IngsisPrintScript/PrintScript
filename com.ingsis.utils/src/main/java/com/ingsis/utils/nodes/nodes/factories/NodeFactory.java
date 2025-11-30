/*
 * My Project
 */

package com.ingsis.utils.nodes.nodes.factories; /*
                                                 * My Project
                                                 */

import com.ingsis.utils.nodes.nodes.Node;
import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.expression.identifier.IdentifierNode;
import com.ingsis.utils.nodes.nodes.expression.literal.LiteralNode;
import com.ingsis.utils.nodes.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.utils.nodes.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.utils.nodes.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.nodes.nodes.type.TypeNode;
import com.ingsis.utils.type.types.Types;
import java.util.List;

public interface NodeFactory {
    IfKeywordNode createConditionalNode(
            ExpressionNode condition,
            List<Node> thenBody,
            List<Node> elseBody,
            Integer line,
            Integer column);

    DeclarationKeywordNode createDeclarationNode(
            IdentifierNode identifierNode,
            ExpressionNode expressionNode,
            Types declaredType,
            Boolean isMutable,
            Integer line,
            Integer column);

    BinaryOperatorNode createBinaryOperatorNode(
            String symbol,
            ExpressionNode leftChild,
            ExpressionNode rightChild,
            Integer line,
            Integer column);

    ValueAssignationNode createValueAssignationNode(
            IdentifierNode identifierNode,
            ExpressionNode expressionNode,
            Integer line,
            Integer column);

    TypeAssignationNode createTypeAssignationNode(
            IdentifierNode identifierNode, TypeNode typeNode, Integer line, Integer column);

    TypeNode createTypeNode(String type, Integer line, Integer column);

    IdentifierNode createIdentifierNode(String name, Integer line, Integer column);

    LiteralNode createLiteralNode(String value, Integer line, Integer column);
}
