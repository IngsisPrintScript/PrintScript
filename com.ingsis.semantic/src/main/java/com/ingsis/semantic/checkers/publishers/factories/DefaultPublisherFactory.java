/*
 * My Project
 */

package com.ingsis.semantic.checkers.publishers.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.semantic.checkers.handlers.NodeEventHandler;
import com.ingsis.semantic.checkers.handlers.factories.HandlersFactory;
import com.ingsis.semantic.checkers.publishers.GenericNodeEventPublisher;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class DefaultPublisherFactory implements PublishersFactory {
    private final HandlersFactory handlersFactory;

    public DefaultPublisherFactory(HandlersFactory handlersFactory) {
        this.handlersFactory = handlersFactory;
    }

    @Override
    public GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher() {
        return new GenericNodeEventPublisher<>(createLetNodeEventHandlers());
    }

    private Collection<NodeEventHandler<DeclarationKeywordNode>> createLetNodeEventHandlers() {
        Collection<NodeEventHandler<DeclarationKeywordNode>> handlers = new ArrayList<>();
        handlers.add(handlersFactory.createLetVariableExistenceHandler());
        handlers.add(handlersFactory.createLetCorrectTypeHandler());
        return handlers;
    }

    @Override
    public GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher() {
        return new GenericNodeEventPublisher<ExpressionNode>(createExpressionNodeEventHandlers());
    }

    private Collection<NodeEventHandler<ExpressionNode>> createExpressionNodeEventHandlers() {
        Collection<NodeEventHandler<ExpressionNode>> handlers = new ArrayList<>();
        handlers.add(handlersFactory.createExpressionVariableExistenceHandler());
        handlers.add(handlersFactory.createOperatorValidityHandler());
        return handlers;
    }

    @Override
    public GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher() {
        return new GenericNodeEventPublisher<>(List.of());
    }
}
