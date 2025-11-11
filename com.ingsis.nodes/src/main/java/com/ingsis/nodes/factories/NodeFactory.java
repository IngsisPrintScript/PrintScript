/*
 * My Project
 */

package com.ingsis.nodes.factories;

import com.ingsis.nodes.Node;
import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.type.TypeNode;
import java.util.List;

public interface NodeFactory {
    IfKeywordNode createConditionalNode(
            ExpressionNode condition,
            List<Node> thenBody,
            List<Node> elseBody,
            Integer line,
            Integer column);

    DeclarationKeywordNode createLetNode(
            TypeAssignationNode typeAssignationNode,
            ValueAssignationNode valueAssignationNode,
            Integer line,
            Integer column);

    DeclarationKeywordNode createConstNode(
            TypeAssignationNode typeAssignationNode,
            ValueAssignationNode valueAssignationNode,
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
