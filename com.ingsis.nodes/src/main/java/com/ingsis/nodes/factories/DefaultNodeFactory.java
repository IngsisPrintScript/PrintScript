/*
 * My Project
 */

package com.ingsis.nodes.factories;

import com.ingsis.nodes.expression.identifier.IdentifierNode;
import com.ingsis.nodes.expression.literal.LiteralNode;
import com.ingsis.nodes.expression.operator.BinaryOperatorNode;
import com.ingsis.nodes.expression.operator.OperatorNode;
import com.ingsis.nodes.expression.operator.TypeAssignationNode;
import com.ingsis.nodes.expression.operator.ValueAssignationNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.types.Types;
import com.ingsis.visitors.Interpretable;
import java.util.Collection;

public final class DefaultNodeFactory implements NodeFactory {
    @Override
    public IfKeywordNode createConditionalNode(
            OperatorNode booleanExpression,
            Collection<Interpretable> thenBody,
            Collection<Interpretable> elseBody) {
        return new IfKeywordNode(booleanExpression, thenBody, elseBody);
    }

    @Override
    public LetKeywordNode createLetNode(
            TypeAssignationNode typeAssignationNode, ValueAssignationNode valueAssignationNode) {
        return new LetKeywordNode(typeAssignationNode, valueAssignationNode);
    }

    @Override
    public BinaryOperatorNode createBinaryOperatorNode(
            String symbol, OperatorNode leftChild, OperatorNode rightChild) {
        return new BinaryOperatorNode(symbol, leftChild, rightChild);
    }

    @Override
    public ValueAssignationNode createValueAssignationNode(
            IdentifierNode identifierNode, OperatorNode operatorNode) {
        return new ValueAssignationNode(identifierNode, operatorNode);
    }

    @Override
    public TypeAssignationNode createTypeAssignationNode(
            IdentifierNode identifierNode, TypeNode typeNode) {
        return new TypeAssignationNode(identifierNode, typeNode);
    }

    @Override
    public TypeNode createTypeNode(String type) {
        return new TypeNode(Types.fromKeyword(type));
    }

    @Override
    public IdentifierNode createIdentifierNode(String name) {
        return new IdentifierNode(name);
    }

    @Override
    public LiteralNode createLiteralNode(String value) {
        return new LiteralNode(value);
    }
}
