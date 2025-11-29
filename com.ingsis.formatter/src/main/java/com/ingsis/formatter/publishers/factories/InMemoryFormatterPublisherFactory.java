/*
 * My Project
 */

package com.ingsis.formatter.publishers.factories;

import com.ingsis.utils.nodes.nodes.expression.ExpressionNode;
import com.ingsis.utils.nodes.nodes.keyword.DeclarationKeywordNode;
import com.ingsis.utils.nodes.nodes.keyword.IfKeywordNode;
import com.ingsis.utils.rule.observer.handlers.factories.HandlerFactory;
import com.ingsis.utils.rule.observer.publishers.GenericNodeEventPublisher;
import com.ingsis.utils.rule.observer.publishers.factories.PublishersFactory;

public class InMemoryFormatterPublisherFactory implements PublishersFactory {
    private final HandlerFactory handlerFactory;

    public InMemoryFormatterPublisherFactory(HandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
    }

    @Override
    public GenericNodeEventPublisher<DeclarationKeywordNode> createLetNodePublisher() {
        return new GenericNodeEventPublisher<>(handlerFactory.createDeclarationHandler());
    }

    @Override
    public GenericNodeEventPublisher<IfKeywordNode> createConditionalNodePublisher() {
        return new GenericNodeEventPublisher<>(handlerFactory.createConditionalHandler());
    }

    @Override
    public GenericNodeEventPublisher<ExpressionNode> createExpressionNodePublisher() {
        return new GenericNodeEventPublisher<>(handlerFactory.createExpressionHandler());
    }
}
