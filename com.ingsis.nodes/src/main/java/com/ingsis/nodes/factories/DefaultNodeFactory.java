/*
 * My Project
 */

package com.ingsis.nodes.factories;

import com.ingsis.nodes.identifier.IdentifierNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.nodes.keyword.LetKeywordNode;
import com.ingsis.nodes.literal.LiteralNode;
import com.ingsis.nodes.operator.OperatorNode;
import com.ingsis.nodes.operator.TypeAssignationNode;
import com.ingsis.nodes.operator.ValueAssignationNode;
import com.ingsis.nodes.operator.strategies.OperatorStrategy;
import com.ingsis.nodes.operator.strategies.factories.StrategyFactory;
import com.ingsis.nodes.type.TypeNode;
import com.ingsis.types.Types;
import com.ingsis.visitors.Interpretable;
import java.util.Collection;

public final class DefaultNodeFactory implements NodeFactory {
    private final StrategyFactory STRATEGY_FACTORY;

    public DefaultNodeFactory(StrategyFactory STRATEGY_FACTORY) {
        this.STRATEGY_FACTORY = STRATEGY_FACTORY;
    }

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
    public ValueAssignationNode createValueAssignationNode(
            IdentifierNode identifierNode, OperatorNode operatorNode) {
        OperatorStrategy strategy = STRATEGY_FACTORY.typeAssignationStrategy();
        return new ValueAssignationNode(identifierNode, operatorNode, strategy);
    }

    @Override
    public TypeAssignationNode createTypeAssignationNode(
            IdentifierNode identifierNode, TypeNode typeNode) {
        OperatorStrategy strategy = STRATEGY_FACTORY.valueAssignationStrategy();
        return new TypeAssignationNode(identifierNode, typeNode, strategy);
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
