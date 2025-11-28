/*
 * My Project
 */

package com.ingsis.semantic.checkers.publishers.factories;

import com.ingsis.nodes.expression.ExpressionNode;
import com.ingsis.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.nodes.keyword.IfKeywordNode;
import com.ingsis.rule.observer.handlers.factories.HandlerFactory;
import com.ingsis.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.rule.observer.publishers.factories.PublishersFactory;

public final class DefaultSemanticPublisherFactory implements PublishersFactory {
    private final HandlerFactory handlersFactory;

    public DefaultSemanticPublisherFactory(HandlerFactory handlersFactory) {
        this.handlersFactory = handlersFactory;
    }

    @Override
    public GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher() {
        return new GenericNodeEventPublisher<>(handlersFactory.createDeclarationHandler());
    }

    @Override
    public GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher() {
        return new GenericNodeEventPublisher<ExpressionNode>(
                handlersFactory.createExpressionHandler());
    }

    @Override
    public GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher() {
        return new GenericNodeEventPublisher<>(handlersFactory.createConditionalHandler());
    }
}
