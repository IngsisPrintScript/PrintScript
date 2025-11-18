/*
 * My Project
 */

package com.ingsis.semantic.checkers.publishers.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.rule.observer.handlers.NodeEventHandler;
import com.ingsis.rule.observer.handlers.factories.HandlersFactory;
import com.ingsis.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.rule.observer.publishers.factories.PublishersFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class DefaultSemanticPublisherFactory implements PublishersFactory {
    private final HandlersFactory handlersFactory;

    public DefaultSemanticPublisherFactory(HandlersFactory handlersFactory) {
        this.handlersFactory = handlersFactory;
    }

    @Override
    public GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher() {
        return new GenericNodeEventPublisher<>(createLetNodeEventHandlers());
    }

    @Override
    public GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher() {
        return new GenericNodeEventPublisher<ExpressionNode>(createExpressionNodeEventHandlers());
    }

    @Override
    public GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher() {
        return new GenericNodeEventPublisher<>(List.of());
    }

    private Collection<NodeEventHandler<DeclarationKeywordNode>> createLetNodeEventHandlers() {
        Collection<NodeEventHandler<DeclarationKeywordNode>> handlers = new ArrayList<>();
        handlers.add(handlersFactory.createLetVariableExistenceHandler());
        handlers.add(handlersFactory.createLetCorrectTypeHandler());
        return handlers;
    }

    private Collection<NodeEventHandler<ExpressionNode>> createExpressionNodeEventHandlers() {
        Collection<NodeEventHandler<ExpressionNode>> handlers = new ArrayList<>();
        handlers.add(handlersFactory.createExpressionVariableExistenceHandler());
        handlers.add(handlersFactory.createOperatorValidityHandler());
        return handlers;
    }
}
