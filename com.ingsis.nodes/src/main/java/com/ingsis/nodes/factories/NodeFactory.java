/*
 * My Project
 */

package com.ingsis.nodes.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.nodes.expression.operator.OperatorNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.visitors.Interpretable;
import java.util.Collection;

public interface NodeFactory {
    IfKeywordNode createConditionalNode(
            OperatorNode booleanExpression,
            Collection<Interpretable> thenBody,
            Collection<Interpretable> elseBody);

    LetKeywordNode createLetNode(
            TypeAssignationNode typeAssignationNode, ValueAssignationNode valueAssignationNode);

    BinaryOperatorNode createBinaryOperatorNode(
            String symbol, ExpressionNode leftChild, ExpressionNode rightChild);

    ValueAssignationNode createValueAssignationNode(
            IdentifierNode identifierNode, ExpressionNode expressionNode);

    TypeAssignationNode createTypeAssignationNode(IdentifierNode identifierNode, TypeNode typeNode);

    TypeNode createTypeNode(String type);

    IdentifierNode createIdentifierNode(String name);

    LiteralNode createLiteralNode(String value);
}
