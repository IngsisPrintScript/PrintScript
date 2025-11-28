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
import com.ingsis.types.Types;
import java.util.List;

public final class DefaultNodeFactory implements NodeFactory {
    @Override
    public IfKeywordNode createConditionalNode(
            ExpressionNode condition,
            List<Node> thenBody,
            List<Node> elseBody,
            Integer line,
            Integer column) {
        return new IfKeywordNode(condition, thenBody, elseBody, line, column);
    }

    @Override
    public DeclarationKeywordNode createLetNode(
            TypeAssignationNode typeAssignationNode,
            ValueAssignationNode valueAssignationNode,
            Integer line,
            Integer column) {
        return new DeclarationKeywordNode(
                typeAssignationNode, valueAssignationNode, true, line, column);
    }

    @Override
    public DeclarationKeywordNode createConstNode(
            TypeAssignationNode typeAssignationNode,
            ValueAssignationNode valueAssignationNode,
            Integer line,
            Integer column) {
        return new DeclarationKeywordNode(
                typeAssignationNode, valueAssignationNode, false, line, column);
    }

    @Override
    public BinaryOperatorNode createBinaryOperatorNode(
            String symbol,
            ExpressionNode leftChild,
            ExpressionNode rightChild,
            Integer line,
            Integer column) {
        return new BinaryOperatorNode(symbol, leftChild, rightChild, line, column);
    }

    @Override
    public ValueAssignationNode createValueAssignationNode(
            IdentifierNode identifierNode,
            ExpressionNode expressionNode,
            Integer line,
            Integer column) {
        return new ValueAssignationNode(identifierNode, expressionNode, line, column);
    }

    @Override
    public TypeAssignationNode createTypeAssignationNode(
            IdentifierNode identifierNode, TypeNode typeNode, Integer line, Integer column) {
        return new TypeAssignationNode(identifierNode, typeNode, line, column);
    }

    @Override
    public TypeNode createTypeNode(String type, Integer line, Integer column) {
        return new TypeNode(Types.fromKeyword(type), line, column);
    }

    @Override
    public IdentifierNode createIdentifierNode(String name, Integer line, Integer column) {
        return new IdentifierNode(name, line, column);
    }

    @Override
    public LiteralNode createLiteralNode(String value, Integer line, Integer column) {
        return new LiteralNode(value, line, column);
    }
}
